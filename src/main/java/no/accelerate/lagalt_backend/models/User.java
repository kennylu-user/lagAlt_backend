package no.accelerate.lagalt_backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @Column(unique = true, nullable = false, updatable = false)
    private String id;

    @Column(length = 50, nullable = false)
    private String f_name;
    @Column(length = 50, nullable = false)
    private String l_name;
    @Column(length = 1000)
    private String description;


    @OneToMany(mappedBy = "user")
    private Set<Application> applications;
    @OneToMany(mappedBy = "owner")
    private Set<Project> projectsOwned;


    @ManyToMany
    @JoinTable(
            name = "user_projects_membership",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "project_id")}
    )
    private Set<Project> projectsParticipated;
    private boolean hidden;

    @ManyToMany
    @JoinTable(
            name = "user_skill",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "skill_id")}
    )
    private Set<Skill> skills;

    @OneToMany(mappedBy = "user")
    private Set<Comment> comments;
}
