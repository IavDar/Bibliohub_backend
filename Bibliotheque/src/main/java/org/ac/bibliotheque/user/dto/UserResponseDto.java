package org.ac.bibliotheque.user.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ac.bibliotheque.role.Role;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {


    @Schema(description = "product unique", example = "100")
    private Long id;
    @NotNull(message = "имеил не может быть null")
    @NotBlank(message = "имеил не может быть пустым")
    @Schema(description = "User email", example = "dsadas@dsad.ew")
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
    private String message;


}
