package no.accelerate.lagalt_backend.services.project;

import no.accelerate.lagalt_backend.models.Application;
import no.accelerate.lagalt_backend.models.Project;
import no.accelerate.lagalt_backend.models.User;
import no.accelerate.lagalt_backend.repositories.ProjectRepository;
import no.accelerate.lagalt_backend.utils.error.exceptions.ProjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;

@Service
public class ProjectServiceImpl implements ProjectService{
    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
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
}
