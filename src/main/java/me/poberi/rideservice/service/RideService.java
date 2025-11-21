package me.poberi.rideservice.service;

import lombok.RequiredArgsConstructor;
import me.poberi.rideservice.dto.RideRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RideService {

    public void createRide(RideRequest rideRequest) {
        System.out.println(rideRequest);
    }

}
