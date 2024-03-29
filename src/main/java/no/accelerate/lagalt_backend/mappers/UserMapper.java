package no.accelerate.lagalt_backend.mappers;

import no.accelerate.lagalt_backend.models.*;
import no.accelerate.lagalt_backend.models.dto.project.ProjectPostDTO;
import no.accelerate.lagalt_backend.models.dto.user.UserDTO;
import no.accelerate.lagalt_backend.models.dto.user.UserPostDTO;
import no.accelerate.lagalt_backend.models.dto.user.UserUpdateDTO;
import no.accelerate.lagalt_backend.repositories.SkillRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Autowired
    SkillRepository skillRepository;
    public abstract User userPostDtoToUser(UserPostDTO userDto);

    @Mapping(target = "skills", source = "skills", qualifiedByName = "skillsTitlesToSkills")
    public abstract User userUpdateDtoToUser(UserUpdateDTO userUpdateDTO);

    // Mappings from character to DTO
    @Mapping(target = "projectsOwned", source = "projectsOwned", qualifiedByName = "projectsToIds")
    @Mapping(target = "projectsParticipated", source = "projectsParticipated", qualifiedByName = "projectsToIds")
    @Mapping(target = "applications", source = "applications", qualifiedByName = "projectsToIds1")
    @Mapping(target = "comments", source = "comments", qualifiedByName = "commentsToIds")
    @Mapping(target = "skills", source = "skills", qualifiedByName = "skillsToTitles")
    public abstract UserDTO userToUserDto(User user);

    public abstract Collection<UserDTO> userToUserDto(Collection<User> users);


    @Named("projectsToIds")
    Set<Integer> map(Set<Project> source) {
        if (source == null) return null;
        return source.stream().map(p -> p.getId()
        ).collect(Collectors.toSet());
    }
    @Named("projectsToIds1")
    Set<Integer> map1(Set<Application> source) {
        if (source == null) return null;
        return source.stream().map(p -> p.getId()
        ).collect(Collectors.toSet());
    }
    @Named("commentsToIds")
    Set<Integer> commentsToIds(Set<Comment> source) {
        if (source == null) return null;
        return source.stream().map(p -> p.getId()
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