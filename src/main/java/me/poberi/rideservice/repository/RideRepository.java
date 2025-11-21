package me.poberi.rideservice.repository;

import me.poberi.rideservice.model.Ride;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RideRepository extends JpaRepository<Ride, Long> {
}
