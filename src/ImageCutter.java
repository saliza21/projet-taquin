import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

public class ImageCutter extends JFrame {
    private BufferedImage image;
    private JLabel imageLabel;
    private JTextField rowField, colField;
    private JButton loadButton, cutButton;
    private JFileChooser fileChooser;

    public ImageCutter() {
        super("Image Cutter");

        fileChooser = new JFileChooser();
        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);

        JPanel controlPanel = new JPanel();
        rowField = new JTextField(5);
        colField = new JTextField(5);
        loadButton = new JButton("Load Image");
        cutButton = new JButton("Cut Image");

        controlPanel.add(new JLabel("Rows:"));
        controlPanel.add(rowField);
        controlPanel.add(new JLabel("Columns:"));
        controlPanel.add(colField);
        controlPanel.add(loadButton);
        controlPanel.add(cutButton);

        loadButton.addActionListener(this::loadImage);
        cutButton.addActionListener(this::cutImage);

        add(controlPanel, BorderLayout.NORTH);
        add(new JScrollPane(imageLabel), BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
    }

    private void loadImage(ActionEvent e) {
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fileChooser.getSelectedFile();
                image = ImageIO.read(file);
                ImageIcon imageIcon = new ImageIcon(image);
                imageLabel.setIcon(imageIcon);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error loading image", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void cutImage(ActionEvent e) {
        int rows = Integer.parseInt(rowField.getText());
        int cols = Integer.parseInt(colField.getText());
        int pieceWidth = image.getWidth() / cols;
        int pieceHeight = image.getHeight() / rows;

        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                try {
                    BufferedImage subImage = image.getSubimage(x * pieceWidth, y * pieceHeight, pieceWidth, pieceHeight);
                    File outputFile = new File("image_piece_" + y + "_" + x + ".png");
                    ImageIO.write(subImage, "png", outputFile);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error saving image pieces", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        JOptionPane.showMessageDialog(this, "Images saved successfully!");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ImageCutter().setVisible(true));
    }
}
