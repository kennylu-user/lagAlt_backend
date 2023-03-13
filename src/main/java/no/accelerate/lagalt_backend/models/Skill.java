package no.accelerate.lagalt_backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "skill")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 50, nullable = false)
    private String title;

    @JsonIgnore
    @ManyToMany(mappedBy = "skills")
    private Set<User> users;

    @JsonIgnore
    @ManyToMany(mappedBy = "skillsRequired")
    private Set<Project> projects;

}
