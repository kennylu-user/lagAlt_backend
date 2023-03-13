package no.accelerate.lagalt_backend.services.project;

import no.accelerate.lagalt_backend.models.Application;
import no.accelerate.lagalt_backend.models.User;
import no.accelerate.lagalt_backend.models.Project;
import no.accelerate.lagalt_backend.services.CrudService;

import java.util.Set;


public interface ProjectService extends CrudService<Project, Integer> {
    public Set<Application> findAllProjectApplications(int id);
    public Set<User> findAllMembers(int id);

}
