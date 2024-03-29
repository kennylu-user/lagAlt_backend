package no.accelerate.lagalt_backend.models.dto.user;

//import no.accelerate.lagalt_backend.models.Application;
import lombok.Data;
import no.accelerate.lagalt_backend.models.Application;
import no.accelerate.lagalt_backend.models.Comment;
import no.accelerate.lagalt_backend.models.Project;

import java.time.LocalDate;
import java.util.Set;

@Data
public class UserDTO {
    private String id;
    private String f_name;
    private String l_name;
    private String description;
    private Set<Integer> applications;
    private Set<Integer> projectsOwned;
    private Set<Integer> projectsParticipated;
    private Set<Integer> comments;
    private Set<String> skills;
    private boolean hidden;

}
