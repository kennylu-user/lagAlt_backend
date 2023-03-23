package no.accelerate.lagalt_backend.services.project;

import no.accelerate.lagalt_backend.models.*;
import no.accelerate.lagalt_backend.repositories.ApplicationRepository;
import no.accelerate.lagalt_backend.repositories.ProjectRepository;
import no.accelerate.lagalt_backend.repositories.SkillRepository;
import no.accelerate.lagalt_backend.repositories.UserRepository;
import no.accelerate.lagalt_backend.services.skill.SkillService;
import no.accelerate.lagalt_backend.services.application.ApplicationService;
import no.accelerate.lagalt_backend.services.comment.CommentService;
import no.accelerate.lagalt_backend.utils.exceptions.ProjectNotFoundException;
import no.accelerate.lagalt_backend.utils.exceptions.SkillNotFoundException;
import no.accelerate.lagalt_backend.utils.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService{
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ApplicationRepository applicationRepository;
    private final SkillRepository skillRepository;
    private final ApplicationService applicationService;
    private final CommentService commentService;

    public ProjectServiceImpl(ProjectRepository projectRepository, UserRepository userRepository, ApplicationService applicationService, ApplicationRepository applicationRepository, CommentService commentService,SkillRepository skillRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.applicationService = applicationService;
        this.applicationRepository = applicationRepository;
        this.skillRepository = skillRepository;
        this.commentService = commentService;
    }


    @Override
    public Project findById(Integer id) {
        return projectRepository.findById(id).orElseThrow(() -> new ProjectNotFoundException(id));
    }

    @Override
    public Collection<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public Project add(Project entity) {
        return projectRepository.save(entity);
    }

    @Override
    public void update(Project entity) {
        User owner = this.findById(entity.getId()).getOwner();
        entity.setOwner(owner);
        projectRepository.save(entity);
    }

    @Override
    public void deleteById(Integer id) {
        Set<Application> applications = this.findAllProjectApplications(id);
        Set<Comment> comments = this.findAllComments(id);
        Set<String> members = this.findAllMembers(id)
                .stream()
                .map(user -> user.getId())
                .collect(Collectors.toSet());
        for (Application a : applications) {
            applicationService.deleteById(a.getId());
        }
        for (Comment c : comments) {
            commentService.deleteById(c.getId());
        }
        this.removeMembersByIds(id,members);

        projectRepository.deleteById(id);
    }


    @Override
    public Set<Application> findAllProjectApplications(int id) {
        Project project = projectRepository.findById(id).orElseThrow(() -> new ProjectNotFoundException(id));
        return project.getApplications();
    }

    @Override
    public Set<User> findAllMembers(int id) {
        Project p = this.projectRepository.findById(id).orElseThrow(() -> new ProjectNotFoundException(id));
        return p.getMembers();
    }

    @Override
    public void updateMembers(int id, Set<String> user_ids) {
        Project p = this.projectRepository.findById(id).orElseThrow(() -> new ProjectNotFoundException(id));
        Set<User> members = new HashSet<>();

        for (String u_id : user_ids) {
            System.out.println(u_id);
            User u = userRepository.findById(u_id).orElseThrow(() -> new UserNotFoundException(u_id));
            Set<Project> proj = u.getProjectsParticipated();
            proj.add(p);
            u.setProjectsParticipated(proj);
            members.add(u);

            userRepository.save(u);
        }
        for (User user : p.getMembers()
             ) {
            members.add(user);

        }

        p.setMembers(members);


        projectRepository.save(p);

    }

    @Override
    public void test(int id) {
        Project p = this.findById(id);
        Set<String> membersToBe = new HashSet<>();
        for (Application a :p.getApplications()
             ) {
            if(a.getStatus().equals("APPROVED")){
                membersToBe.add(a.getUser().getId());
                a.setProject(null);
            } else if (a.getStatus().equals("DENIED")) {
                a.setProject(null);
            }

            applicationRepository.save(a);
        }
        this.updateMembers(id, membersToBe);
        this.projectRepository.save(p);
    }

    @Override
    public void removeMembersByIds(int id, Set<String> user_ids) {
        Project p = this.projectRepository.findById(id).orElseThrow(() -> new ProjectNotFoundException(id));
        Set<User> members = new HashSet<>();

        for (String u_id : user_ids) {
            User u = userRepository.findById(u_id).orElseThrow(() -> new UserNotFoundException(u_id));
            Set<Project> proj = u.getProjectsParticipated();
            proj.remove(p);
            u.setProjectsParticipated(proj);
//            members.add(u);

            userRepository.save(u);
        }
        for (User user : p.getMembers()
        ) {
            if(!user_ids.contains(user.getId())){

                members.add(user);
            }
        }

        p.setMembers(members);


        projectRepository.save(p);

    }

    @Override
    public Set<Skill> findAllSkills(int id) {
        return this.findById(id).getSkillsRequired();
    }

    @Override
    public Set<Comment> findAllComments(int id) {
        return this.findById(id).getComments();
    }

    @Override
    public Set<String> findAllTags(int id) {
        return this.findById(id).getTags();
    }

    @Override
    public void updateSkills(int id, int[] skillsIds) {
        Project project = projectRepository.findById(id).orElseThrow(() -> new ProjectNotFoundException(id));
        Set<Skill> skills = new HashSet<>();
        for (int skillId : skillsIds) {
            Skill skill = skillRepository.findById(skillId).orElseThrow(() -> new SkillNotFoundException(skillId));
            skills.add(skill);
        }
        project.setSkillsRequired(skills);
        projectRepository.save(project);
    }

}
