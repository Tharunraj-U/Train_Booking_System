package com.presidio.Backend.Repo;

import com.presidio.Backend.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    void deleteAllById(Long id);
}
