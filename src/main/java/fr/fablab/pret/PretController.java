package fr.fablab.pret;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/prets")
public class PretController {

    private final PretService service;

    public PretController(PretService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReponsePret emprunter(@Valid @RequestBody DemandePret demande) {
        return ReponsePret.de(service.emprunter(demande.adherent(), demande.materiel(), demande.duree()));
    }

    @PutMapping("/{id}/prolongation")
    public ReponsePret prolonger(@PathVariable long id) {
        return ReponsePret.de(service.prolonger(id));
    }

    @PutMapping("/{id}/retour")
    public ReponsePret rendre(@PathVariable long id) {
        return ReponsePret.de(service.rendre(id));
    }

    public record DemandePret(@NotBlank String adherent, @NotBlank String materiel, int duree) {
    }

    public record ReponsePret(Long id, String adherent, String materiel, LocalDate debut, LocalDate retourPrevu, LocalDate dateRetour) {
        static ReponsePret de(Pret p) {
            return new ReponsePret(p.getId(), p.getAdherent(), p.getMateriel(), p.getDebut(), p.getRetourPrevu(), p.getDateRetour().orElse(null));
        }
    }
}
