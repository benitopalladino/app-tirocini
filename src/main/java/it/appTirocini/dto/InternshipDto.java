package it.appTirocini.dto;

import it.appTirocini.entity.Internship;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class InternshipDto {

    private UUID id;

    @NotNull(message = "Seleziona uno studente")
    private UUID studentId;

    @NotNull(message = "Seleziona un'azienda")
    private UUID companyId;

    @NotNull(message = "La data di inizio è obbligatoria")
    @PastOrPresent(message = "La data di inizio non può essere nel futuro")
    private LocalDate startDate;

    @NotNull(message = "La data di fine è obbligatoria")
    @FutureOrPresent(message = "La data di fine non può essere nel passato")
    private LocalDate endDate;

    @NotNull(message = "Lo status è obbligatorio")
    private Internship.InternshipStatus status;

    private String notes;
}
