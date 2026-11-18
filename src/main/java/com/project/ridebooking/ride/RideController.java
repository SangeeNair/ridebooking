package com.project.ridebooking.ride;

import com.project.ridebooking.driver.repository.DriverRepo;
import com.project.ridebooking.dto.AcceptRideRequest;
import com.project.ridebooking.dto.RideRequest;
import com.project.ridebooking.dto.RideResponse;
import com.project.ridebooking.ride.repository.RideRepo;
import com.project.ridebooking.rider.repository.RiderRepo;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rides")
public class RideController {

    private final RideService rideService;

    public RideController(RideService rideService){
        this.rideService = rideService;
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RideResponse requestRide(@Valid @RequestBody RideRequest request) {
        return rideService.requestRide(request);
    }

    @GetMapping("/{id}")
    public RideResponse getRide(@PathVariable Long id) {
        return rideService.getRide(id);
    }

    @PostMapping("/{id}/accept")
    public RideResponse acceptRide(@PathVariable Long id,
                                   @Valid @RequestBody AcceptRideRequest request) {
        return rideService.acceptRideRequest(id, request.getDriverId());
    }

    @PostMapping("/{id}/arrive")
    public RideResponse markArriving(@PathVariable Long id) {
        return rideService.markArriving(id);
    }

    @PostMapping("/{id}/start")
    public RideResponse startRide(@PathVariable Long id) {
        return rideService.startRide(id);
    }

    @PostMapping("/{id}/complete")
    public RideResponse completeRide(@PathVariable Long id) {
        return rideService.completeRide(id);
    }

    @PostMapping("/{id}/cancel")
    public RideResponse cancelRide(@PathVariable Long id) {
        return rideService.cancelRide(id);
    }
}
