package no.accelerate.lagalt_backend.services.project;

import no.accelerate.lagalt_backend.models.Application;
import no.accelerate.lagalt_backend.models.Comment;
import no.accelerate.lagalt_backend.models.Project;
import no.accelerate.lagalt_backend.models.User;
import no.accelerate.lagalt_backend.repositories.ProjectRepository;
import no.accelerate.lagalt_backend.repositories.UserRepository;
import no.accelerate.lagalt_backend.utils.error.exceptions.ProjectNotFoundException;
import no.accelerate.lagalt_backend.utils.error.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class ProjectServiceImpl implements ProjectService{
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
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
    public void updateMembers(int id, int[] user_ids) {
        Project p = this.projectRepository.findById(id).orElseThrow(() -> new ProjectNotFoundException(id));
        Set<User> members = new HashSet<>();
        System.out.println(p.getMembers());

        for (int u_id : user_ids) {
            System.out.println(u_id);
            User u = userRepository.findById(u_id).orElseThrow(() -> new UserNotFoundException(u_id));
            Set<Project> proj = u.getProjectsParticipated();
            proj.add(p);
            u.setProjectsParticipated(proj);
            members.add(u);

            userRepository.save(u);
        }

        p.setMembers(members);

        System.out.println(p.getMembers());

        projectRepository.save(p);

    }

    @Override
    public Set<Comment> getAllComments(int id) {
        Project p = this.projectRepository.findById(id).orElseThrow(() -> new ProjectNotFoundException(id));
        return p.getComments();
    }

}
