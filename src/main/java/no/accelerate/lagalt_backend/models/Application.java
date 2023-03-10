package no.accelerate.lagalt_backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Entity
@Getter
@Setter
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 200, nullable = false)
    private String motivation;
    // Status is initially set to 'Pending'. An admin can set the applications to either 'Accepted' or 'Denied'
    @Column(columnDefinition = "varchar(20) default 'Pending'")
    private String status;
    @ManyToOne
    @JoinColumn
    private User user;
    @ManyToOne
    @JoinColumn
    private Project project;
}