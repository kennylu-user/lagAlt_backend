package no.accelerate.lagalt_backend.models.dto.user;

import lombok.Data;
import no.accelerate.lagalt_backend.models.Project;

import java.util.Set;

@Data
public class UserPostDTO {
    private String id;
    private String f_name;
    private String l_name;

}
