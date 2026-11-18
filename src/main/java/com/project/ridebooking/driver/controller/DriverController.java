package com.project.ridebooking.driver.controller;

import com.project.ridebooking.driver.entity.Driver;
import com.project.ridebooking.driver.service.DriverService;
import com.project.ridebooking.dto.DriverResponse;
import com.project.ridebooking.dto.LocationUpdateRequest;
import com.project.ridebooking.dto.NearbyDriverResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {
    private final DriverService driverService;

    public DriverController(DriverService driverService){
        this.driverService = driverService;
    }

    @PutMapping("/{id}/location")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateLocation(@PathVariable Long id, @Valid @RequestBody LocationUpdateRequest locationRequest){
        driverService.updateLocation(id, locationRequest.getLocationLat(), locationRequest.getLocationLng());
    }


    @GetMapping("/nearby")
    public List<NearbyDriverResponse> getNearbyDrivers(@RequestParam double lat,
                                                       @RequestParam double lng,
                                                       @RequestParam(defaultValue = "3") double radiusKm){

        return driverService.getNearbyDrivers(lat, lng,radiusKm);
    }

    @GetMapping("/{id}")
    public DriverResponse getDriver(@PathVariable Long id){

        System.out.println( driverService.findDriver(id));
        return driverService.findDriver(id);
    }



}
