package no.accelerate.lagalt_backend.mappers;

import no.accelerate.lagalt_backend.models.Application;
import no.accelerate.lagalt_backend.models.Project;
import no.accelerate.lagalt_backend.models.User;
import no.accelerate.lagalt_backend.models.dto.application.ApplicationDTO;
import no.accelerate.lagalt_backend.models.dto.application.ApplicationPostDTO;
import no.accelerate.lagalt_backend.models.dto.application.ApplicationUpdateDTO;
import no.accelerate.lagalt_backend.models.dto.project.ProjectPostDTO;
import no.accelerate.lagalt_backend.models.dto.project.ProjectUpdateDTO;
import no.accelerate.lagalt_backend.services.project.ProjectService;
import no.accelerate.lagalt_backend.services.user.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class ApplicationMapper {
    @Autowired
    UserService userService;
    @Autowired
    ProjectService projectService;
    @Mapping(target = "user", source = "user", qualifiedByName = "idToUser")
    @Mapping(target = "project", source = "project", qualifiedByName = "idToProject")
    public abstract Application applicationPostDtoToApplication(ApplicationPostDTO applicationDto);
    public abstract Application applicationUpdateDtoToApplication(ApplicationUpdateDTO applicationUpdateDTO);
    @Mapping(target = "user", source = "user", qualifiedByName = "userToId")
    @Mapping(target = "project", source = "project", qualifiedByName = "projectToId")
    public abstract ApplicationDTO applicationToApplicationDto(Application application);
    public abstract Collection<ApplicationDTO> applicationToApplicationDto(Collection<Application> application);


    @Named("userToId")
    String map(User source) {
        if (source == null) return null;
        return source.getId();
    }

    @Named("projectToId")
    int map(Project source) {
        if (source == null) return -1;
        return source.getId();
    }
    @Named("idToUser")
    User mapToUser(String id) {
        return userService.findById(id);
    }
    @Named("idToProject")
    Project mapToProject(Integer id) {
        return projectService.findById(id);
    }
}
