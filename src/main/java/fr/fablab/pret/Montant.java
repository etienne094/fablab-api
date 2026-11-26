package fr.fablab.pret;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** Un montant en euros, au centime. */
public record Montant(BigDecimal valeur) {

    public Montant {
        valeur = valeur.setScale(2, RoundingMode.HALF_UP);
    }

    public static Montant euros(double euros) {
        return new Montant(BigDecimal.valueOf(euros));
    }

    public Montant fois(long n) {
        return new Montant(valeur.multiply(BigDecimal.valueOf(n)));
    }
}
