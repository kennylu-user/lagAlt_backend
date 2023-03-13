package no.accelerate.lagalt_backend.models.dto.project;

import lombok.Data;

@Data
public class ProjectUpdateDTO {
    private int id;
    private String title;
    private String description;
    private String status;
    private String img_url;

}
