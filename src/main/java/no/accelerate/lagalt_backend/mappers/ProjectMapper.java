package no.accelerate.lagalt_backend.mappers;

import no.accelerate.lagalt_backend.models.*;
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
    @Mapping(target = "members", source = "members", qualifiedByName = "userMembersToIds")
    @Mapping(target = "skillsRequired", source = "skillsRequired", qualifiedByName = "skillsToIds")
    @Mapping(target = "comments", source = "comments", qualifiedByName = "commentsToIds")
    public abstract ProjectDTO projectToProjectDto(Project project);

    public abstract Collection<ProjectDTO> projectToProjectDto(Collection<Project> projects);

    @Named("applicationUserToIds")
    Set<Integer> map(Set<Application> source) {
        if (source == null) return null;
        return source.stream().map(u -> u.getId()
        ).collect(Collectors.toSet());
    }
    @Named("userMembersToIds")
    Set<Integer> membersToIds(Set<User> source) {
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

    @Named("skillsToIds")
    Set<Integer> skillsToIds(Set<Skill> source) {
        if (source == null) return null;
        return source.stream().map(s -> s.getId()
        ).collect(Collectors.toSet());
    }

    @Named("commentsToIds")
    Set<Integer> commentsToIds(Set<Comment> source) {
        if (source == null) return null;
        return source.stream().map(c -> c.getId()
        ).collect(Collectors.toSet());
    }
}
