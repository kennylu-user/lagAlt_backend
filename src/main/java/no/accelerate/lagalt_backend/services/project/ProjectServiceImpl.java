package no.accelerate.lagalt_backend.services.project;

import no.accelerate.lagalt_backend.models.Project;
import no.accelerate.lagalt_backend.models.User;
import no.accelerate.lagalt_backend.repositories.ProjectRepository;
import no.accelerate.lagalt_backend.utils.error.exceptions.ProjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
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
}
