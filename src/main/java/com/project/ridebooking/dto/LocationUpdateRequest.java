package com.project.ridebooking.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public class LocationUpdateRequest {

    @NotNull @DecimalMax("90") @DecimalMin("-90")
    private Double locationLat;
    @NotNull @DecimalMax("180") @DecimalMin("-180")
    private Double locationLng;

    LocationUpdateRequest(){

    }
    LocationUpdateRequest(double lat, double lng){
        this.locationLat = lat;
        this.locationLng = lng;

    }


    public Double getLocationLat() {
        return locationLat;
    }

    public Double getLocationLng() {
        return locationLng;
    }

    public void setLocationLat(Double locationLat) {
        this.locationLat = locationLat;
    }

    public void setLocationLng(Double locationLng) {
        this.locationLng = locationLng;
    }
}
