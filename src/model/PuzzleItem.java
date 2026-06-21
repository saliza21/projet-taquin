/**
 * Cette classe représente un objet PuzzleItem avec une valeur et des coordonnées de position.
 */
package model;

import java.awt.image.BufferedImage;

public class PuzzleItem {
    private String value;
    private BufferedImage image;
    private int posX;
    private int posY;

    /**
     * Construit un PuzzleItem avec la valeur, posX et posY donnés.
     * @param value la valeur du PuzzleItem
     * @param posX la position de coordonnée x du PuzzleItem
     * @param posY la position de coordonnée y du PuzzleItem
     */
    public PuzzleItem(String value, int posX, int posY) {
        this.value = value;
        this.posX = posX;
        this.posY = posY;
    }

    /**
     * Construit un PuzzleItem avec la valeur, posX et posY donnés.
     * @param value la valeur du PuzzleItem
     * @param image l'image du PuzzleItem
     * @param posX la position de coordonnée x du PuzzleItem
     * @param posY la position de coordonnée y du PuzzleItem
     */
    public PuzzleItem(BufferedImage image,String value, int posX, int posY) {
        this.value = value;
        this.image = image;
        this.posX = posX;
        this.posY = posY;
    }

    /**
     * Obtient la valeur du PuzzleItem.
     * @return la valeur du PuzzleItem
     */
    public String getValue() {
        return value;
    }

    /**
     * Définit la valeur du PuzzleItem.
     * @param value la valeur à définir
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Obtient la position de coordonnée x du PuzzleItem.
     * @return la position de coordonnée x
     */
    public int getPosX() {
        return posX;
    }

    /**
     * Définit la position de coordonnée x du PuzzleItem.
     * @param posX la position de coordonnée x à définir
     */
    public void setPosX(int posX) {
        this.posX = posX;
    }

    /**
     * Obtient la position de coordonnée y du PuzzleItem.
     * @return la position de coordonnée y
     */
    public int getPosY() {
        return posY;
    }

    /**
     * Définit la position de coordonnée y du PuzzleItem.
     * @param posY la position de coordonnée y à définir
     */
    public void setPosY(int posY) {
        this.posY = posY;
    }
    /**
     * Obtient l'image du PuzzleItem.
     * @return l'image du PuzzleItem
     */
    public BufferedImage getImage() {
        return image;
    }
    /**
     * Définit l'image du PuzzleItem.
     * @param image l'image
     */
    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
