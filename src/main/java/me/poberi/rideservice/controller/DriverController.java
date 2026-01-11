package me.poberi.rideservice.controller;

import lombok.RequiredArgsConstructor;
import me.poberi.rideservice.dto.RideResponse;
import me.poberi.rideservice.service.RideService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/drivers")
@RequiredArgsConstructor
public class DriverController {

    private final RideService rideService;

    // TODO verjetno ne rabim vec tega ctrl
}
