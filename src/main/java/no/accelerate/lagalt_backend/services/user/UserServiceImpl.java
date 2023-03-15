package no.accelerate.lagalt_backend.services.user;

import no.accelerate.lagalt_backend.models.Application;
import no.accelerate.lagalt_backend.models.Project;
import no.accelerate.lagalt_backend.models.User;
import no.accelerate.lagalt_backend.repositories.UserRepository;
import no.accelerate.lagalt_backend.utils.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
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
        userRepository.save(entity);
    }

    @Override
    public void deleteById(Integer integer) {
        userRepository.deleteById(integer);
    }
    @Override
    public Set<Project> findAllProjectsOwned(int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return user.getProjectsOwned();
    }

    @Override
    public Set<Project> findAllProjectsParticipated(int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return user.getProjectsParticipated();
    }

    @Override
    public Set<Application> findAllUserApplications(int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return user.getApplications();
    }

    @Override
    public void applyToProject(Application applicationUpdateDtoToApplication) {

        User user = applicationUpdateDtoToApplication.getUser();
        user.getApplications().add(applicationUpdateDtoToApplication);
        userRepository.save(user);
    }


}
