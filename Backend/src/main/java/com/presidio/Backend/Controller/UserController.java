package com.presidio.Backend.Controller;


import com.presidio.Backend.Repo.UserRepository;
import com.presidio.Backend.model.Ticket;
import com.presidio.Backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {


    @Autowired
    private UserRepository userRepository;

    @GetMapping("getAllTickets/{id}")
    public List<Ticket> getAllTicket(@PathVariable Long id){
        Optional<User> user=userRepository.findById(id);
        List<Ticket> ticket=user.get().getTickets();
        return ticket;
    }
}
