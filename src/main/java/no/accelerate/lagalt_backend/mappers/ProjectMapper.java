package no.accelerate.lagalt_backend.mappers;

import no.accelerate.lagalt_backend.models.*;
import no.accelerate.lagalt_backend.models.dto.project.ProjectDTO;
import no.accelerate.lagalt_backend.models.dto.project.ProjectPostDTO;
import no.accelerate.lagalt_backend.models.dto.project.ProjectUpdateDTO;
import no.accelerate.lagalt_backend.repositories.SkillRepository;
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
    SkillRepository skillRepository;
    @Autowired
    UserService userService;
    @Mapping(target = "owner", source = "owner", qualifiedByName = "idToOwner")
    @Mapping(target = "skillsRequired", source = "skillsRequired", qualifiedByName = "skillsTitlesToSkills")
    public abstract Project projectPostDtoToProject(ProjectPostDTO projectDto);
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "applications", ignore = true)
    @Mapping(target = "members", ignore = true)
    @Mapping(target = "skillsRequired", source = "skillsRequired", qualifiedByName = "skillsTitlesToSkills")
    public abstract Project projectUpdateDtoToProject(ProjectUpdateDTO projectUpdateDTO);

    @Mapping(target = "owner", source = "owner", qualifiedByName = "userToIds")
    @Mapping(target = "applications", source = "applications", qualifiedByName = "applicationUserToIds")
    @Mapping(target = "members", source = "members", qualifiedByName = "userMembersToIds")
    @Mapping(target = "comments", source = "comments", qualifiedByName = "commentsToIds")
    @Mapping(target = "skillsRequired", source = "skillsRequired", qualifiedByName = "skillsToTitles")
    public abstract ProjectDTO projectToProjectDto(Project project);

    public abstract Collection<ProjectDTO> projectToProjectDto(Collection<Project> projects);

    @Named("applicationUserToIds")
    Set<Integer> map(Set<Application> source) {
        if (source == null) return null;
        return source.stream().map(u -> u.getId()
        ).collect(Collectors.toSet());
    }
    @Named("userMembersToIds")
    Set<String> membersToIds(Set<User> source) {
        if (source == null) return null;
        return source.stream().map(u -> u.getId()
        ).collect(Collectors.toSet());
    }
    @Named("userToIds")
    String map(User source){
        if (source == null) return null;
        return source.getId();
    }

    @Named("idToOwner")
    User mapToUser(String id) {
        return userService.findById(id);
    }

    @Named("commentsToIds")
    Set<Integer> commentsToIds(Set<Comment> source) {
        if (source == null) return null;
        return source.stream().map(u -> u.getId()
        ).collect(Collectors.toSet());
    }
    @Named("skillsToTitles")
    Set<String> skillsToTitles(Set<Skill> source) {
        if (source == null) return null;
        return source.stream().map(u -> u.getTitle()
        ).collect(Collectors.toSet());
    }

    @Named("skillsTitlesToSkills")
    Set<Skill> skillsTitlesToSkills(Set<String> source) {
        if (source == null) return null;
        return source.stream().map(title -> skillRepository.findByTitle(title)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid skill title: " + title)))
                .collect(Collectors.toSet());
    }
}
