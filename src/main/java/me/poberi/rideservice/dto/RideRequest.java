package me.poberi.rideservice.dto;

import java.time.LocalDateTime;
import java.util.List;

public record RideRequest(
        Long driverId,
        Location startLocation,
        Location endLocation,
        List<Long> passengerIds,
        LocalDateTime rideTime
) {}
