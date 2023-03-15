package no.accelerate.lagalt_backend.models.dto.skill;

import lombok.Data;
import no.accelerate.lagalt_backend.models.Project;
import no.accelerate.lagalt_backend.models.User;

import java.util.Set;

@Data
public class SkillDTO {
    private int id;
    private String title;
    private Set<Integer> users;
    private Set<Integer> projects;
}

