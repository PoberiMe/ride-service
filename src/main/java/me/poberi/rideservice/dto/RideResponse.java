package me.poberi.rideservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RideResponse {
    private Long id;
    private Long driverId;
    private Location startLocation;
    private Location endLocation;
    private List<Long> passengerIds;
    private LocalDateTime rideTime;
    private LocalDateTime creationTime;
}

