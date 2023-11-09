package com.example.sakilaapi.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;
import java.time.Year;
import java.util.Set;

@Entity
@Table(name = "film")
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long film_id;

    @Column(nullable = false)
    private String title;

    private String description;

    private Year releaseYear;

    @Column(nullable = false)
    private Integer rentalDuration;

    @Column(nullable = false)
    private Integer rentalRate;

    private Integer length;

    @Column(nullable = false)
    private Double replacementCost;

    @Enumerated(EnumType.STRING)
    private Rating rating;

    private String specialFeatures;

    @ManyToOne
    @JoinColumn(name = "language_id")
    private Language language;

    @ManyToOne
    @JoinColumn(name = "original_language_id")
    private Language originalLanguage;

    @UpdateTimestamp
    @Column(columnDefinition = "timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp last_update;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "film_actor",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id"))
    private Set<Actor> actors;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "film_category",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories;


}

