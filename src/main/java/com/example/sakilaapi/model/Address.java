package com.example.sakilaapi.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;

@Entity
@Table(name = "address")
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long address_id;

    @Column(nullable = false)
    private String address;

    private String address2;

    @Column(nullable = false)
    private String district;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    private String postal_code;

    @Column(nullable = false)
    private String phone;

    @UpdateTimestamp
    @Column(columnDefinition = "timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp last_update;


}
