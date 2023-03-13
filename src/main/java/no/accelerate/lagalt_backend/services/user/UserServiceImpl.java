package no.accelerate.lagalt_backend.services.user;

import no.accelerate.lagalt_backend.models.User;
import no.accelerate.lagalt_backend.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findById(Integer integer) {
        return userRepository.findById(integer).orElseThrow(() -> new RuntimeException());
    }

    @Override
    public Collection<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User add(User entity) {
        return userRepository.save(entity);

    }

    @Override
    public void update(User entity) {

    }

    @Override
    public void deleteById(Integer integer) {

    }




}
