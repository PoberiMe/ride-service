package me.poberi.rideservice.service;

import lombok.RequiredArgsConstructor;
import me.poberi.rideservice.dto.RideRequest;
import me.poberi.rideservice.model.Ride;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RideService {

    private final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

    public void createRide(RideRequest request) {

        // Convert to Point
        Point start = geometryFactory.createPoint(
                new Coordinate(request.startLocation().longitude(), request.startLocation().latitude())
        );

        Point end = geometryFactory.createPoint(
                new Coordinate(request.endLocation().longitude(), request.endLocation().latitude())
        );

        Ride ride = Ride.builder()
                .driverId(request.driverId())
                .startLocation(start)
                .endLocation(end)
                .passengerIds(request.passengerIds())
                .rideTime(request.rideTime())
                .build();

        // repository.save(ride);
        System.out.println(ride);
    }
}
