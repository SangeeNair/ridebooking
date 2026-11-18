package com.project.ridebooking.dto;

public class NearbyDriverResponse {
    private final Long driverId;
    private final String name;
    private final String vehicleNumber;
    private final double distanceKm;

    public NearbyDriverResponse(Long driverId, String name, String vehicleNumber, double distanceKm) {
        this.driverId = driverId;
        this.name = name;
        this.vehicleNumber = vehicleNumber;
        this.distanceKm = distanceKm;
    }

    public Long getDriverId() { return driverId; }
    public String getName() { return name; }
    public String getVehicleNumber() { return vehicleNumber; }
    public double getDistanceKm() { return distanceKm; }
}
