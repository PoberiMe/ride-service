package me.poberi.rideservice.dto;

import lombok.*;

@Data
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Location{
    double lat;
    double lng;
}

