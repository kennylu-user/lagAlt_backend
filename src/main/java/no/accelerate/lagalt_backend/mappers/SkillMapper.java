package no.accelerate.lagalt_backend.mappers;

import no.accelerate.lagalt_backend.models.Project;
import no.accelerate.lagalt_backend.models.Skill;
import no.accelerate.lagalt_backend.models.User;
import no.accelerate.lagalt_backend.models.dto.skill.SkillDTO;
import no.accelerate.lagalt_backend.models.dto.skill.SkillPostDTO;
import no.accelerate.lagalt_backend.models.dto.skill.SkillUpdateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class SkillMapper {
    public abstract Skill skillPostDtoToSkill(SkillPostDTO skillPostDTO);
    public abstract Skill skillUpdateDtoToSkill(SkillUpdateDTO skillPostDTO);

    @Mapping(target = "users", source = "users", qualifiedByName = "usersToIds")
    @Mapping(target = "projects", source = "projects", qualifiedByName = "projectsToIds")
    public abstract SkillDTO skillToSkillDto(Skill skill);

    public abstract Collection<SkillDTO> skillToSkillDto(Collection<Skill> skills);

    @Named("usersToIds")
    Set<Integer> map1(Set<User> source) {
        if (source == null) return null;
        return source.stream().map(p -> p.getId()
        ).collect(Collectors.toSet());
    }

    @Named("projectsToIds")
    Set<Integer> map2(Set<Project> source) {
        if (source == null) return null;
        return source.stream().map(p -> p.getId()
        ).collect(Collectors.toSet());
    }
}
