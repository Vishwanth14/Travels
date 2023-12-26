package com.travel.Travels.controller;

import com.travel.Travels.service.TravelAgencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/travel-packages")
public class TravelPackageController {
        @Autowired
        private TravelAgencyService travelAgencyService;

        @GetMapping("/itinerary/{id}")
        public void printItinerary(@PathVariable Long id) {
                travelAgencyService.printItinerary(id);
        }

        @GetMapping("/passenger-list/{id}")
        public void printPassengerList(@PathVariable Long id) {
                travelAgencyService.printPassengerList(id);
        }

        @GetMapping("/passenger-details/{id}")
        public void printPassengerDetails(@PathVariable Long id) {
                travelAgencyService.printPassengerDetails(id);
        }

        @GetMapping("/available-activities/{id}")
        public void printAvailableActivities(@PathVariable Long id) {
                travelAgencyService.printAvailableActivities(id);
        }
}


