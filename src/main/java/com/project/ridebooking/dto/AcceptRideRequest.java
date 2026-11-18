package com.project.ridebooking.dto;

import jakarta.validation.constraints.NotNull;

public class AcceptRideRequest {
    @NotNull
    private Long driverId;

    public AcceptRideRequest() {}

    public Long getDriverId() { return driverId; }
    public void setDriverId(Long driverId) { this.driverId = driverId; }
}
