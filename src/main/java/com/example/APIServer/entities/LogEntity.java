package com.example.APIServer.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Table(name = "logs", schema = "project")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "log_seq")
    @SequenceGenerator(name = "log_seq", schema = "project", sequenceName = "logs_id_sequence", allocationSize = 1)
    @Column(name = "log_id", updatable = false, nullable = false)
    private int logId;

    @ManyToOne
    @JoinColumn(name = "user_id_fk", referencedColumnName = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "status_id_fk", referencedColumnName = "status_id")
    private StatusEntity status;

    private long changedTime;

    public LogEntity(UserEntity user, StatusEntity status, long changedTime) {
        this.user = user;
        this.status = status;
        this.changedTime = changedTime;
    }
}

