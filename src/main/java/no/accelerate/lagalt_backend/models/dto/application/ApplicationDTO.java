package no.accelerate.lagalt_backend.models.dto.application;

import lombok.Data;

@Data
public class ApplicationDTO {
    private int id;
    private String motivation;
    private String status;
    private String user;
    private int project;
}
