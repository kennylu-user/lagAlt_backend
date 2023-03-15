package no.accelerate.lagalt_backend.models.dto.comment;

import lombok.Data;
import no.accelerate.lagalt_backend.models.Comment;
import no.accelerate.lagalt_backend.models.Project;
import no.accelerate.lagalt_backend.models.User;

import java.util.Set;

@Data
public class CommentDTO {
    private int id;
    private String message;
    private int user;
    private int project;
    private int repliedTo;
    private Set<Integer> replies;
}
