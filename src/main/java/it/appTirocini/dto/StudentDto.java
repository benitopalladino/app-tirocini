package it.appTirocini.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class StudentDto {

    private UUID id;

    @NotBlank(message = "Matricola obbligatoria")
    private String matricola;

    @NotBlank(message = "Nome obbligatorio")
    private String firstName;

    @NotBlank(message = "Cognome obbligatorio")
    private String lastName;

    @NotBlank(message = "Email obbligatoria")
    @Email(message = "Email non valida")
    private String email;
}
