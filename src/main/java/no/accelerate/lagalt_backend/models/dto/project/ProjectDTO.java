package no.accelerate.lagalt_backend.models.dto.project;

import jakarta.persistence.*;
import lombok.Data;
import no.accelerate.lagalt_backend.models.Application;
import no.accelerate.lagalt_backend.models.Comment;
import no.accelerate.lagalt_backend.models.Skill;
import no.accelerate.lagalt_backend.models.User;
import no.accelerate.lagalt_backend.models.enums.Category;

import java.util.Set;

@Data
public class ProjectDTO {
    private int id;
    private String title;
    private String description;
    private Category category;
    private String status;
    private Set<Integer> applications;
    private String owner;
    private Set<String> members;
    private String img_url;
    private Set<Integer> comments;
    private Set<Integer> skillsRequired;
    private Set<String> tags;
}
