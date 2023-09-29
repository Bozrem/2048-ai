import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MainGUI {
    private static int generation = 0;
    private static int bestScore = 0;
    private static int currentGenBest = 0;
    private static int lastGenBest = 0;

    public static void main(String[] args) {
        JFrame frame = new JFrame("2048 AI Trainer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1600, 900);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Information Line
        JLabel infoLine = new JLabel();
        updateInfoLine(infoLine);

        mainPanel.add(infoLine, BorderLayout.WEST);

        // 16 instances of 2048 game
        JPanel gamePanel = new JPanel(new GridLayout(5, 7));

        ModelManager modelManager = new ModelManager();
        modelManager.createNewGeneration(0);

        GameBoardPanel gameBoardPanel = new GameBoardPanel(0); // Initialize with the ModelID
        gamePanel.add(gameBoardPanel);

        mainPanel.add(gamePanel, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.setVisible(true);

        // Update info and game boards
        new Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                // Update the infoLine with real stats
                updateInfoLine(infoLine);

                // Update each game board panel
                // Assume GameBoardPanel has an updateBoard method
                for (Component component : gamePanel.getComponents()) {
                    if (component instanceof GameBoardPanel) {
                        ((GameBoardPanel) component).updateBoard();
                    }
                }
            }
        }).start();
    }

    // Utility function to update the information line
    private static void updateInfoLine(JLabel infoLine) {
        infoLine.setText(String.format("AI Gen: %d | Best Score: %d | Current Gen Best: %d | Last Gen Best: %d",
                generation, bestScore, currentGenBest, lastGenBest));
    }
}

