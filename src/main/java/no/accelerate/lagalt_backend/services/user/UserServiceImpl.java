package no.accelerate.lagalt_backend.services.user;

import no.accelerate.lagalt_backend.models.*;
import no.accelerate.lagalt_backend.repositories.UserRepository;
import no.accelerate.lagalt_backend.utils.exceptions.UserNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
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
    public User findById(String id) {
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
    public User addById(Jwt jwt) {
        User user = new User();
        user.setId(jwt.getClaimAsString("sub"));
        user.setF_name(jwt.getClaimAsString("name"));
        user.setHidden(false);
        userRepository.save(user);
        return user;
    }

    @Override
    public void update(User entity) {
        userRepository.save(entity);
    }

    @Override
    public void deleteById(String integer) {
        userRepository.deleteById(integer);
    }
    @Override
    public Set<Project> findAllProjectsOwned(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return user.getProjectsOwned();
    }

    @Override
    public Set<Project> findAllProjectsParticipated(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return user.getProjectsParticipated();
    }

    @Override
    public Set<Application> findAllUserApplications(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return user.getApplications();
    }

    @Override
    public void applyToProject(Application applicationUpdateDtoToApplication) {

        User user = applicationUpdateDtoToApplication.getUser();
        user.getApplications().add(applicationUpdateDtoToApplication);
        userRepository.save(user);
    }

    @Override
    public Set<Skill> findAllSkills(String id) {
        return this.findById(id).getSkills();
    }

    @Override
    public Set<Comment> findAllComments(String id) {
        return null;
    }


}
