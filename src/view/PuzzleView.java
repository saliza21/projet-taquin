/**
 * Cette classe représente la vue du puzzle avec les éléments graphiques et les interactions utilisateur.
 */
package view;

import controller.PuzzleController;
import model.Puzzle;
import util.EcouteurModele;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class PuzzleView extends JFrame implements EcouteurModele {
    private PuzzleController puzzleController;
    private Puzzle puzzle;

    private JPanel puzzlePanel;
    private JLabel numberOfMoves;

    private BufferedImage image;
    private JFileChooser fileChooser;

    /**
     * Construit la vue du puzzle.
     */
    public PuzzleView() throws HeadlessException {
        setTitle("Taquin");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        initComponents();
        setVisible(true);
        puzzleController = new PuzzleController();
        puzzle = puzzleController.getPuzzle();
        puzzle.ajoutEcouteur(this);
    }

   /**
 * Initialise les composants de la vue du puzzle.
 */
private void initComponents() {
    initMenuBar();
    initPuzzlePanel();
    initMovesCounter();
}

/**
 * Initialise le compteur de mouvements.
 */
private void initMovesCounter() {
    JPanel mouvesCounterPanel = new JPanel();
    numberOfMoves = new JLabel("your game is already started");
    mouvesCounterPanel.add(numberOfMoves);
    add(mouvesCounterPanel, BorderLayout.SOUTH);
}

/**
 * Initialise le panneau du puzzle.
 */
private void initPuzzlePanel() {
    puzzlePanel = new JPanel();
    add(puzzlePanel, BorderLayout.CENTER);
}

/**
 * Initialise la barre de menu.
 */
private void initMenuBar() {
    JMenuBar menuBar = new JMenuBar();
    JMenu gameMenu = new JMenu("Game");
    JMenuItem newGame = new JMenuItem("New game");
    JMenuItem exitGame = new JMenuItem("Exit");
    gameMenu.add(newGame);
    gameMenu.add(new JSeparator());
    gameMenu.add(exitGame);
    newGame.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            showNewGameDialog();
        }
    });
    exitGame.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    });
    menuBar.add(gameMenu);
    setJMenuBar(menuBar);
}
    /**
     * Affiche une boîte de dialogue pour créer un nouveau jeu en permettant à l'utilisateur de sélectionner les dimensions du jeu.
     */
    private void showNewGameDialog() {
        // Create dialog
        JDialog dialog = new JDialog(this, "Select game dimensions", true);
        dialog.setLayout(new GridLayout(3, 2));
        centerDialog(this,dialog);
        fileChooser = new JFileChooser();

        // Add labels and text fields for input
        dialog.add(new JLabel("Enter number of rows:"));
        JTextField rowsField = new JTextField();
        dialog.add(rowsField);

        dialog.add(new JLabel("Enter number of columns:"));
        JTextField colsField = new JTextField();
        dialog.add(colsField);

        JButton loadButton = new JButton("image");
        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (fileChooser.showOpenDialog(dialog) == JFileChooser.APPROVE_OPTION) {
                    try {
                        File file = fileChooser.getSelectedFile();
                        image = ImageIO.read(file);
                        ImageIcon imageIcon = new ImageIcon(image);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(dialog, "Error loading image", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        dialog.add(loadButton);

        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int rows = Integer.parseInt(rowsField.getText().equals("")?"3":rowsField.getText());
                int cols = Integer.parseInt(colsField.getText().equals("")?"3":colsField.getText());
                createNewGame(rows, cols);
                dialog.dispose(); // Close the dialog after confirmation
            }
        });
        dialog.add(confirmButton);

        // Set dialog properties and make it visible
        dialog.pack();
        dialog.setVisible(true);
    }
    /**
     * Crée un nouveau jeu avec les dimensions spécifiées et met à jour l'affichage du puzzle.
     * @param rows le nombre de lignes du nouveau jeu
     * @param cols le nombre de colonnes du nouveau jeu
     */
    private void createNewGame(int rows, int cols) {
        puzzleController.createPuzzle(rows,cols,resizeImage(image, puzzlePanel.getWidth(), puzzlePanel.getHeight()));
        puzzle = puzzleController.getPuzzle();
        puzzlePanel.removeAll();
        puzzlePanel.setLayout(new GridLayout(rows,cols));

        putPuzzleButtons(rows, cols);
        revalidate();
        repaint();
    }
    /**
     * Place les boutons du puzzle sur le panneau en fonction des dimensions spécifiées.
     * @param rows le nombre de lignes du puzzle
     * @param cols le nombre de colonnes du puzzle
     */
    private void putPuzzleButtons(int rows, int cols) {
        int index = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                JButton btn;
                if(image == null){
                    btn = new JButton(puzzle.getPuzzleItems()[i][j].getValue());
                }else{
                    btn = new JButton();
                    btn.setIcon(new ImageIcon(puzzle.getPuzzleItems()[i][j].getImage()));
                }
                btn.setToolTipText(puzzle.getPuzzleItems()[i][j].getValue());
                btn.setBackground(Color.WHITE);
                setButtonStyle(i, j, btn);
                if(puzzle.getPuzzleItems()[i][j].getValue() ==" "){
                    btn.setIcon(null);
                    btn.setBackground(Color.BLACK);
                    btn.setEnabled(false);
                }
                puzzlePanel.add(btn);
                index++;
            }
        }
    }
    /**
     * Définit le style du bouton en fonction de la possibilité de mouvement de l'item du puzzle associé.
     * @param i l'indice de ligne de l'item du puzzle
     * @param j l'indice de colonne de l'item du puzzle
     * @param btn le bouton associé à l'item du puzzle
     */
    private void setButtonStyle(int i, int j, JButton btn) {
        if(puzzle.getItemsCanMove().contains(puzzle.getPuzzleItems()[i][j])){
            btn.setForeground(btn.getBackground());
            btn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    btn.setBackground(Color.GREEN); // Change background color when hovered
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    btn.setBackground(Color.WHITE); // Restore default background color when mouse exits
                }
            });
            btn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JButton clickedButton = (JButton) e.getSource();
                    Arrays.stream(puzzle.getPuzzleItems())
                            .flatMap(Arrays::stream)
                            .filter(item -> item.getValue().equals(clickedButton.getToolTipText()))
                            .findFirst()
                            .ifPresentOrElse(item -> {
                                puzzleController.movePuzzleItem(item);
                                modeleMisAJour(puzzle);
                                System.out.println("Found puzzle item: " + item.getValue());
                            }, () -> {
                                System.out.println("Puzzle item not found for button text: ");
                            });

                }
            });
        }else{
//            btn.setEnabled(false);
        }
    }
/**
     * Met à jour le modèle en fonction de la source.
     *
     * @param source La source de la mise à jour.
     */
    @Override
    public void modeleMisAJour(Object source) {
        if (source instanceof Puzzle) {
            puzzle = (Puzzle) source;
            puzzlePanel.removeAll();
            puzzlePanel.setLayout(new GridLayout(puzzle.getPuzzleItems().length, puzzle.getPuzzleItems()[0].length));
            putPuzzleButtons(puzzle.getPuzzleItems().length, puzzle.getPuzzleItems()[0].length);
            revalidate();
            repaint();
            numberOfMoves.setText("Total moves: " + puzzle.getTotalMoves());
            if (puzzle.isSolved()) {
                JOptionPane.showMessageDialog(null, "Congratulations! You've finished the game.", "Game Finished", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    /**
     * Centre le dialogue par rapport à la fenêtre parente.
     *
     * @param parent La fenêtre parente.
     * @param dialog Le dialogue à centrer.
     */
    private static void centerDialog(JFrame parent, JDialog dialog) {
        // Get the parent's location, width, and height
        Point p = parent.getLocation();
        int parentWidth = parent.getWidth();
        int parentHeight = parent.getHeight();

        // Get the dialog's width and height
        int dialogWidth = dialog.getWidth();
        int dialogHeight = dialog.getHeight();

        // Calculate the new location for the dialog
        int x = p.x- Math.abs(parentWidth-dialogWidth)/8;
        int y = p.y + parentHeight/2;

        // Set the location of the dialog
        dialog.setLocation(x, y);
    }

    /**
     * Redimensionne une image à la taille cible spécifiée.
     *
     * @param originalImage L'image originale à redimensionner.
     * @param targetWidth La largeur cible de l'image redimensionnée.
     * @param targetHeight La hauteur cible de l'image redimensionnée.
     * @return L'image redimensionnée.
     */
    private static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resizedImage.createGraphics();

        // Draw the original image scaled to fit the new dimensions
        g.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        g.dispose();

        // Optionally apply high-quality scaling
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        return resizedImage;
    }

}
