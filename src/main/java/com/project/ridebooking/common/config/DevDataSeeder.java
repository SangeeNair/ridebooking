package com.project.ridebooking.common.config;

import com.project.ridebooking.driver.entity.Driver;
import com.project.ridebooking.driver.entity.DriverStatus;
import com.project.ridebooking.driver.repository.DriverRepo;
import com.project.ridebooking.rider.entity.Rider;
import com.project.ridebooking.rider.repository.RiderRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class DevDataSeeder implements CommandLineRunner {

    private final RiderRepo riders;
    private final DriverRepo drivers;

    public DevDataSeeder(RiderRepo riders, DriverRepo drivers) {
        this.riders = riders;
        this.drivers = drivers;
    }


    @Override
    public void run(String... args) throws Exception {
        if (riders.count() == 0) {
            riders.save(new Rider("Asha", "+1-555-0101"));
            drivers.save(new Driver("Ravi", "KA01AB1234", DriverStatus.AVAILABLE));
            drivers.save(new Driver("Meena", "KA02CD5678", DriverStatus.AVAILABLE));
        }
    }
}
