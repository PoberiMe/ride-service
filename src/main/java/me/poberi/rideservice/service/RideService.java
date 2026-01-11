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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RideService {

    private final RideRepository rideRepository;
    private final RideMapper rideMapper;
    private final RestTemplate restTemplate;

    private final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

    public RideResponse createRide(RideRequest request) {

        Point startLocation = geometryFactory.createPoint(
                new Coordinate(request.getStartLocation().getLng(),
                        request.getStartLocation().getLat()));

        Point endLocation = geometryFactory.createPoint(
                new Coordinate(request.getEndLocation().getLng(),
                        request.getEndLocation().getLat()));

        Ride rideEntity = new Ride();
        rideEntity.setDriverId(request.getDriverId());
        rideEntity.setStartLocation(startLocation);
        rideEntity.setEndLocation(endLocation);
        rideEntity.setPassengerIds(request.getPassengerIds());
        rideEntity.setRideTime(request.getRideTime());
        rideEntity.setEndName(request.getEndName());
        rideEntity.setStartName(request.getStartName());
        rideEntity.setCapacity(request.getCapacity());

        Ride ride = rideRepository.save(rideEntity);

        Map<String, Object> routeRequest = Map.of(
                "startLocation", Map.of(
                        "lat", request.getStartLocation().getLat(),
                        "lng", request.getStartLocation().getLng()
                ),
                "endLocation", Map.of(
                        "lat", request.getEndLocation().getLat(),
                        "lng", request.getEndLocation().getLng()
                ),
                "startTime", request.getRideTime().toString(),
                "rideId", ride.getId(),
                "startName", ride.getStartName(),
                "endName", ride.getEndName()
        );

        String routeServiceUrl = "http://route-service:8080/routes";
        restTemplate.postForObject(routeServiceUrl, routeRequest, Void.class);

        return rideMapper.toResponse(ride);
    }

    public Page<RideResponse> getAllRides(Pageable pageable) {
        return rideRepository
                .findAll(pageable)
                .map(rideMapper::toResponse);
    }

    public RideResponse getRide(Long id) {
        return rideMapper.toResponse(
                rideRepository
                        .findById(id)
                        .orElseThrow(() -> new RideNotFoundException("Ride not found"))
        );
    }

    public List<RideResponse> getAllRidesByDriver(Long driverId) {
        List<Ride> rides = rideRepository.findByDriverId(driverId);

        return rides.stream()
                .map(rideMapper::toResponse).toList();
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

    public RideResponse removePassengerFromRide(Long rideId, Long passengerId) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new RideNotFoundException("Ride not found"));

        if (!ride.getPassengerIds().contains(passengerId)) {
            throw new PassengerAlreadyInRideException("Passenger not in ride " + rideId);
        }

        ride.getPassengerIds().remove(passengerId);
        return rideMapper.toResponse(rideRepository.save(ride));
    }

    public Page<RideResponse> getAllRidesByPassenger(Long passengerId, Pageable pageable) {
        return rideRepository
                .findByPassengerIdsContaining(passengerId, pageable)
                .map(rideMapper::toResponse);
    }

    public void deleteRide(Long id) {
        Ride ride = rideRepository.findById(id)
                .orElseThrow(() -> new RideNotFoundException("Ride not found"));
        rideRepository.delete(ride);
    }
}
