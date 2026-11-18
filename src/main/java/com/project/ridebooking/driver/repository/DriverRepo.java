package com.project.ridebooking.driver.repository;

import com.project.ridebooking.driver.entity.Driver;
import com.project.ridebooking.driver.entity.DriverStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface DriverRepo extends JpaRepository<Driver, Long> {
    List<Driver> findByStatus(DriverStatus status);

    @Query("""
            select d from Driver d
            where d.status =:status
            and d.locationLat between :minLat and :maxLat
            and d.locationLng between :minLng and :maxLng
            and d.locationUpdatedAt > :since
            """)
    List<Driver> findCandidatesInBox(@Param("status") DriverStatus status,
                                     @Param("minLat") double minLat,
                                     @Param("maxLat") double maxLat,
                                     @Param("minLng") double minLng,
                                     @Param("maxLng") double maxLng,
                                     @Param("since") Instant since);


}
