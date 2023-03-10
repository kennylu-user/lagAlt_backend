package no.accelerate.lagalt_backend.models.dto.project;

import jakarta.persistence.*;
import lombok.Data;
import no.accelerate.lagalt_backend.models.Application;
import no.accelerate.lagalt_backend.models.User;

import java.util.Set;

@Data
public class ProjectDTO {
    private int id;
    private String title;
    private String description;
    private String status;
    Set<User> contributors;
    Set<Application> applications;
    private int owner;
    private Set<User> members;
}
