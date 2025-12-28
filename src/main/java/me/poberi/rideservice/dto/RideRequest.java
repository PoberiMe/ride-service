package me.poberi.rideservice.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RideRequest {
    private Long driverId;
    private Location startLocation;
    private Location endLocation;
    private List<Long> passengerIds;
    private LocalDateTime rideTime;
}

