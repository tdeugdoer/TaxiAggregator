package com.tserashkevich.ratingservice.repositories;

import com.tserashkevich.ratingservice.models.Rating;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RatingRepository extends CassandraRepository<Rating, UUID> {
}
