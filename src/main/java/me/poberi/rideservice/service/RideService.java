package me.poberi.rideservice.service;

import lombok.RequiredArgsConstructor;
import me.poberi.rideservice.dto.RideRequest;
import me.poberi.rideservice.dto.RideResponse;
import me.poberi.rideservice.exception.PassengerAlreadyInRideException;
import me.poberi.rideservice.exception.RideNotFoundException;
import me.poberi.rideservice.mapper.RideMapper;
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
    private final RideMapper rideMapper;

    private final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

    public RideResponse createRide(RideRequest request) {

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

        return rideMapper.toResponse(rideRepository.save(ride));
    }

    public List<RideResponse> getAllRides() {
        return rideRepository
                .findAll().stream()
                .map(rideMapper::toResponse)
                .toList();
    }

    public RideResponse addPassengerToRide(Long rideId, Long passengerId) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new RideNotFoundException("Ride not found"));

        if (ride.getPassengerIds().contains(passengerId)) {
            throw new PassengerAlreadyInRideException("Passenger already exists in ride " +  rideId);
        }

        ride.getPassengerIds().add(passengerId);
        return rideMapper.toResponse(rideRepository.save(ride));
    }
}
