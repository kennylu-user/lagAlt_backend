package no.accelerate.lagalt_backend.models.dto.user;

import lombok.Data;
import no.accelerate.lagalt_backend.models.Project;

import java.util.Set;

@Data
public class UserPostDTO {
    private int id;
    private String f_name;
    private String l_name;
    private String description;
    private boolean hidden;
}
