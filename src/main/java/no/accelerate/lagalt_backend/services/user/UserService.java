package no.accelerate.lagalt_backend.services.user;

import no.accelerate.lagalt_backend.services.CrudService;
import no.accelerate.lagalt_backend.models.User;

import java.util.Collection;

public interface UserService extends CrudService<User,Integer> {
    public Collection<User> findAll();
}
