package it.appTirocini.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class CompanyDto {

    private UUID id;

    @NotBlank(message = "Nome obbligatorio")
    private String name;

    @NotBlank(message = "Città obbligatoria")
    private String city;

    @NotBlank(message = "Referente obbligatorio")
    private String contactName;

    @NotBlank(message = "Email obbligatoria")
    @Email(message = "Email referente non valida")
    private String contactEmail;
}
