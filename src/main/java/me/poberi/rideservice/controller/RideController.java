package me.poberi.rideservice.controller;


import lombok.RequiredArgsConstructor;
import me.poberi.rideservice.dto.RideRequest;
import me.poberi.rideservice.dto.RideResponse;
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

    @PostMapping
    public ResponseEntity<RideResponse> createRide(@RequestBody RideRequest request) {
        RideResponse created = rideService.createRide(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("Location", "/rides/" + created.getId())
                .body(created);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<RideResponse> getRides(@RequestParam(required = false) Long passengerId) {
        if (passengerId != null) {
            return rideService.getAllRidesByPassenger(passengerId);
        }
        return rideService.getAllRides();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RideResponse getRide(@PathVariable Long id) {
        return rideService.getRide(id);
    }

    @PatchMapping("/{rideId}/passengers/{passengerId}")
    public ResponseEntity<RideResponse> addPassenger(
            @PathVariable Long rideId,
            @PathVariable Long passengerId
    ) {
        RideResponse response = rideService.addPassengerToRide(rideId, passengerId);
        return ResponseEntity.ok(response);
    }
}
