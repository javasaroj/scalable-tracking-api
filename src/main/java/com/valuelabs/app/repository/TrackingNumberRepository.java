package com.valuelabs.app.repository;

import com.valuelabs.app.entity.TrackingNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrackingNumberRepository extends JpaRepository<TrackingNumber, Long> {

    Optional<TrackingNumber> findByTrackingNumber(String trackingNumber);
}
