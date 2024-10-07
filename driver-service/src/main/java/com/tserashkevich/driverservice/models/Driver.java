package com.tserashkevich.driverservice.models;

import com.tserashkevich.driverservice.models.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "drivers")
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(nullable = false, length = 50)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(unique = true, nullable = false, length = 12)
    private String phoneNumber;

    private LocalDate birthDate;

    private Boolean available;

    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL)
    private List<Car> cars = new ArrayList<>();
}