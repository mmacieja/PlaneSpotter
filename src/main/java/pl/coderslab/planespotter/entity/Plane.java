package pl.coderslab.planespotter.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "planes")
public class Plane {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String registration;
    @Column(unique = true)
    private String icao24;

    @ManyToOne
    private Airline airline;
}
