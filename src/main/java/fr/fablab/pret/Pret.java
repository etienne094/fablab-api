package fr.fablab.pret;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.DAYS;

@Entity
public class Pret {

    public static final int DUREE_MAX_JOURS = 14;
    public static final int PROLONGATION_MAX_JOURS = 7;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String adherent;
    private String materiel;
    private LocalDate debut;
    private LocalDate retourPrevu;
    private LocalDate dateRetour;
    private boolean dejaProlonge;

    protected Pret() {
    }

    private Pret(String adherent, String materiel, LocalDate debut, LocalDate retourPrevu) {
        this.adherent = adherent;
        this.materiel = materiel;
        this.debut = debut;
        this.retourPrevu = retourPrevu;
    }

    public static Pret creer(String adherent, String materiel, LocalDate debut, int dureeJours) {
        if (dureeJours < 1 || dureeJours > DUREE_MAX_JOURS) {
            throw new DureeInvalide(dureeJours);
        }
        return new Pret(adherent, materiel, debut, debut.plusDays(dureeJours));
    }

    public void rendre(LocalDate le) {
        if (dateRetour != null) {
            throw new PretDejaRendu(id);
        }
        this.dateRetour = le;
    }

    public boolean estEnCours() {
        return dateRetour == null;
    }

    public boolean estEnRetard(LocalDate aujourdhui) {
        return estEnCours() && retourPrevu.isBefore(aujourdhui);
    }

    public Montant penalite(LocalDate retour) {
        long jours = DAYS.between(retourPrevu, retour);
        return Montant.euros(0.50).fois(Math.max(0, jours));
    }

    public Pret prolonger(int jours) {
        if (dejaProlonge) throw new PretDejaProlonge(id);
        if (jours < 1 || jours > PROLONGATION_MAX_JOURS) throw new DureeInvalide(jours);
        return avecRetourPrevu(retourPrevu.plusDays(jours));
    }

    private Pret avecRetourPrevu(LocalDate date) {
        this.retourPrevu = date;
        this.dejaProlonge = true;
        return this;
    }

    public Long getId() {
        return id;
    }

    public String getAdherent() {
        return adherent;
    }

    public String getMateriel() {
        return materiel;
    }

    public LocalDate getDebut() {
        return debut;
    }

    public LocalDate getRetourPrevu() {
        return retourPrevu;
    }

    public Optional<LocalDate> getDateRetour() {
        return Optional.ofNullable(dateRetour);
    }
}
