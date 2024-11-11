package com.dif76oq.postMicroservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Date;
import java.util.List;

@Data
@Entity
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
    @NonNull
    @Column
    private String password;

    public User (String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    private boolean enabled;
    @Column(name="verification_code")
    private String verificationCode;
    @Column(name="verification_expiration")
    private LocalDateTime verificationCodeExpiresAt;

    @Column(unique = true)
    private String phone;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Post> posts;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Review> reviews;

    private LocalDate dateBirth;

    private float avgRating = 0;

    private int numberOfReviews = 0;

    @Transient
    private int age;

    public int getAge() {
        if (dateBirth!=null) {
            return Period.between(dateBirth, LocalDate.now()).getYears();
        } else {
            return 0;
        }    }

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;


}
