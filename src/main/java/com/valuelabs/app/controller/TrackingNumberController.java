package com.valuelabs.app.controller;

import com.valuelabs.app.service.TrackingNumberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/tracking")
public class TrackingNumberController {

    private final TrackingNumberService service;

    @Autowired
    public TrackingNumberController(TrackingNumberService service) {
        this.service = service;
    }

    @Operation(
            summary = "Generate Tracking Number",
            description = "Generates a unique tracking number based on origin, destination, weight, customer information, and an optional creation date.",
            tags = { "Tracking" }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully generated the tracking number", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "400", description = "Bad request, missing or invalid parameters"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/next-tracking-number")
    public ResponseEntity<Map<String, Object>> generateTrackingNumber(
            @RequestParam String origin_country_id,
            @RequestParam String destination_country_id,
            @RequestParam String weight,
            @RequestParam(required = false) String created_at,
            @RequestParam(required = false) String customer_id,
            @RequestParam(required = false) String customer_name,
            @RequestParam String customer_slug) {

        String trackingNumber = service.generateTrackingNumber(origin_country_id, destination_country_id, weight, customer_id, customer_slug);

        Map<String, Object> response = new HashMap<>();
        response.put("tracking_number", trackingNumber);
        response.put("created_at", LocalDateTime.now());

        return ResponseEntity.ok(response);
    }
}
