package com.presidio.Backend.Services;

import com.presidio.Backend.model.Train;
import com.presidio.Backend.Repo.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TrainService {

    @Autowired
    private TrainRepository trainRepository;

    // Create a new Train
    public Train createTrain(Train train) {
        return trainRepository.save(train);
    }

    // Update an existing Train
    public Optional<Train> updateTrain(Long id, Train trainDetails) {
        Optional<Train> optionalTrain = trainRepository.findById(id);

        if (optionalTrain.isPresent()) {
            Train train = optionalTrain.get();
            train.setName(trainDetails.getName());
            train.setSource(trainDetails.getSource());
            train.setDestination(trainDetails.getDestination());
            train.setTime(trainDetails.getTime());
            train.setSeatsAvailable(trainDetails.getSeatsAvailable());
            train.setPrice(trainDetails.getPrice());

            return Optional.of(trainRepository.save(train));
        } else {
            return Optional.empty();
        }
    }

    public ResponseEntity<Optional<Train>> deleteTheTrain(Long id) {
        Optional<Train> train=trainRepository.findById(id);
        trainRepository.deleteById(id);
        return ResponseEntity.ok(train);
    }
}
