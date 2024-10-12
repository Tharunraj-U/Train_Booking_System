package com.presidio.Backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Ticket extends SuperClass {

    @ManyToOne
    @JoinColumn(name = "train_id")
    private Train train;  // Reference to Train

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;    // Reference to User

    private int seatNumber;  // Seat number booked by the user

    private LocalDateTime bookingDate;  // Date when the ticket was booked

    private String destination;  // Destination of the journey

    private double startingAmount;  // Price of the ticket

    private LocalDateTime expiringTime;  // Expiry time of the ticket (if applicable)

    private String bookingClass;  // The class of the booking (e.g., Economy, Business)

}
