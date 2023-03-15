package no.accelerate.lagalt_backend.models.dto.comment;

import lombok.Data;
import no.accelerate.lagalt_backend.models.Comment;
import no.accelerate.lagalt_backend.models.Project;
import no.accelerate.lagalt_backend.models.User;

import java.util.Set;

@Data
public class CommentUpdateDTO {
    private int id;
    private String message;
}
