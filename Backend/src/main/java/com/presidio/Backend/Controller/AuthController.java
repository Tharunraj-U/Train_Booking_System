package com.presidio.Backend.Controller;

import com.presidio.Backend.Auth.JwtProvider;
import com.presidio.Backend.Repo.UserRepository;
import com.presidio.Backend.Response.Password;
import com.presidio.Backend.Services.*;
import com.presidio.Backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;

    @Autowired
    private EmailService emailService; // Inject EmailService

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return new ResponseEntity<>(new AuthResponse("Email Already is Registered"), HttpStatus.BAD_REQUEST);
        }


        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setRoles(user.getRoles());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(newUser);

        Authentication authentication = authenticate(user.getEmail(), user.getPassword());
        String jwt = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Registration successful");

        try {
            emailService.sendWelcomeEmail(user.getEmail()); // Send welcome email
        } catch (Exception e) {
            e.printStackTrace(); // Log exception (don't interrupt the flow)
        }

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest request) {
        Authentication authentication = authenticate(request.getEmail(), request.getPassword());
        String jwt = jwtProvider.generateToken(authentication);

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Login successful");


        try {
            emailService.sendLoginEmail(request.getEmail()); // Send login email
        } catch (Exception e) {
            e.printStackTrace(); // Log exception (don't interrupt the flow)
        }

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    @PostMapping("/otp")
    public  ResponseEntity<String> otpGenreate(@RequestBody Password email) throws Exception {
        Optional<User> user = userRepository.findByEmail(email.email());
        if (!user.isPresent()) {
            return new ResponseEntity<>("User Not Found ", HttpStatus.NOT_FOUND);
        }

        String otp=userService.generateTheOtp();
        emailService.sendOtp(otp,email.email());
        return new ResponseEntity<>(otp,HttpStatus.ACCEPTED);
    }


    @PutMapping("/updatePassword")
    public  ResponseEntity<String> changePassword(@RequestBody Password password) {

        Optional<User> user = userRepository.findByEmail(password.email());
        if (user.isPresent()) {
            user.get().setPassword(passwordEncoder.encode(password.password()));
            userRepository.save(user.get());
            return new ResponseEntity<>("Password updated successfully." + password.password(), HttpStatus.OK);
        }

        return new ResponseEntity<>("User Not Found ", HttpStatus.NOT_FOUND);
    }







    //    for the authentication service method
    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customerUserDetailsService.loadUserByUsername(username);
        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username.");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password.");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }


}