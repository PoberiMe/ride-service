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
    private String startName;
    private Location endLocation;
    private String endName;
    private List<Long> passengerIds;
    private LocalDateTime rideTime;
    private int capacity;
}

