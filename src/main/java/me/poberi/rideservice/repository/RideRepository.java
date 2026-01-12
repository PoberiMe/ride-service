package me.poberi.rideservice.repository;

import me.poberi.rideservice.model.Ride;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RideRepository extends JpaRepository<Ride, Long> {
    Page<Ride> findByDriverId(Long driverId, Pageable pageable);
    Page<Ride> findByPassengerIdsContaining(Long passengerId, Pageable pageable);
}
