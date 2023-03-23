package no.accelerate.lagalt_backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import no.accelerate.lagalt_backend.models.enums.Category;

import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 50,nullable = false)
    private String title;
    @Column(length = 500,nullable = false)
    private String description;
    @Column(length = 100,nullable = false)
    private String status;
    @Column(length = 1000000)
    private String img_url;

    @Enumerated(EnumType.STRING)
    private Category category;

    @OneToMany(mappedBy = "project")
    Set<Application> applications;
    @ManyToOne
    @JoinColumn(name = "owner_id",nullable = false)
    private User owner;
    @ManyToMany(mappedBy = "projectsParticipated")
    private Set<User> members;

    @ManyToMany
    @JoinTable(
            name = "project_skill",
            joinColumns = {@JoinColumn(name = "project_id")},
            inverseJoinColumns = {@JoinColumn(name = "skill_id")}
    )
    private Set<Skill> skillsRequired;

    @OneToMany(mappedBy = "project")
    private Set<Comment> comments;

    @ElementCollection
    private Set<String> tags;

}
