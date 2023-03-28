package no.accelerate.lagalt_backend.services.comment;

import no.accelerate.lagalt_backend.models.Comment;
import no.accelerate.lagalt_backend.repositories.CommentRepository;

import no.accelerate.lagalt_backend.utils.exceptions.CommentNotFoundException;


import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;

@Service
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment findById(Integer integer) {
        return commentRepository.findById(integer).orElseThrow(() -> new CommentNotFoundException(integer));
    }

    @Override
    public Collection<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public Comment add(Comment entity) {
        return commentRepository.save(entity);
    }

    @Override
    public void update(Comment entity) {
        commentRepository.save(entity);
    }

    @Override
    public void deleteById(Integer integer) {
        commentRepository.findById(integer).orElseThrow(()->new CommentNotFoundException(integer)).setProject(null);

        commentRepository.deleteById(integer);
    }

    @Override
    public Set<Comment> findAllReplies(int comment_id) {
        Comment comment = commentRepository.findById(comment_id).orElseThrow(() -> new CommentNotFoundException(comment_id));
        return comment.getReplies();
    }
}