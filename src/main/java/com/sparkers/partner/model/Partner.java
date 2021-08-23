package com.sparkers.partner.model;

import lombok.*;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "partners")
public class Partner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "reference", unique = true, nullable = false)
    private String reference;

    @Column(name = "locale", nullable = false)
    private String locale;

    @Column(name = "expiration_time", nullable = false)
    private ZonedDateTime expirationTime;
}
