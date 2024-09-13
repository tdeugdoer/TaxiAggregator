package com.tserashkevich.rideservice.repositories;


import com.tserashkevich.rideservice.models.Ride;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RideRepository extends MongoRepository<Ride, String> {
}
