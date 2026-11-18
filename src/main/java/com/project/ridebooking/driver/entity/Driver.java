package com.project.ridebooking.driver.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name="drivers")
public class Driver {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String vehicleNumber;

    @Enumerated(EnumType.STRING)
    private DriverStatus status = DriverStatus.OFFLINE;


    private Double locationLat;
    private Double locationLng;
    private Instant locationUpdatedAt;

    protected Driver() {}


    public Driver(String name, String vehicleNumber, DriverStatus status){

        this.name=name;
        this.vehicleNumber = vehicleNumber;
        this.status = status;

    }

    public void updateLocation(double latitude, double longitude) {
        this.locationLat = latitude;
        this.locationLng = longitude;
        this.locationUpdatedAt = Instant.now();
    }

    public Double getLatitude() { return locationLat; }
    public Double getLongitude() { return locationLng; }
    public Instant getLocationUpdatedAt() { return locationUpdatedAt; }

    @Version
    private Long version;

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

    public void markAvailable(){
        this.status = DriverStatus.AVAILABLE;
    }

    public boolean isAvailable(){
       return  this.status == DriverStatus.AVAILABLE;
    }

    public void markBusy(){
        if(!isAvailable()){
            throw new IllegalStateException("Driver " + id + " is not available");
        }
        this.status = DriverStatus.BUSY;
    }

    public void goOnline() {
        if (status == DriverStatus.BUSY) {
            throw new IllegalStateException("Driver " + id + " is on a trip");
        }
        this.status = DriverStatus.AVAILABLE;
    }

    public void goOffline() {
        if (status == DriverStatus.BUSY) {
            throw new IllegalStateException("Driver " + id + " cannot go offline during a trip");
        }
        this.status = DriverStatus.OFFLINE;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", vehicleNumber='" + vehicleNumber + '\'' +
                ", status=" + status +
                ", locationLat=" + locationLat +
                ", locationLng=" + locationLng +
                ", locationUpdatedAt=" + locationUpdatedAt +
                ", version=" + version +
                '}';
    }
}
