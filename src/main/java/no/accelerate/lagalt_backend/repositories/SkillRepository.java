package no.accelerate.lagalt_backend.repositories;

import no.accelerate.lagalt_backend.models.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Integer> {
    Optional<Skill> findByTitle(String title);
}
