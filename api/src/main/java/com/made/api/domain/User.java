package com.made.api.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor

@Entity
@Table(name = "USER")
public class User {

    @Id
    @Column(length = 20)
    private String id;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(columnDefinition = "TINYINT")
    private int active = 1;

    @Column(unique = true)
    private String refreshToken;

    @Column(length = 2)
    private String gender;

    @Column(length = 5)
    private int age;

    @Column
    @UpdateTimestamp
    private LocalDateTime uptDt;

}
