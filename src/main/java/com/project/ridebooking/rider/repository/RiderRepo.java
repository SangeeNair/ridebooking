package com.project.ridebooking.rider.repository;

import com.project.ridebooking.rider.entity.Rider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RiderRepo extends JpaRepository<Rider, Long> {


}


