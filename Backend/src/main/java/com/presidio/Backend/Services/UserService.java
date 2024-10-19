package com.presidio.Backend.Services;

import com.presidio.Backend.Repo.UserRepository;
import com.presidio.Backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Retrieve a user by email
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Update user details
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public String generateTheOtp() {
        int otp=(int)((Math.random()*9000)+1000);
        return  otp+"";
    }
}
