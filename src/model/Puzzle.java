/**
 * Cette classe représente un puzzle avec des items et des fonctionnalités de jeu.
 */
package model;

import util.AbstrcatModeleEcoutable;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class Puzzle extends AbstrcatModeleEcoutable {
    private PuzzleItem[][] puzzleItems;
    private BufferedImage image;
    private int voidCaseX;
    private int voidCaseY;
    private int totalMoves;
    private boolean isSolved;

    /**
     * Construit un puzzle avec le nombre de lignes et de colonnes donné.
     * @param lignes le nombre de lignes du puzzle
     * @param columns le nombre de colonnes du puzzle
     */
    public Puzzle(int lignes, int columns) {
        puzzleItems = new PuzzleItem[lignes][columns];
        voidCaseX = lignes - 1;
        voidCaseY = columns - 1;
        initPuzzle();
        melangerPuzzle();
        totalMoves = 0;
        isSolved = false;
    }

    /**
     * Construit un puzzle avec le nombre de lignes et de colonnes donné.
     * @param lignes le nombre de lignes du puzzle
     * @param columns le nombre de colonnes du puzzle
     */
    public Puzzle(int lignes, int columns,BufferedImage fullImage) {
        image = fullImage;
        puzzleItems = new PuzzleItem[lignes][columns];
        voidCaseX = lignes - 1;
        voidCaseY = columns - 1;
        initPuzzle();
        setImages(lignes,columns);
        melangerPuzzle();
        totalMoves = 0;
        isSolved = false;
    }
/**
 * Définit les images pour le puzzle en fonction du nombre de lignes et de colonnes.
 *
 * @param lignes Le nombre de lignes du puzzle.
 * @param columns Le nombre de colonnes du puzzle.
 */
private void setImages(int lignes, int columns) {
    int pieceWidth = this.image.getWidth() / columns;
    int pieceHeight = this.image.getHeight() / lignes;
    for (int x = 0; x < lignes; x++) {
        for (int y = 0; y < columns; y++) {
            puzzleItems[x][y].setImage(image.getSubimage(y * pieceWidth, x * pieceHeight, pieceWidth, pieceHeight));
        }
    }
}
    /**
     * Initialise les items du puzzle.
     */
    public void initPuzzle() {
        int index = 0;
        for (int ligne = 0; ligne < this.puzzleItems.length; ligne++) {
            for (int column = 0; column < this.puzzleItems[0].length; column++) {
                this.puzzleItems[ligne][column] = new PuzzleItem(Integer.toString(index), ligne, column);
                index++;
            }
        }
        this.puzzleItems[this.puzzleItems.length - 1][this.puzzleItems[0].length - 1].setValue(" ");
    }

    /**
     * Mélange les items du puzzle.
     */
    public void melangerPuzzle() {
        Random random = new Random();

        for (int i = 0; i < random.nextInt(100) + 10; i++) {
            List<Character> possibleMoves = getPossibleMoves();
            int randomIndex = random.nextInt(possibleMoves.size());// choisir une position aleatoire
            char randomValue = possibleMoves.get(randomIndex);// extracter la valeur a partir de l'index
            moveItem(randomValue);
        }
    }

    /**
     * Commence le jeu du puzzle.
     */
    public void play() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int numberOfMoves = 0;
        try {
            System.out.println("Press 'q' to quit.");
            System.out.println("you have this possible moves: " + getPossibleMoves().stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(", ")));
            afficherPuzzle();
            while (true) {
                // Check if a key has been pressed
                if (reader.ready()) {
                    System.out.println("you have this possible moves: " + getPossibleMoves().stream()
                            .map(Object::toString)
                            .collect(Collectors.joining(", ")));
                    afficherPuzzle();
                    // Read the pressed key
                    char key = (char) reader.read();

                    // Print the pressed key
                    selectItemTomoveIt(key);
                    numberOfMoves++;
                    if (isSolved){
                        System.out.println("###Puzzle Solved !!!!###");
                    }
                    // Check if the pressed key is 'q' to quit
                    if (key == 'q') {
                        break;
                    }
                }

                // Sleep for a short duration to avoid busy-waiting
                Thread.sleep(100);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close(); // Always close the reader when done
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Obtient les mouvements possibles.
     * @return une liste de caractères représentant les mouvements possibles
     */
    public List<Character> getPossibleMoves() {
        List<Character> possibleMoves = new ArrayList<>();

        if (this.voidCaseX != 0) {
            possibleMoves.add('Z');
        }

        if (this.voidCaseY != 0) {
            possibleMoves.add('Q');
        }

        if (this.voidCaseX != this.puzzleItems.length - 1) {
            possibleMoves.add('S');
        }

        if (this.voidCaseY != this.puzzleItems[0].length - 1) {
            possibleMoves.add('D');
        }

        return possibleMoves;

    }
    /**
     * Obtient les items pouvant être déplacés.
     * @return une liste d'items pouvant être déplacés
     */
    public List<PuzzleItem> getItemsCanMove() {
        Set<PuzzleItem> possibleMoves = new HashSet<>();

        if (this.voidCaseX != 0) {
            possibleMoves.add(puzzleItems[voidCaseX - 1][voidCaseY]);
        }

        if (this.voidCaseY != 0) {
            possibleMoves.add(puzzleItems[voidCaseX][voidCaseY - 1]);

        }

        if (this.voidCaseX != this.puzzleItems.length - 1) {
            possibleMoves.add(puzzleItems[voidCaseX + 1][voidCaseY]);

        }

        if (this.voidCaseY != this.puzzleItems[0].length - 1) {
            possibleMoves.add(puzzleItems[voidCaseX][voidCaseY + 1]);

        }
        return possibleMoves.stream().collect(Collectors.toList());

    }
    /**
     * Sélectionne un item à déplacer en fonction de la touche pressée.
     * @param key la touche pressée
     */
    public void selectItemTomoveIt(char key) {
        moveItem(key);
        checkIfPuzzleIsSolved();
    }
    /**
     * Sélectionne un PuzzleItem à déplacer.
     * @param itemSelected l'item à déplacer
     */
    public void selectPuzzleItemTomoveIt(PuzzleItem itemSelected) {
        selectItemTomoveIt(getKeyCharacterFromItem(itemSelected));
    }
    /**
     * Obtient la touche correspondante à un PuzzleItem donné.
     * @param itemSelected l'item sélectionné
     * @return la touche correspondante
     */
    private char getKeyCharacterFromItem(PuzzleItem itemSelected) {
        if(itemSelected.getPosX() - voidCaseX == 1)
            return 'S';
        if(itemSelected.getPosX() - voidCaseX == -1)
            return 'Z';
        if(itemSelected.getPosY() - voidCaseY == 1)
            return 'D';
        if(itemSelected.getPosY() - voidCaseY == -1)
            return 'Q';
        return ' ';
    }
    /**
     * Déplace l'élément du puzzle en fonction de la touche pressée.
     * @param item la touche pressée
     */
    public void moveItem(char item) {
        switch (item) {
            case 'Z':
                if (this.voidCaseX != 0)
                    switchVoidToTop();
                break;
            case 'Q':
                if (this.voidCaseY != 0)
                    switchVoidToLeft();
                break;
            case 'S':
                if (this.voidCaseX != this.puzzleItems.length - 1)
                    switchVoidToButtom();
                break;
            case 'D':
                if (this.voidCaseY != this.puzzleItems[0].length - 1)
                    switchVoidToRight();
                break;
            default:
                break;
        }
        fireChangement();
    }
    /**
     * Déplace l'élément vide vers la droite.
     */
    private void switchVoidToRight() {
        PuzzleItem selectedItem = puzzleItems[voidCaseX][voidCaseY + 1];
        PuzzleItem voidItem = changeSelectedItemPostionAndGetVoidItem(selectedItem);
        voidItem.setPosY(voidCaseY + 1);
        puzzleItems[voidCaseX][voidCaseY + 1] = voidItem;
        voidCaseY++;
        totalMoves++;
    }
    /**
     * Déplace l'élément vide vers le bas.
     */
    private void switchVoidToButtom() {
        PuzzleItem selectedItem = puzzleItems[voidCaseX + 1][voidCaseY];
        PuzzleItem voidItem = changeSelectedItemPostionAndGetVoidItem(selectedItem);
        voidItem.setPosX(voidCaseX + 1);
        puzzleItems[voidCaseX + 1][voidCaseY] = voidItem;
        voidCaseX++;
        totalMoves++;
    }
    /**
     * Déplace l'élément vide vers la gauche.
     */
    private void switchVoidToLeft() {
        PuzzleItem selectedItem = puzzleItems[voidCaseX][voidCaseY - 1];
        PuzzleItem voidItem = changeSelectedItemPostionAndGetVoidItem(selectedItem);
        voidItem.setPosY(voidCaseY - 1);
        puzzleItems[voidCaseX][voidCaseY - 1] = voidItem;
        voidCaseY--;
        totalMoves++;
    }
    /**
     * Déplace l'élément vide vers le haut.
     */
    private void switchVoidToTop() {
        PuzzleItem selectedItem = puzzleItems[voidCaseX - 1][voidCaseY];
        PuzzleItem voidItem = changeSelectedItemPostionAndGetVoidItem(selectedItem);
        voidItem.setPosX(voidCaseX - 1);
        puzzleItems[voidCaseX - 1][voidCaseY] = voidItem;// ecraser l'objet selected Item with void Item
        voidCaseX--;
        totalMoves++;
    }
    /**
     * Change la position de l'élément sélectionné et retourne l'élément vide.
     * @param selectedItem l'élément sélectionné
     * @return l'élément vide
     */
    private PuzzleItem changeSelectedItemPostionAndGetVoidItem(PuzzleItem selectedItem) {
        PuzzleItem voidItem = puzzleItems[voidCaseX][voidCaseY];

        selectedItem.setPosX(voidCaseX);
        selectedItem.setPosY(voidCaseY);

        puzzleItems[voidCaseX][voidCaseY] = selectedItem;
        return voidItem;
    }
    /**
     * Vérifie si le puzzle est résolu en comparant les valeurs des items avec leur index attendu.
     */
    private void checkIfPuzzleIsSolved() {
        int index = 0;
        for (int ligne = 0; ligne < this.puzzleItems.length; ligne++) {
            for (int column = 0; column < this.puzzleItems[0].length; column++) {
                char indexValue = (ligne == this.puzzleItems.length - 1 && column == this.puzzleItems[0].length - 1) ? ' ' : Integer.toString(index).charAt(0);
                if (this.puzzleItems[ligne][column].getValue().charAt(0) != indexValue)
                    return;
                index++;
            }
        }
        setSolved(true);
    }
    /**
     * Affiche le puzzle en console.
     */
    public void afficherPuzzle() {
        int index = 0;
        for (int ligne = 0; ligne < this.puzzleItems.length; ligne++) {
            for (int column = 0; column < this.puzzleItems[0].length; column++) {
                System.out.printf("%s ", this.puzzleItems[ligne][column].getValue());
            }
            System.out.println();
        }
    }
    /**
     * Obtient les items du puzzle.
     * @return la matrice d'items du puzzle
     */
    public PuzzleItem[][] getPuzzleItems() {
        return puzzleItems;
    }
    /**
     * Définit les items du puzzle.
     * @param puzzleItems la matrice d'items à définir
     */
    public void setPuzzleItems(PuzzleItem[][] puzzleItems) {
        this.puzzleItems = puzzleItems;
    }
    /**
     * Obtient la position en x de l'élément vide.
     * @return la position en x de l'élément vide
     */
    public int getVoidCaseX() {
        return voidCaseX;
    }
    /**
     * Définit la position en x de l'élément vide.
     * @param voidCaseX la position en x à définir
     */
    public void setVoidCaseX(int voidCaseX) {
        this.voidCaseX = voidCaseX;
    }
    /**
     * Obtient la position en y de l'élément vide.
     * @return la position en y de l'élément vide
     */
    public int getVoidCaseY() {
        return voidCaseY;
    }
    /**
     * Définit la position en y de l'élément vide.
     * @param voidCaseY la position en y à définir
     */
    public void setVoidCaseY(int voidCaseY) {
        this.voidCaseY = voidCaseY;
    }
    /**
     * Vérifie si le puzzle est résolu.
     * @return true si le puzzle est résolu, sinon false
     */
    public boolean isSolved() {
        return isSolved;
    }
    /**
     * Définit si le puzzle est résolu.
     * @param solved true si le puzzle est résolu, sinon false
     */
    public void setSolved(boolean solved) {
        isSolved = solved;
    }
    /**
     * Obtient le nombre total de mouvements effectués.
     * @return le nombre total de mouvements
     */
    public int getTotalMoves() {
        return totalMoves;
    }
}
