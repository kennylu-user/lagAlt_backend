package no.accelerate.lagalt_backend.services.skill;

import no.accelerate.lagalt_backend.models.Skill;
import no.accelerate.lagalt_backend.services.CrudService;

import java.util.Collection;

public interface SkillService extends CrudService<Skill, Integer> {
    public Collection<Skill> findAll();
}
