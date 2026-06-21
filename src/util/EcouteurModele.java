/**
 * Cette interface définit un écouteur de modèle qui réagit à une mise à jour du modèle.
 */
package util;

public interface EcouteurModele {
    /**
     * Méthode appelée lorsqu'un modèle est mis à jour.
     * @param source la source de la mise à jour
     */
    void modeleMisAJour(Object source);
}
