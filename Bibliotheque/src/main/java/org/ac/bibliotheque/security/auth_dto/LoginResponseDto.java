package org.ac.bibliotheque.security.auth_dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ac.bibliotheque.role.Role;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {
    private Long id;
    private String email;
    private String name;
    private String surname;
    private String country;
    private String city;
    private String street;
    private String number;
    private String zip;
    private String phone;
    private Set<Role> role;
    private String accessToken;
    private String refreshToken;
    private String message;

}

