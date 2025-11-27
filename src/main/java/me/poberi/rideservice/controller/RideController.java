package me.poberi.rideservice.controller;


import lombok.RequiredArgsConstructor;
import me.poberi.rideservice.dto.RideRequest;
import me.poberi.rideservice.dto.RideResponse;
import me.poberi.rideservice.service.RideService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rides")
@RequiredArgsConstructor
public class RideController {

    private final RideService rideService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createRide(@RequestBody RideRequest rideRequest) {
        rideService.createRide(rideRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<RideResponse> getRides() {
        return rideService.getAllRides();
    }
}
