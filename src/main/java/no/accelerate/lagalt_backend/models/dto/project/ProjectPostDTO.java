package no.accelerate.lagalt_backend.models.dto.project;

import lombok.Data;
import no.accelerate.lagalt_backend.models.Application;
import no.accelerate.lagalt_backend.models.User;
import no.accelerate.lagalt_backend.models.enums.Category;

import java.util.Set;

@Data
public class ProjectPostDTO {
    private int id;
    private String title;
    private String description;
    private Category category;
    private String status;
    private String owner;
    private String img_url;
    private Set<String> skillsRequired;
    private Set<String> tags;
}
