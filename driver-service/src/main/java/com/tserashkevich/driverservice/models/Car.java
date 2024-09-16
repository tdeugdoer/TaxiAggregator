package com.tserashkevich.driverservice.models;

import com.tserashkevich.driverservice.models.enums.Color;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 10)
    private String number;

    @Column(nullable = false, length = 10)
    private String brand;

    @Column(nullable = false, length = 10)
    private String model;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Color color;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "driver_id")
    private Driver driver;
}
