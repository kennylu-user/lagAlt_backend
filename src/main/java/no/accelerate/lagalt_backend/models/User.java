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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 50, nullable = false)
    private String f_name;
    @Column(length = 50, nullable = false)
    private String l_name;
    @Column(length = 100)
    private String description;
    @OneToMany(mappedBy = "user")
    private Set<Application> applications;
//    @OneToMany
//    private Set<Project> projectsCreated;
//    @ManyToMany
//    private Set<Project>  projectsContributedTo;
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

}
