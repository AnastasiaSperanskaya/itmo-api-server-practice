package com.example.APIServer.entities;

import lombok.*;
import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "profiles", schema = "project")
@Data
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", schema = "project", sequenceName = "user_id_sequence", allocationSize = 1)
    @Column(name = "user_id", updatable = false, nullable = false)
    private int userId;
    private String username;
    private String email;

    @ManyToOne
    @JoinColumn(name = "status_id_fk", referencedColumnName = "status_id")
    private StatusEntity status;

    public UserEntity(StatusEntity status, String username, String email) {
        this.username = username;
        this.email = email;
        this.status = status;
    }
}