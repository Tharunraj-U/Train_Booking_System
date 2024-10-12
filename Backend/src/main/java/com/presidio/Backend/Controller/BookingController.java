package com.presidio.Backend.Controller;

import com.presidio.Backend.Repo.TicketRepository;
import com.presidio.Backend.Services.BookingService;
import com.presidio.Backend.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;


    @Autowired
    private TicketRepository ticketRepository;

    // Create a new booking (ticket)
    @PostMapping("/create")
    public ResponseEntity<?> createBooking(@RequestBody Ticket ticket) {
        try {
            // Create the booking
            Ticket newTicket = bookingService.createBooking(ticket);

            if (newTicket != null) {
                // Attempt to send the email
                try {
                    bookingService.sendEmail(newTicket);
                } catch (Exception e) {
                    // Email sending failure
                    return ResponseEntity.status(500)
                            .body("Ticket created, but email could not be sent: " + e.getMessage());
                }
                // Return success if ticket creation and email sending succeeded
                return ResponseEntity.ok(newTicket);
            } else {
                // Ticket creation failed
                return ResponseEntity.badRequest().body("Failed to create booking. Train or User not found.");
            }
        } catch (Exception e) {
            // Handle unexpected errors
            return ResponseEntity.status(500).body("An unexpected error occurred: " + e.getMessage());
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> cancelTicket(@PathVariable Long id) {
        try {
            // Check if the ticket exists before deletion
            Optional<Ticket> ticket = ticketRepository.findById(id);
            if (ticket.isPresent()) {
                // Delete the ticket
                ticketRepository.deleteById(id);
                return ResponseEntity.ok("Ticket cancelled successfully.");
            } else {
                // If the ticket was not found
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ticket not found.");
            }
        } catch (Exception e) {
            // Handle any unexpected exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }




}
