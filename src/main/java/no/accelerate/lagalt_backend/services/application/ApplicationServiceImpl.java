package no.accelerate.lagalt_backend.services.application;

import no.accelerate.lagalt_backend.models.Application;
import no.accelerate.lagalt_backend.models.Project;
import no.accelerate.lagalt_backend.models.User;
import no.accelerate.lagalt_backend.repositories.ApplicationRepository;
import no.accelerate.lagalt_backend.utils.error.exceptions.ApplicationNotFoundException;
import no.accelerate.lagalt_backend.utils.error.exceptions.ProjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ApplicationServiceImpl implements ApplicationService{
    private final ApplicationRepository applicationRepository;

    public ApplicationServiceImpl(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
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
    }

    @Override
    public void deleteById(Integer integer) {
        applicationRepository.deleteById(integer);
    }
}
