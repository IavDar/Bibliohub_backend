package org.ac.bibliotheque.user.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDto {
    private String email;
    private String name;
    private String surname;
    private String country;
    private String city;
    private String street;
    private String number;
    private String zip;
    private String phone;
    private boolean active;


}
