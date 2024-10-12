package com.presidio.Backend.Services;

import com.presidio.Backend.Repo.TicketRepository;
import com.presidio.Backend.Repo.TrainRepository;
import com.presidio.Backend.Repo.UserRepository;
import com.presidio.Backend.model.Ticket;
import com.presidio.Backend.model.Train;
import com.presidio.Backend.model.User;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class BookingService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TrainRepository trainRepository;

    @Autowired
    private UserRepository userRepository;


    // Logic to create a new booking
    public Ticket createBooking(Ticket ticket) {
        Optional<Train> trainOpt = trainRepository.findById(ticket.getTrain().getId());
        Optional<User> userOpt = userRepository.findById(ticket.getUser().getId());

        if (trainOpt.isPresent() && userOpt.isPresent()) {
            Train train = trainOpt.get();
            User user = userOpt.get();

            // Decrease the available seats
            if (train.getSeatsAvailable() > 0) {
                train.setSeatsAvailable(train.getSeatsAvailable() - 1);
                trainRepository.save(train);
            } else {
                throw new RuntimeException("No seats available for this train.");
            }

            // Create a new ticket
            Ticket newTicket = new Ticket();
            newTicket.setTrain(train);
            newTicket.setUser(user);
            newTicket.setDestination(ticket.getDestination());
            Random random=new Random();
            newTicket.setSeatNumber(random.nextInt(train.getSeatsAvailable()));
            newTicket.setBookingClass(ticket.getBookingClass());
            newTicket.setStartingAmount(train.getPrice());
            newTicket.setBookingDate(LocalDateTime.now());
            newTicket.setExpiringTime(train.getTime());  // Assuming expiry is the train departure time

            // Save the ticket to the database
            return ticketRepository.save(newTicket);
        } else {
            throw new RuntimeException("Train or User not found.");
        }
    }

    // Method to send email
    public void sendEmail(Ticket newTicket) throws MessagingException {
        emailService.sendConfirmationEmail(newTicket);
    }

    // Method to cancel a booking
    public void cancelBooking(Long ticketId) throws MessagingException {
        Optional<Ticket> ticketOpt = ticketRepository.findById(ticketId);
        if (ticketOpt.isPresent()) {
            Ticket ticket = ticketOpt.get();
            ticketRepository.delete(ticket);

            // Increment the available seats when a ticket is canceled
            Train train = ticket.getTrain();
            train.setSeatsAvailable(train.getSeatsAvailable() + 1);
            trainRepository.save(train);

            emailService.sendCancellationEmail(ticket);
        } else {
            throw new RuntimeException("Ticket not found.");
        }
    }
}
