import model.Puzzle;
import view.PuzzleView;

import javax.swing.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Scanner scanner = new Scanner(System.in);
                //                        if(reader.ready()){
                System.out.println("Choisir un mode pour démarer le jeu:");
                System.out.println("1-interface graphique");
                System.out.println("2-console");
                int mode = scanner.nextInt();

                if(mode == 2){
                        System.out.println("entrer le nombre des lignes:");
                        int lignes = scanner.nextInt();
                        System.out.println("entrer le nombre des colones:");
                        int colones = scanner.nextInt();

                        Puzzle taquin = new Puzzle(lignes,colones);
                        taquin.play();

                    }
                if(mode == 1){
                    new PuzzleView().setVisible(true);
                }
            }
        });
    }
}