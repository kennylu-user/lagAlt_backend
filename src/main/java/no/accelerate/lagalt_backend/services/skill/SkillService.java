package no.accelerate.lagalt_backend.services.skill;

import no.accelerate.lagalt_backend.models.Project;
import no.accelerate.lagalt_backend.models.Skill;
import no.accelerate.lagalt_backend.models.User;
import no.accelerate.lagalt_backend.services.CrudService;

import java.util.Set;

public interface SkillService extends CrudService<Skill, Integer> {
    public void updateUsers(int skill_id, String[] user_ids);

    public Set<User> findAllUsers(int skill_id);

    public void updateProjects(int skill_id, int[] project_ids);

    public Set<Project> findAllProjects(int skill_id);

}
