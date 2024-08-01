package org.ac.bibliotheque.user.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

    @NotNull(message = "имеил не может быть null")
    @NotBlank(message = "имеил не может быть пустым")
    @Schema(description = "User email", example = "dsadas@dsad.ew")
    private String email;
    @NotNull(message = "пароль не может быть null")
    @NotBlank(message = "пароль не может быть пустым")
    private String password;
    private String role;


}
