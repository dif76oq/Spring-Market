package com.dif76oq.postMicroservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Data
@Entity
@Table(name = "reviews")
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NonNull
    private String title;
    @NonNull
    private String content;
    @NonNull
    private int rating;
    @ManyToOne
    @JoinColumn(name = "buyer_id", nullable = false)
    private User buyer;
    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private User seller;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;
}
