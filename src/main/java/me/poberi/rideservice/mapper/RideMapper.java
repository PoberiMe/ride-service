package me.poberi.rideservice.mapper;

import me.poberi.rideservice.dto.Location;
import me.poberi.rideservice.dto.RideResponse;
import me.poberi.rideservice.model.Ride;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Component;

@Component
public class RideMapper {

    public RideResponse toResponse(Ride ride) {
        return new RideResponse(
                ride.getId(),
                ride.getDriverId(),
                toLocation(ride.getStartLocation()),
                ride.getStartName(),
                toLocation(ride.getEndLocation()),
                ride.getEndName(),
                ride.getPassengerIds(),
                ride.getRideTime(),
                ride.getCreationTime(),
                ride.getCapacity()
        );
    }

    private Location toLocation(Point point) {
        return new Location(
                point.getY(), // lat
                point.getX()
        );
    }
}
