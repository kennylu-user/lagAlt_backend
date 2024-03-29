package no.accelerate.lagalt_backend.models.dto.skill;

import lombok.Data;

import java.util.Set;

@Data
public class SkillDTO {
    private int id;
    private String title;
    private Set<String> users;
    private Set<Integer> projects;
}

