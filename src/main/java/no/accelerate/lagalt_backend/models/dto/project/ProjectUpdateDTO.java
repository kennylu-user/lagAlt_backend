package no.accelerate.lagalt_backend.models.dto.project;

import lombok.Data;
import no.accelerate.lagalt_backend.models.enums.Category;

import java.util.Set;

@Data
public class ProjectUpdateDTO {
    private int id;
    private String title;
    private String description;
    private Category category;
    private String status;
    private String img_url;
    private Set<String> skillsRequired;
    private Set<String> tags;
}
