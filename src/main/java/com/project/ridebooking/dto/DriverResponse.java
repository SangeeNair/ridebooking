package com.project.ridebooking.dto;

import com.project.ridebooking.driver.entity.Driver;
import com.project.ridebooking.driver.entity.DriverStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.Instant;

public class DriverResponse {

    private Long id;
    private String name;
    private String vehicleNumber;

    @Enumerated(EnumType.STRING)
    private DriverStatus status = DriverStatus.OFFLINE;


    private Double locationLat;
    private Double locationLng;


    public DriverResponse(){


    }
    public DriverResponse(Long driverId, String name, String vehicleNumber,
                          DriverStatus status, Double locationLat, Double locationLng){
        this.id = driverId;
        this.name =name;
        this.locationLat = locationLat;
        this.locationLng = locationLng;
        this.status = status;
        this.vehicleNumber = vehicleNumber;

    }
    public static DriverResponse from(Driver driver){
        return new DriverResponse(driver.getId(), driver.getName(), driver.getVehicleNumber(),
                driver.getStatus(), driver.getLatitude(), driver.getLongitude());
    }

    @Override
    public String toString() {
        return "DriverResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", vehicleNumber='" + vehicleNumber + '\'' +
                ", status=" + status +
                ", locationLat=" + locationLat +
                ", locationLng=" + locationLng +
                '}';
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public DriverStatus getStatus() {
        return status;
    }

    public Double getLocationLat() {
        return locationLat;
    }

    public Double getLocationLng() {
        return locationLng;
    }
}
