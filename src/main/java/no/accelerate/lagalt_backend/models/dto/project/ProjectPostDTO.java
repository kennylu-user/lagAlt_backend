package no.accelerate.lagalt_backend.models.dto.project;

import lombok.Data;
import no.accelerate.lagalt_backend.models.Application;
import no.accelerate.lagalt_backend.models.User;

@Data
public class ProjectPostDTO {
    private int id;
    private String title;
    private String description;
    private String status;
    private Integer owner;
    private String img_url;
}
