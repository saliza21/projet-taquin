/**
 * Cette classe représente un modèle écoutable abstrait qui implémente l'interface ModeleEcoutable.
 */
package util;

import java.util.ArrayList;
import java.util.List;

public class AbstrcatModeleEcoutable implements ModeleEcoutable {
    public List<EcouteurModele> ecouteurs;

    /**
     * Construit un modèle écoutable abstrait.
     */
    public AbstrcatModeleEcoutable() {
        ecouteurs = new ArrayList<>();
    }

    @Override
    public void ajoutEcouteur(EcouteurModele e) {
        ecouteurs.add(e);
    }

    @Override
    public void retraitEcouteur(EcouteurModele e) {
        ecouteurs.remove(e);
    }

    /**
     * Notifie les écouteurs d'un changement dans le modèle.
     */
    protected void fireChangement() {
        for (EcouteurModele ecouteur : ecouteurs) {
            ecouteur.modeleMisAJour(this);
        }
    }
}
