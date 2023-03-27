package no.accelerate.lagalt_backend.services.application;

import no.accelerate.lagalt_backend.models.Application;
import no.accelerate.lagalt_backend.models.Project;
import no.accelerate.lagalt_backend.models.User;
import no.accelerate.lagalt_backend.repositories.ApplicationRepository;
import no.accelerate.lagalt_backend.repositories.ProjectRepository;
import no.accelerate.lagalt_backend.services.project.ProjectService;
import no.accelerate.lagalt_backend.utils.exceptions.ApplicationNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class ApplicationServiceImpl implements ApplicationService{
    private final ApplicationRepository applicationRepository;
    private final ProjectService projectService;
    private final ProjectRepository projectRepository;


    public ApplicationServiceImpl(ApplicationRepository applicationRepository, ProjectService projectService, ProjectRepository projectRepository) {
        this.applicationRepository = applicationRepository;
        this.projectService = projectService;
        this.projectRepository = projectRepository;
    }

    @Override
    public Application findById(Integer id) {
        return applicationRepository.findById(id).orElseThrow(() -> new ApplicationNotFoundException(id));
    }

    @Override
    public Collection<Application> findAll() {
        return applicationRepository.findAll();
    }

    @Override
    public Application add(Application entity) {
        return applicationRepository.save(entity);
    }

    @Override
    public void update(Application entity) {
        User user = this.findById(entity.getId()).getUser();
        Project p1 = this.findById(entity.getId()).getProject();
        entity.setUser(user);
        entity.setProject(p1);
        applicationRepository.save(entity);
        Set<String> membersToBe = new HashSet<>();

        Application a = findById(entity.getId());
        if(a.getStatus().equals("APPROVED")){
            membersToBe.add(entity.getUser().getId());
            entity.setProject(null);
        }
        applicationRepository.save(entity);
        projectService.updateMembers(p1.getId(), membersToBe);
        this.projectRepository.save(p1);
    }

    @Override
    public void deleteById(Integer integer) {
        applicationRepository.deleteById(integer);
    }

    @Override
    public void accept() {

    }

    @Override
    public void deny() {

    }
}
