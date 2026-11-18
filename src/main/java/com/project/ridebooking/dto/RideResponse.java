package com.project.ridebooking.dto;

import com.project.ridebooking.ride.entity.Ride;
import com.project.ridebooking.ride.entity.RideStatus;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public class RideResponse {


    private Long driverId;

    @NotNull
    private Long riderId;

    @NotNull
    private Long rideId;

    private RideStatus rideStatus;

    private Instant requestedAt;
    public RideResponse(Long id, Long riderId, Long driverId,
                        RideStatus status, Instant requestedAt) {
        this.rideId = id;
        this.riderId = riderId;
        this.driverId = driverId;
        this.rideStatus = status;
        this.requestedAt = requestedAt;
    }

    public static RideResponse from(Ride ride) {
        return new RideResponse(
                ride.getId(),
                ride.getRider().getId(),
                ride.getDriver() == null ? null : ride.getDriver().getId(),
                ride.getStatus(),
                ride.getRequestedAt());
    }


    public Long getDriverId() {
        return driverId;
    }

    public Long getRiderId() {
        return riderId;
    }

    public Long getRideId() {
        return rideId;
    }

    public RideStatus getRideStatus() {
        return rideStatus;
    }

    public Instant getRequestedAt() {
        return requestedAt;
    }
}
