package no.accelerate.lagalt_backend.models.dto.user;

//import no.accelerate.lagalt_backend.models.Application;
import no.accelerate.lagalt_backend.models.Project;

import java.util.Set;

public class UserDTO {
    private int id;
    private String f_name;
    private String l_name;
    private String description;
//    private Set<Application> applications;
    private Set<Project> projectsOwned;
    private Set<Project> projectsParticipated;
    private boolean hidden;
}
