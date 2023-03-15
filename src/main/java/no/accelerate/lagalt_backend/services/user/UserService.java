package no.accelerate.lagalt_backend.services.user;

import no.accelerate.lagalt_backend.models.Application;
import no.accelerate.lagalt_backend.models.Project;
import no.accelerate.lagalt_backend.models.User;
import no.accelerate.lagalt_backend.services.CrudService;

import java.util.Collection;
import java.util.Set;

public interface UserService extends CrudService<User,Integer> {
    public Set<Project> findAllProjectsOwned(int id);
    public Set<Project> findAllProjectsParticipated(int id);
    public Set<Application> findAllUserApplications(int id);

    void applyToProject(Application applicationUpdateDtoToApplication);
}
