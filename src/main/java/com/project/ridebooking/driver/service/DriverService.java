package com.project.ridebooking.driver.service;

import com.project.ridebooking.common.GeoUtils;
import com.project.ridebooking.common.exception.NotFoundException;
import com.project.ridebooking.driver.entity.Driver;
import com.project.ridebooking.driver.entity.DriverStatus;
import com.project.ridebooking.driver.repository.DriverRepo;
import com.project.ridebooking.dto.DriverResponse;
import com.project.ridebooking.dto.NearbyDriverResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;

@Service
public class DriverService {
    private static final double KM_PER_DEGREE_LAT=111.0;
    private static final Duration LOCATION_MAX_AGE = Duration.ofMinutes(2);

    private DriverRepo driverRepo;

    public DriverService(DriverRepo driverRepo){
        this.driverRepo = driverRepo;
    }

    @Transactional
    public void updateLocation(Long id, Double lat, Double lng){
        Driver driver = driverRepo.findById(id).orElseThrow(()-> new NotFoundException("driver id:"+id+" not found"));
        driver.updateLocation(lat, lng);
    }

    @Transactional
    public List<NearbyDriverResponse> getNearbyDrivers(double lat, double lng, double radiusKm){
        double latDelta = radiusKm / KM_PER_DEGREE_LAT;
        double lngDelta = radiusKm / (KM_PER_DEGREE_LAT * Math.cos(Math.toRadians(lat)));
        List<Driver> candidates  = driverRepo.findCandidatesInBox(
                DriverStatus.AVAILABLE,
                lat - latDelta, lat + latDelta,
                lng - lngDelta, lng + lngDelta,
                Instant.now().minus(LOCATION_MAX_AGE)
        );
        return candidates.stream()
                .map(d -> new NearbyDriverResponse(
                        d.getId(), d.getName(), d.getVehicleNumber(),
                        GeoUtils.distanceKm(lat, lng, d.getLatitude(), d.getLongitude())))
                .filter(r -> r.getDistanceKm() <= radiusKm)
                .sorted(Comparator.comparingDouble(NearbyDriverResponse::getDistanceKm))
                .toList();

    }

    public DriverResponse findDriver(Long id){

       Driver driver= driverRepo.findById(id).orElseThrow(()->new NotFoundException("driver with id:"+id+" not found"));

       return DriverResponse.from(driver);

    }







}
