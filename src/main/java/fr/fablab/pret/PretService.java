package fr.fablab.pret;

import fr.fablab.materiel.MaterielRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDate;

@Service
@Transactional
public class PretService {

    public static final int PRETS_EN_COURS_MAX = 3;

    private final PretRepository prets;
    private final MaterielRepository materiels;
    private final Clock horloge;

    public PretService(PretRepository prets, MaterielRepository materiels, Clock horloge) {
        this.prets = prets;
        this.materiels = materiels;
        this.horloge = horloge;
    }

    public Pret emprunter(String adherent, String materiel, int dureeJours) {
        if (!materiels.existsById(materiel)) {
            throw new MaterielInconnu(materiel);
        }
        if (prets.existsByMaterielAndDateRetourIsNull(materiel)) {
            throw new MaterielIndisponible(materiel);
        }
        if (prets.countByAdherentAndDateRetourIsNull(adherent) >= PRETS_EN_COURS_MAX) {
            throw new TropDePretsEnCours(adherent);
        }
        return prets.save(Pret.creer(adherent, materiel, LocalDate.now(horloge), dureeJours));
    }

    public Pret prolonger(long id) {
        Pret pret = prets.findById(id).orElseThrow(() -> new PretInconnu(id));
        return prets.save(pret.prolonger(Pret.PROLONGATION_MAX_JOURS));
    }

    public Pret rendre(long id) {
        Pret pret = prets.findById(id).orElseThrow(() -> new PretInconnu(id));
        pret.rendre(LocalDate.now(horloge));
        return prets.save(pret);
    }
}
