package no.accelerate.lagalt_backend.mappers;

import no.accelerate.lagalt_backend.models.Project;
import no.accelerate.lagalt_backend.models.User;
import no.accelerate.lagalt_backend.models.dto.project.ProjectDTO;
import no.accelerate.lagalt_backend.models.dto.user.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class ProjectMapper {

    @Mapping(target = "owner", source = "owner", qualifiedByName = "userToIds")
    public abstract ProjectDTO projectToProjectDto(Project project);

    public abstract Collection<ProjectDTO> projectToProjectDto(Collection<Project> projects);

//    @Named("userToIds")
//    Set<Integer> map(Set<User> source) {
//        if (source == null) return null;
//        return source.stream().map(u -> u.getId()
//        ).collect(Collectors.toSet());
//    }
    @Named("userToIds")
    int map(User source){
        if (source == null) return -1;
        return source.getId();
    }
}
