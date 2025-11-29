package me.poberi.rideservice.repository;

import me.poberi.rideservice.model.Ride;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RideRepository extends JpaRepository<Ride, Long> {
    List<Ride> findByDriverId(Long driverId);
    List<Ride> findByPassengerIdsContaining(Long passengerId);
}
