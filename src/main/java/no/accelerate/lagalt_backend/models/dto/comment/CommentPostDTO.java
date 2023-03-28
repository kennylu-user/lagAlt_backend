package no.accelerate.lagalt_backend.models.dto.comment;

import lombok.Data;


@Data
public class CommentPostDTO {
    private Integer id;
    private String message;
    private String user;
    private int project;
    private Integer repliedTo;
}
