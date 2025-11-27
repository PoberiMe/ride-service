package me.poberi.rideservice.service;

import lombok.RequiredArgsConstructor;
import me.poberi.rideservice.dto.Location;
import me.poberi.rideservice.dto.RideRequest;
import me.poberi.rideservice.dto.RideResponse;
import me.poberi.rideservice.model.Ride;
import me.poberi.rideservice.repository.RideRepository;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.stereotype.Service;

import java.util.List;

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

        rideRepository.save(ride);
    }

    public List<RideResponse> getAllRides() {
        List<Ride> rides = rideRepository.findAll();

        return rides.stream().map(this::mapToRideResponse).toList();
    }

    private RideResponse mapToRideResponse(Ride ride) {
        return RideResponse.builder()
                .id(ride.getId())
                .driverId(ride.getDriverId())
                .startLocation(toLocation(ride.getStartLocation()))
                .endLocation(toLocation(ride.getEndLocation()))
                .passengerIds(ride.getPassengerIds())
                .rideTime(ride.getRideTime())
                .creationTime(ride.getCreationTime())
                .build();
    }

    private Location toLocation(Point point) {
        if (point == null) return null;
        return new Location(point.getY(), point.getX()); // lat = Y, lon = X
    }
}
