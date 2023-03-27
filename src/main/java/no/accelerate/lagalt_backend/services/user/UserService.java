package no.accelerate.lagalt_backend.services.user;

import no.accelerate.lagalt_backend.models.*;
import no.accelerate.lagalt_backend.services.CrudService;

import java.util.Collection;
import java.util.Set;

public interface UserService extends CrudService<User,String> {
    public Set<Project> findAllProjectsOwned(String id);
    public Set<Project> findAllProjectsParticipated(String id);
    public Set<Application> findAllUserApplications(String id);

    void applyToProject(Application applicationUpdateDtoToApplication);

    Set<Skill> findAllSkills(String id);

    Set<Comment> findAllComments(String id);

    Set<Project> findAllRecommended(String id);
}
