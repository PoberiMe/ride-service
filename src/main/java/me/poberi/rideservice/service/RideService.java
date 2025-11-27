package me.poberi.rideservice.service;

import lombok.RequiredArgsConstructor;
import me.poberi.rideservice.dto.RideRequest;
import me.poberi.rideservice.model.Ride;
import me.poberi.rideservice.repository.RideRepository;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RideService {

    private final RideRepository rideRepository;

    private final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

    public void createRide(RideRequest request) {

        Point startLocation = geometryFactory.createPoint(
                new Coordinate(request.startLocation().latitude(),
                               request.startLocation().longitude()));

        Point endLocation = geometryFactory.createPoint(
                new Coordinate(request.endLocation().latitude(),
                               request.endLocation().longitude()));

        Ride ride = new Ride();
        ride.setDriverId(request.driverId());
        ride.setStartLocation(startLocation);
        ride.setEndLocation(endLocation);
        ride.setPassengerIds(request.passengerIds());
        ride.setRideTime(request.rideTime());

        System.out.println(ride);
        int a = 12;
        rideRepository.save(ride);
    }
}
