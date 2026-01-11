package me.poberi.rideservice.controller;


import lombok.RequiredArgsConstructor;
import me.poberi.rideservice.dto.RideRequest;
import me.poberi.rideservice.dto.RideResponse;
import me.poberi.rideservice.service.RideService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
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
    public Page<RideResponse> getRides(
            @RequestParam(required = false) Long passengerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        if (passengerId != null) {
            return rideService.getAllRidesByPassenger(passengerId, pageable);
        }
        return rideService.getAllRides(pageable);
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

    @PatchMapping("/{rideId}/passengers/{passengerId}/remove")
    public ResponseEntity<RideResponse> removePassenger(
            @PathVariable Long rideId,
            @PathVariable Long passengerId
    ) {
        RideResponse response = rideService.removePassengerFromRide(rideId, passengerId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRide(@PathVariable Long id) {
        rideService.deleteRide(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/drivers/{driverId}")
    @ResponseStatus(HttpStatus.OK)
    public List<RideResponse> getRidesByDriver(@PathVariable Long driverId) {
        return rideService.getAllRidesByDriver(driverId);
    }
}
