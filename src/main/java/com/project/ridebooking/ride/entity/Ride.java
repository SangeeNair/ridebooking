package com.project.ridebooking.ride.entity;

import com.project.ridebooking.driver.entity.Driver;
import com.project.ridebooking.rider.entity.Rider;
import jakarta.persistence.*;

import java.time.Instant;


@Entity
@Table(name="rides")
public class Ride {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne (optional = false, fetch=FetchType.LAZY)
    private Rider rider;

    @ManyToOne (fetch=FetchType.LAZY)
    private Driver driver;

    private double pickupLat;
    private double pickupLng;
    private double dropoffLat;
    private double dropoffLng;

    @Enumerated(EnumType.STRING)
    private RideStatus status;

    private Instant requestedAt;

    @Version
    private Long version;

    protected Ride(){};

    public Ride(Rider rider, double pickupLat, double pickupLng,
               double dropoffLat, double dropoffLng) {
        this.rider = rider;
        this.pickupLat = pickupLat;
        this.pickupLng = pickupLng;
        this.dropoffLat = dropoffLat;
        this.dropoffLng = dropoffLng;
        this.status = RideStatus.REQUESTED;
        this.requestedAt = Instant.now();
    }

    public void transitionTo(RideStatus next){
       if(!status.canTransitionTo(next)){
           throw new IllegalStateException("Cannot move ride from " + status + " to " + next);
        }
       this.status = next;
    }

    public void assignDriver(Driver driver) {
        transitionTo(RideStatus.DRIVER_ASSIGNED);  // fails unless the ride is REQUESTED
        driver.markBusy();                          // fails unless the driver is AVAILABLE
        this.driver = driver;
    }

    public void cancel(){
        transitionTo(RideStatus.CANCELLED);
        if(driver!=null){
            driver.markAvailable();
        }
    }
    public void complete() {
        transitionTo(RideStatus.COMPLETED);
        driver.markAvailable();
    }

    public Long getId() {
        return id;
    }

    public Rider getRider() {
        return rider;
    }

    public Driver getDriver() {
        return driver;
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

    public RideStatus getStatus() {
        return status;
    }

    public Instant getRequestedAt() {
        return requestedAt;
    }
}
