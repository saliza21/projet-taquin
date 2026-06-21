/**
 * Cette interface définit un modèle écoutable qui permet d'ajouter et de retirer des écouteurs de modèle.
 */
package util;

public interface ModeleEcoutable {
    /**
     * Ajoute un écouteur au modèle.
     * @param e l'écouteur à ajouter
     */
    void ajoutEcouteur(EcouteurModele e);

    /**
     * Retire un écouteur du modèle.
     * @param e l'écouteur à retirer
     */
    void retraitEcouteur(EcouteurModele e);
}
