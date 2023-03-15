package no.accelerate.lagalt_backend.services.skill;

import no.accelerate.lagalt_backend.models.Project;
import no.accelerate.lagalt_backend.models.Skill;
import no.accelerate.lagalt_backend.models.User;
import no.accelerate.lagalt_backend.repositories.ProjectRepository;
import no.accelerate.lagalt_backend.repositories.SkillRepository;
import no.accelerate.lagalt_backend.repositories.UserRepository;
import no.accelerate.lagalt_backend.utils.error.exceptions.ProjectNotFoundException;
import no.accelerate.lagalt_backend.utils.error.exceptions.SkillNotFoundException;
import no.accelerate.lagalt_backend.utils.error.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class SkillServiceImpl implements SkillService{
    private final SkillRepository skillRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public SkillServiceImpl(SkillRepository skillRepository,
                            UserRepository userRepository,
                            ProjectRepository projectRepository) {
        this.skillRepository = skillRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public Skill findById(Integer integer) {
        return skillRepository.findById(integer).orElseThrow(() -> new RuntimeException());
    }

    @Override
    public Skill add(Skill entity) {
        return skillRepository.save(entity);
    }

    @Override
    public void update(Skill entity) {
        skillRepository.save(entity);
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public Collection<Skill> findAll() {
        return skillRepository.findAll();
    }

    @Override
    public void updateUsers(int skill_id, int[] user_ids) {
        Skill skill = skillRepository.findById(skill_id).orElseThrow(() -> new SkillNotFoundException(skill_id));
        Set<User> users = new HashSet<>();

        for (int id: user_ids) {
            User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
            users.add(user);
            user.getSkills().add(skill);
        }

        skill.setUsers(users);
        skillRepository.save(skill);
    }

    @Override
    public Set<User> findAllUsers(int skill_id) {
        Skill skill = skillRepository.findById(skill_id).orElseThrow(() -> new SkillNotFoundException(skill_id));
        return skill.getUsers();
    }

    @Override
    public void updateProjects(int skill_id, int project_ids[]){
        Skill skill = skillRepository.findById(skill_id).orElseThrow(() -> new SkillNotFoundException(skill_id));
        Set<Project> projects = new HashSet<>();
        for (int id : project_ids) {
            Project project = projectRepository.findById(id).orElseThrow(() -> new ProjectNotFoundException(id));
            projects.add(project);
            project.getSkillsRequired().add(skill);
        }

        skill.setProjects(projects);
        skillRepository.save(skill);
    }

    @Override
    public Set<Project> findAllProjects(int skill_id) {
        Skill skill = skillRepository.findById(skill_id).orElseThrow(() -> new SkillNotFoundException(skill_id));
        return skill.getProjects();
    }


}
