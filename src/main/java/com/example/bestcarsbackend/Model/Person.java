package com.example.bestcarsbackend.Model;

import com.example.bestcarsbackend.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "person_table")
@Component
public class Person {
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "user_sequence",
            strategy = GenerationType.SEQUENCE
    )
    @Column(name = "person_id")
    private Long id;
    @Column(name = "person_username")
    private String username;
    @Column(name = "person_firstName")
    private String firstName;
    @Column(name = "person_lastName")
    private String lastName;
    @Column(name = "person_email", unique = true)
    private String email;
    @Column(name = "person_password")
    private String password;
    @Column(nullable = false, updatable = false)
    private String personCode;
    private String imageUrl;
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

}
