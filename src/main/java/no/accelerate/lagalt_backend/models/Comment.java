package no.accelerate.lagalt_backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column()
    private String message;

    @ManyToOne
    @JoinColumn
    private User user;

    @ManyToOne
    @JoinColumn
    private Project project;

    @ManyToOne
    @JoinColumn(name = "replied_to_id")
    private Comment repliedTo;

    @OneToMany(mappedBy = "repliedTo", cascade = CascadeType.ALL)
    private Set<Comment> replies;
}
