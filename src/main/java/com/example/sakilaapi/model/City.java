package com.example.sakilaapi.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;

@Entity
@Table(name = "city")
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long city_id;

    @Column(nullable = false)
    private String city;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @UpdateTimestamp
    @Column(columnDefinition = "timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp last_update;

}
