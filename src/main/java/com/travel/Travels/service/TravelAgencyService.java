package com.travel.Travels.service;

import com.travel.Travels.entity.Activity;
import com.travel.Travels.entity.Destination;
import com.travel.Travels.entity.Passenger;
import com.travel.Travels.entity.TravelPackage;
import com.travel.Travels.enums.PassengerType;
import com.travel.Travels.repository.ActivityRepository;
import com.travel.Travels.repository.DestinationRepository;
import com.travel.Travels.repository.PassengerRepository;
import com.travel.Travels.repository.TravelPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TravelAgencyService {
    @Autowired
    private TravelPackageRepository travelPackageRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private DestinationRepository destinationRepository;

    public void printItinerary(Long travelPackageId) {
        TravelPackage travelPackage = travelPackageRepository.findById(travelPackageId).orElse(null);
        if (travelPackage != null) {
            System.out.println("Travel Package: " + travelPackage.getName());
            for (Destination destination : travelPackage.getDestinations()) {
                System.out.println("Destination: " + destination.getName());
                for (Activity activity : destination.getActivities()) {
                    System.out.println("Activity: " + activity.getName() +
                            ", Cost: " + activity.getCost() +
                            ", Capacity: " + activity.getCapacity() +
                            ", Description: " + activity.getDescription());
                }
            }
            System.out.println();
        }
    }

    public void printPassengerList(Long travelPackageId) {
        List<Passenger> passengers = passengerRepository.findByTravelPackageId(travelPackageId);
        if (!passengers.isEmpty()) {
            System.out.println("Passenger List for Travel Package ID " + travelPackageId + ":");
            for (Passenger passenger : passengers) {
                System.out.println("Passenger Name: " + passenger.getName() +
                        ", Passenger Number: " + passenger.getPassengerNumber());
            }
            System.out.println();
        } else {
            System.out.println("No passengers enrolled for Travel Package ID " + travelPackageId);
        }
    }

    public void printPassengerDetails(Long passengerId) {
        Passenger passenger = passengerRepository.findById(passengerId).orElse(null);
        if (passenger != null) {
            System.out.println("Passenger Name: " + passenger.getName() +
                    ", Passenger Number: " + passenger.getPassengerNumber() +
                    ", Balance: " + passenger.getBalance());
            for (Activity activity : passenger.getActivities()) {
                System.out.println("Activity: " + activity.getName() +
                        ", Destination: " + activity.getDestination().getName() +
                        ", Price Paid: " + calculatePrice(activity, passenger));
            }
            System.out.println();
        }
    }

    public void printAvailableActivities(Long travelPackageId) {
        TravelPackage travelPackage = travelPackageRepository.findById(travelPackageId).orElse(null);
        if (travelPackage != null) {
            System.out.println("Available Activities for " + travelPackage.getName() + ":");
            for (Destination destination : travelPackage.getDestinations()) {
                for (Activity activity : destination.getActivities()) {
                    int remainingCapacity = activity.getCapacity() - countEnrolledPassengers(activity);
                    if (remainingCapacity > 0) {
                        System.out.println("Activity: " + activity.getName() +
                                ", Destination: " + destination.getName() +
                                ", Remaining Capacity: " + remainingCapacity);
                    }
                }
            }
            System.out.println();
        }
    }

    private double calculatePrice(Activity activity, Passenger passenger) {
        double price = activity.getCost();

        switch (passenger.getPassengerType()) {
            case STANDARD:
                if (passenger.getBalance() >= price) {
                    passenger.setBalance(passenger.getBalance() - price);
                } else {
                    price = 0;
                }
                break;
            case GOLD:
                double discount = 0.1 * price;
                if (passenger.getBalance() >= discount) {
                    passenger.setBalance(passenger.getBalance() - discount);
                    price -= discount;
                } else {
                    price = 0;
                }
                break;
            case PREMIUM:
                price = 0;
                break;
        }

        return price;
    }

    private int countEnrolledPassengers(Activity activity) {
        List<Passenger> passengers = passengerRepository.findByActivities(activity);
        return passengers.size();
    }
}

