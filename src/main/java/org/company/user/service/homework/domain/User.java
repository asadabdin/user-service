package org.company.user.service.homework.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.OffsetDateTime;

import static javax.persistence.GenerationType.SEQUENCE;

@Data
@Entity
@Table(name = "user", schema = "users")
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = SEQUENCE)
    @Column(updatable = false, nullable = false)
    private Integer id;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private String position;

    @Column(nullable = false)
    private String gitHubUrl;

    @CreatedDate
    @Column(nullable = false)
    private OffsetDateTime createdOn;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime updatedOn;
}
