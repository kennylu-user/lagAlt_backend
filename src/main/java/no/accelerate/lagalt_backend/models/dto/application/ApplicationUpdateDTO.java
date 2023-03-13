package no.accelerate.lagalt_backend.models.dto.application;

import lombok.Data;

@Data
public class ApplicationUpdateDTO {
    private int id;
    private String motivation;
    private String status;
}
