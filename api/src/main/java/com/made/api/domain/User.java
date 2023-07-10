package com.made.api.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor

@Entity
@Table(name = "USER")
public class User {

    @Id //primary key
    @Column(length = 20)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(columnDefinition = "TINYINT")
    private int active = 1;

    @Column(unique = true)
    private String refreshToken;

    @Column(length = 50)
    private String role;


    @Column
    @CreationTimestamp
    private LocalDateTime uptDt;

}
