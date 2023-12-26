package com.travel.Travels.repository;

import com.travel.Travels.entity.Activity;
import com.travel.Travels.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    List<Passenger> findByActivities(Activity activity);

    List<Passenger> findByTravelPackageId(Long travelPackageId);
}
