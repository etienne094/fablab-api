package fr.fablab.pret;

public class PretDejaProlonge extends ExceptionMetier {

    public PretDejaProlonge(Long id) {
        super("PRET_DEJA_PROLONGE", 409, "Prêt déjà prolongé : " + id);
    }
}
