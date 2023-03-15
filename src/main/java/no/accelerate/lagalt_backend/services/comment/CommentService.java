package no.accelerate.lagalt_backend.services.comment;

import no.accelerate.lagalt_backend.models.Comment;
import no.accelerate.lagalt_backend.services.CrudService;

import java.util.Set;

public interface CommentService extends CrudService<Comment, Integer> {
    public Set<Comment> findAllReplies(int comment_id);
}
