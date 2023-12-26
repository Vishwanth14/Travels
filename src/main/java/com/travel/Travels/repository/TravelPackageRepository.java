package com.travel.Travels.repository;

import com.travel.Travels.entity.Activity;
import com.travel.Travels.entity.Passenger;
import com.travel.Travels.entity.TravelPackage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TravelPackageRepository extends JpaRepository<TravelPackage, Long> {

}
