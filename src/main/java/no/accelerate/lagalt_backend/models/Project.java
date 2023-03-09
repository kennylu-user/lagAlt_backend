package no.accelerate.lagalt_backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 50)
    private String title;
    @Column(length = 500)
    private String description;
    @Column(length = 100)
    private String status;

    @ManyToMany
    Set<User> contributors;
    @OneToMany(mappedBy = "project")
    Set<Application> applications;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;
    @ManyToMany(mappedBy = "projectsParticipated")
    private Set<User> members;
}
