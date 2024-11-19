package com.valuelabs.app.service;

import com.valuelabs.app.repository.TrackingNumberRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class TrackingNumberServiceTest {

    @Autowired
    private TrackingNumberService service;

    @MockBean
    private TrackingNumberRepository repository;

    @Test
    public void testGenerateTrackingNumber() {
        Mockito.when(repository.findByTrackingNumber(Mockito.anyString())).thenReturn(Optional.empty());

        String trackingNumber = service.generateTrackingNumber("IN", "UK", "2.629", "saroj", "saroj-sahoo");

        assertNotNull(trackingNumber);
        assertTrue(trackingNumber.matches("^[A-Z0-9]{1,16}$"));
    }
}