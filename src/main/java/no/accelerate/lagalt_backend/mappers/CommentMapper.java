package no.accelerate.lagalt_backend.mappers;

import no.accelerate.lagalt_backend.models.Comment;
import no.accelerate.lagalt_backend.models.Project;
import no.accelerate.lagalt_backend.models.User;
import no.accelerate.lagalt_backend.models.dto.comment.CommentDTO;
import no.accelerate.lagalt_backend.models.dto.comment.CommentPostDTO;
import no.accelerate.lagalt_backend.models.dto.comment.CommentUpdateDTO;
import no.accelerate.lagalt_backend.services.comment.CommentService;
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
public abstract class CommentMapper {
    @Autowired
    UserService userService;
    @Autowired
    ProjectService projectService;
    @Autowired
    CommentService commentService;

    @Mapping(target = "user", source = "user", qualifiedByName = "idToUser")
    @Mapping(target = "project", source = "project", qualifiedByName = "idToProject")
    @Mapping(target = "repliedTo", source = "repliedTo", qualifiedByName = "idToRepliedTo")
    public abstract Comment commentPostDtoToComment(CommentPostDTO commentPostDto);
    public abstract Comment commentUpdateDtoToComment(CommentUpdateDTO commentUpdateDTO);

    @Mapping(target = "user", source = "user", qualifiedByName = "usersToIds")
    @Mapping(target = "project", source = "project", qualifiedByName = "projectsToIds")
    @Mapping(target = "repliedTo", source = "repliedTo", qualifiedByName = "repliesToIds")
    @Mapping(target = "replies", source = "replies", qualifiedByName = "repliesToIds2")
    public abstract CommentDTO commentToCommentDTO(Comment comment);
    public abstract Collection<CommentDTO> commentToCommentDTO(Collection<Comment> comments);

    @Named("usersToIds")
    int map1(User source) {
        if (source == null) return -1;
        return source.getId();
    }

    @Named("projectsToIds")
    int map2(Project source) {
        if (source == null) return -1;
        return source.getId();
    }

    @Named("repliesToIds")
    int map3(Comment source) {
        if (source == null) return -1;
        return source.getId();
    }

    @Named("repliesToIds2")
    Set<Integer> map4(Set<Comment> source) {
        if (source == null) return null;
        return source.stream().map(p -> p.getId()
        ).collect(Collectors.toSet());
    }

    @Named("idToUser")
    User mapToUser(Integer id) {
        return userService.findById(id);
    }

    @Named("idToProject")
    Project mapToProject(Integer id) {
        return projectService.findById(id);
    }

    @Named("idToRepliedTo")
    Comment mapToComment(Integer id) {
        return commentService.findById(id);
    }
}
