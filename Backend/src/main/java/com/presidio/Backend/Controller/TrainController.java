package com.presidio.Backend.Controller;

import com.presidio.Backend.Services.TrainService;
import com.presidio.Backend.model.Train;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/train")
public class TrainController {

    @Autowired
    private TrainService trainService;

    // Create a new Train
    @PostMapping("/create")
    public ResponseEntity<Train> createTrain(@RequestBody Train train) {
        Train newTrain = trainService.createTrain(train);
        return ResponseEntity.ok(newTrain);
    }

    // Update an existing Train
    @PutMapping("/update/{id}")
    public ResponseEntity<Train> updateTrain(@PathVariable(value = "id") Long id, @RequestBody Train trainDetails) {
        Optional<Train> updatedTrain = trainService.updateTrain(id, trainDetails);

        return updatedTrain
                .map(train -> ResponseEntity.ok(train))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete a Train by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTrain(@PathVariable Long id) {
         trainService.deleteTheTrain(id);
         return  ResponseEntity.ok("Train Deleted ");
    }
}
