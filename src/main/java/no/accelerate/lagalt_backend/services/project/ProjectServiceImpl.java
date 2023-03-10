package no.accelerate.lagalt_backend.services.project;

import no.accelerate.lagalt_backend.models.Project;
import no.accelerate.lagalt_backend.repositories.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
@Service
public class ProjectServiceImpl implements ProjectService{
    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }


    @Override
    public Project findById(Integer integer) {
        return null;
    }

    @Override
    public Collection<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public Project add(Project entity) {
        return null;
    }

    @Override
    public void update(Project entity) {

    }

    @Override
    public void deleteById(Integer integer) {

    }
}
