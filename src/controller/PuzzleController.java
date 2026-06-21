/**
 * Cette classe contrôle les actions liées au puzzle.
 */
package controller;

import model.Puzzle;
import model.PuzzleItem;

import java.awt.image.BufferedImage;

public class PuzzleController {
    private Puzzle puzzle;

    /**
     * Construit un PuzzleController avec un puzzle par défaut de taille 3x3.
     */
    public PuzzleController() {
        puzzle = new Puzzle(3, 3);
    }

    /**
     * Crée un nouveau puzzle avec le nombre de lignes et de colonnes spécifié.
     * @param lignes le nombre de lignes du nouveau puzzle
     * @param columns le nombre de colonnes du nouveau puzzle
     */
    public void createPuzzle(int lignes, int columns, BufferedImage image) {
        if (image!=null){
            puzzle = new Puzzle(lignes, columns,image);
        }else{
            puzzle = new Puzzle(lignes, columns);

        }
    }

    /**
     * Obtient le puzzle contrôlé par ce PuzzleController.
     * @return le puzzle contrôlé
     */
    public Puzzle getPuzzle() {
        return puzzle;
    }

    /**
     * Déplace un item du puzzle en fonction de l'item sélectionné.
     * @param selectedPuzzleItem l'item du puzzle sélectionné
     */
    public void movePuzzleItem(PuzzleItem selectedPuzzleItem) {
        puzzle.selectPuzzleItemTomoveIt(selectedPuzzleItem);
        puzzle.afficherPuzzle();
    }
}