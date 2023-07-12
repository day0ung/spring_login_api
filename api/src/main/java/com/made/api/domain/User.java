package com.made.api.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;


import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor

@Builder // Builder를 사용하려면 생성자가 있어야함 아래  AllArgsConstructor 추가
@AllArgsConstructor

@Entity
@Table(name = "USER")
public class User {

    @Id //primary key
    @Column(length = 20)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 100, nullable = false)
    private String username;

    @Column(length = 100, nullable = false)
    private String email;

    @Column( nullable = false)
    private String password;

    @Column(length = 50)
    private String role;

    @Column(length = 200)
    private String provider;

    @Column(length = 200)
    private String providerId;

    @Column(columnDefinition = "TINYINT")
    private int active = 1;

    @Column(unique = true)
    private String refreshToken;


    @Column
    @CreationTimestamp
    private LocalDateTime uptDt;

}
