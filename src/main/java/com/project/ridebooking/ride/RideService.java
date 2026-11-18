package com.project.ridebooking.ride;

import com.project.ridebooking.common.exception.NotFoundException;
import com.project.ridebooking.driver.entity.Driver;
import com.project.ridebooking.driver.repository.DriverRepo;
import com.project.ridebooking.dto.RideRequest;
import com.project.ridebooking.dto.RideResponse;
import com.project.ridebooking.ride.entity.Ride;
import com.project.ridebooking.ride.entity.RideStatus;
import com.project.ridebooking.ride.repository.RideRepo;
import com.project.ridebooking.rider.entity.Rider;
import com.project.ridebooking.rider.repository.RiderRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class RideService {

    private final RideRepo rideRepo;
    private final RiderRepo riderRepo;
    private final DriverRepo driverRepo;

    public RideService(RideRepo rideRepo, RiderRepo riderRepo,DriverRepo driverRepo){
        this.rideRepo = rideRepo; //constructor injection
        this.riderRepo = riderRepo;
        this.driverRepo = driverRepo;
    }
    // ---------- Helper ----------

    private Ride findRide(Long rideId) {
        return rideRepo.findById(rideId)
                .orElseThrow(() -> new NotFoundException("Ride " + rideId + " not found"));
    }

    @Transactional
    public RideResponse requestRide(RideRequest rideRequest){

      Rider rider = riderRepo.findById(rideRequest.getRiderId()).orElseThrow(()->
              new NotFoundException("Rider "+rideRequest.getRiderId()+ " not found"));

      Ride ride = new Ride(rider, rideRequest.getPickupLat(), rideRequest.getPickupLng(), rideRequest.getDropoffLat(), rideRequest.getDropoffLng());

      return RideResponse.from(rideRepo.save(ride));


    }

    @Transactional
    public RideResponse getRide(Long id) {
        return rideRepo.findById(id)
                .map(RideResponse::from)
                .orElseThrow(() -> new NotFoundException("Ride " + id + " not found"));
    }

    @Transactional
    public RideResponse acceptRideRequest(Long rideId, Long driverId){
        Ride ride = rideRepo.findById(rideId).orElseThrow(()->new NotFoundException("ride with id:"+rideId+" not found"));
        Driver driver = driverRepo.findById(driverId).orElseThrow(()->new NotFoundException("driver with id:"+driverId+" not found"));

        ride.assignDriver(driver);
        return RideResponse.from(ride);
    }

    @Transactional
    public RideResponse markArriving(Long rideId){
        Ride ride = findRide(rideId);
        ride.transitionTo(RideStatus.DRIVER_ARRIVING);
        return RideResponse.from(ride);
    }
    @Transactional
    public RideResponse startRide(Long rideId) {
        Ride ride = findRide(rideId);
        ride.transitionTo(RideStatus.IN_PROGRESS);
        return RideResponse.from(ride);
    }

    @Transactional
    public RideResponse completeRide(Long rideId) {
        Ride ride = findRide(rideId);
        ride.complete();             // ride → COMPLETED, driver → AVAILABLE
        return RideResponse.from(ride);
    }

    @Transactional
    public RideResponse cancelRide(Long rideId) {
        Ride ride = findRide(rideId);
        ride.cancel();               // ride → CANCELLED, driver freed if assigned
        return RideResponse.from(ride);
    }

}
