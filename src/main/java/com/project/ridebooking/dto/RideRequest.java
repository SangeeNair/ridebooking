package com.project.ridebooking.dto;


import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public class RideRequest {

    @NotNull
    private Long riderId;


    @NotNull @DecimalMin("-90") @DecimalMax("90")
    private Double pickupLat;
    @NotNull @DecimalMin("-180") @DecimalMax("180")
    private Double pickupLng;
    @NotNull @DecimalMin("-90") @DecimalMax("90")
    private Double dropoffLat;
    @NotNull @DecimalMin("-180") @DecimalMax("180")
    private Double dropoffLng;


    public RideRequest(){

    }

    public double getPickupLat() {
        return pickupLat;
    }

    public double getPickupLng() {
        return pickupLng;
    }

    public double getDropoffLat() {
        return dropoffLat;
    }

    public double getDropoffLng() {
        return dropoffLng;
    }

    public void setRiderId(Long riderId) {
        this.riderId = riderId;
    }

    public void setPickupLat(Double pickupLat) {
        this.pickupLat = pickupLat;
    }

    public void setPickupLng(Double pickupLng) {
        this.pickupLng = pickupLng;
    }

    public void setDropoffLat(Double dropoffLat) {
        this.dropoffLat = dropoffLat;
    }

    public void setDropoffLng(Double dropoffLng) {
        this.dropoffLng = dropoffLng;
    }

    public Long getRiderId() {
        return riderId;
    }
}
