package me.poberi.rideservice.controller;


import lombok.RequiredArgsConstructor;
import me.poberi.rideservice.dto.RideRequest;
import me.poberi.rideservice.dto.RideResponse;
import me.poberi.rideservice.mapper.RideMapper;
import me.poberi.rideservice.model.Ride;
import me.poberi.rideservice.service.RideService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rides")
@RequiredArgsConstructor
public class RideController {

    private final RideService rideService;
    private final RideMapper rideMapper;

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

    @PatchMapping("/{rideId}/passengers/{passengerId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<RideResponse> addPassenger(
            @PathVariable Long rideId,
            @PathVariable Long passengerId)
    {
        Ride updatedRide = rideService.addPassengerToRide(rideId, passengerId);
        return ResponseEntity.ok(rideMapper.toResponse(updatedRide));
    }
}
