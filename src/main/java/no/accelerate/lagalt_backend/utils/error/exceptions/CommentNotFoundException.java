package no.accelerate.lagalt_backend.utils.error.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class CommentNotFoundException extends RuntimeException{
    public CommentNotFoundException(int id) {
        super(String.format("Comment with %d not found",id));
    }
}
