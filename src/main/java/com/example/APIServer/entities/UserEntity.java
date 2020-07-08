package com.example.APIServer.entities;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name = "users")
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    @NonNull
    private StatusEntity statusEntity;

    @Column(length = 50)
    @NonNull
    private String username;

    @Email
    private String email;

    @Column(name = "last_status_changed_time")
    private Long lastStatusChangedTime;

    public UserEntity(StatusEntity statusEntity, String username, @Email String email) {
        this.statusEntity = statusEntity;
        this.username = username;
        this.email = email;
    }

    public void setStatusEntity(StatusEntity statusEntity) {
        this.statusEntity = statusEntity;
        lastStatusChangedTime = new Date().getTime();
    }

    public Object getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.email;
    }
}