package no.accelerate.lagalt_backend.mappers;

import no.accelerate.lagalt_backend.models.Application;
import no.accelerate.lagalt_backend.models.Project;
import no.accelerate.lagalt_backend.models.User;
import no.accelerate.lagalt_backend.models.dto.project.ProjectDTO;
import no.accelerate.lagalt_backend.models.dto.project.ProjectPostDTO;
import no.accelerate.lagalt_backend.models.dto.project.ProjectUpdateDTO;
import no.accelerate.lagalt_backend.models.dto.user.UserDTO;
import no.accelerate.lagalt_backend.services.user.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class ProjectMapper {
    @Autowired
    UserService userService;
    @Mapping(target = "owner", source = "owner", qualifiedByName = "idToOwner")
    public abstract Project projectPostDtoToProject(ProjectPostDTO projectDto);
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "applications", ignore = true)
    @Mapping(target = "members", ignore = true)
    public abstract Project projectUpdateDtoToProject(ProjectUpdateDTO projectUpdateDTO);

    @Mapping(target = "owner", source = "owner", qualifiedByName = "userToIds")
    @Mapping(target = "applications", source = "applications", qualifiedByName = "applicationUserToIds")
    public abstract ProjectDTO projectToProjectDto(Project project);

    public abstract Collection<ProjectDTO> projectToProjectDto(Collection<Project> projects);

    @Named("applicationUserToIds")
    Set<Integer> map(Set<Application> source) {
        if (source == null) return null;
        return source.stream().map(u -> u.getId()
        ).collect(Collectors.toSet());
    }
    @Named("userToIds")
    int map(User source){
        if (source == null) return -1;
        return source.getId();
    }

    @Named("idToOwner")
    User mapToUser(Integer id) {
        return userService.findById(id);
    }
}
