package com.dif76oq.NotificationMicroservice.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NonNull
    @Column(unique = true)
    private String email;
    @NonNull
    @Column(unique = true)
    private String username;
    @Column(name="verification_code")
    private String verificationCode;


}
