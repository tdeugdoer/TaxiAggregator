package com.tserashkevich.ratingservice.repositories;

import com.tserashkevich.ratingservice.models.Rating;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RatingRepository extends CassandraRepository<Rating, UUID> {
    List<Rating> findAllByTargetId(UUID targetId);

    @Query("SELECT COUNT(*) FROM ratings WHERE ride_id = :rideId AND source_id = :sourceId ALLOW FILTERING")
    Integer existByRideIdAndSourceId(String rideId, UUID sourceId);

    @Query("SELECT AVG(rating) FROM ratings WHERE target_id = :targetId ALLOW FILTERING")
    Double countTargetIdAvgRating(UUID targetId);
}
