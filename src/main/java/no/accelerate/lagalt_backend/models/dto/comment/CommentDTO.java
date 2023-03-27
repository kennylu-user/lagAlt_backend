package no.accelerate.lagalt_backend.models.dto.comment;

import lombok.Data;

import java.util.Set;

@Data
public class CommentDTO {
    private int id;
    private String message;
    private String user;
    private int project;
    private int repliedTo;
    private Set<Integer> replies;
}
