package no.accelerate.lagalt_backend.services.skill;

import no.accelerate.lagalt_backend.models.Skill;
import no.accelerate.lagalt_backend.repositories.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class SkillServiceImpl implements SkillService{
    private final SkillRepository skillRepository;

    public SkillServiceImpl(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @Override
    public Skill findById(Integer integer) {
        return null;
    }

    @Override
    public Skill add(Skill entity) {
        return null;
    }

    @Override
    public void update(Skill entity) {

    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public Collection<Skill> findAll() {
        return skillRepository.findAll();
    }
}
