package no.accelerate.lagalt_backend.services.user;

import no.accelerate.lagalt_backend.models.*;
import no.accelerate.lagalt_backend.repositories.UserRepository;
import no.accelerate.lagalt_backend.services.application.ApplicationService;
import no.accelerate.lagalt_backend.services.comment.CommentService;
import no.accelerate.lagalt_backend.services.project.ProjectService;
import no.accelerate.lagalt_backend.services.skill.SkillService;
import no.accelerate.lagalt_backend.utils.exceptions.UserNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ApplicationService applicationService;
    private final ProjectService projectService;
    private final CommentService commentService;
    private final SkillService skillService;

    public UserServiceImpl(UserRepository userRepository, ApplicationService applicationService, ProjectService projectService, CommentService commentService, SkillService skillService) {
        this.userRepository = userRepository;
        this.applicationService = applicationService;
        this.projectService = projectService;
        this.commentService = commentService;
        this.skillService = skillService;
    }

    @Override
    public User findById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public Collection<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User add(User entity) {
        return userRepository.save(entity);
    }
    public User addById(Jwt jwt) {
        User user = new User();
        user.setId(jwt.getClaimAsString("sub"));
        user.setF_name(jwt.getClaimAsString("name"));
        user.setHidden(false);
        userRepository.save(user);
        return user;
    }

    @Override
    public void update(User entity) {
        userRepository.save(entity);
    }

    @Override
    public void deleteById(String id) {
        Set<Application> applications = this.findAllUserApplications(id);
        Set<Project> projects = this.findAllProjectsOwned(id);
        Set<Comment> comments = this.findAllComments(id);
        for (Application a : applications) {
            applicationService.deleteById(a.getId());
        }
        for (Project p : projects) {
            projectService.deleteById(p.getId());
        }
        for (Comment c : comments) {
            commentService.deleteById(c.getId());
        }
        userRepository.deleteById(id);
    }
    @Override
    public Set<Project> findAllProjectsOwned(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return user.getProjectsOwned();
    }

    @Override
    public Set<Project> findAllProjectsParticipated(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return user.getProjectsParticipated();
    }

    @Override
    public Set<Application> findAllUserApplications(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return user.getApplications();
    }

    @Override
    public void applyToProject(Application applicationUpdateDtoToApplication) {

        User user = applicationUpdateDtoToApplication.getUser();
        user.getApplications().add(applicationUpdateDtoToApplication);
        userRepository.save(user);
    }

    @Override
    public Set<Skill> findAllSkills(String id) {
        return this.findById(id).getSkills();
    }

    @Override
    public Set<Comment> findAllComments(String id) {
        return this.findById(id).getComments();
    }

    @Override
    public Set<Project> findAllRecommended(String id) {
        Set<Project> recommended = new HashSet<>();
        Set<Skill> allSkills = findAllSkills(id);
        Set<Project> allProj = projectService.findAll().stream().collect(Collectors.toSet());
        for (Skill s : allSkills) {
            for (Project p : s.getProjects()) {
                System.out.println(p);
                if(!recommended.contains(p)){
                    recommended.add(p);
                }
            }
        }
        for (Project p : allProj) {
            if(!recommended.contains(p)){
                recommended.add(p);
            }
        }

        return recommended;
    }


}
