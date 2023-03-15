package no.accelerate.lagalt_backend.models.dto.comment;

import lombok.Data;


@Data
public class CommentPostDTO {
    private int id;
    private String message;
    private int user;
    private int project;
    private int repliedTo;
}
