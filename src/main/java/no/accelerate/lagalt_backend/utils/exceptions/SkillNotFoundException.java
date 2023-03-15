package no.accelerate.lagalt_backend.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class SkillNotFoundException extends RuntimeException {
    public SkillNotFoundException(int id) {
        super(String.format("Skill with %d not found", id));
    }
}
