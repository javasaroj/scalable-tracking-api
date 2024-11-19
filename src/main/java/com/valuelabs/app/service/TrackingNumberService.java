package com.valuelabs.app.service;

import com.valuelabs.app.annotation.LogPayloads;
import com.valuelabs.app.annotation.TrackExecutionTime;
import com.valuelabs.app.entity.TrackingNumber;
import com.valuelabs.app.repository.TrackingNumberRepository;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class TrackingNumberService {

    private final TrackingNumberRepository repository;

    private final RedissonClient redissonClient;

    @Autowired
    public TrackingNumberService(TrackingNumberRepository repository, RedissonClient redissonClient) {
        this.repository = repository;
        this.redissonClient = redissonClient;
    }


    /**
     * Generates a unique tracking number based on required parameters.
     *
     * @params Request parameters provided by the user.
     * @return A unique tracking number.
     */
    @LogPayloads
    @TrackExecutionTime
    public String generateTrackingNumber(String originCountry, String destinationCountry, String weight, String customerId, String customerSlug) {
        RLock lock = redissonClient.getLock("trackingNumberLock");
        try {
            if (lock.tryLock(10, 1, TimeUnit.SECONDS)) {
                String uniquePart = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
                String trackingNumber = (originCountry + destinationCountry + weight.replace(".", "") + customerSlug.substring(0, 3) + uniquePart)
                        .replaceAll("[^A-Z0-9]", "").substring(0, 16);

                while (repository.findByTrackingNumber(trackingNumber).isPresent()) {
                    log.info("Generated tracking id {} already present", trackingNumber);
                    trackingNumber = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 16);
                }

                repository.save(new TrackingNumber(trackingNumber, LocalDateTime.now()));
                return trackingNumber;
            } else {
                throw new RuntimeException("Unable to acquire lock");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Lock acquisition interrupted", e);
        } finally {
            lock.unlock();
        }
    }
}
