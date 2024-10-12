package com.presidio.Backend.Services;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthResponse {
    private String jwt;
    private String message;

    public AuthResponse(String message) {
        this.message = message;
    }
}
