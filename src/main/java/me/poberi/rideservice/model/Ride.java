package me.poberi.rideservice.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import org.locationtech.jts.geom.Point;
import java.util.List;

import lombok.*;


@Entity
@Table(name = "ride")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ride {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long driverId;
    @Column(columnDefinition = "geometry(Point, 4326)")
    private Point startLocation;
    private String startName;
    @Column(columnDefinition = "geometry(Point, 4326)")
    private Point endLocation;
    private String endName;
    @ElementCollection
    private List<Long> passengerIds;
    private LocalDateTime rideTime;
    private LocalDateTime creationTime = LocalDateTime.now();
    private int capacity;
}