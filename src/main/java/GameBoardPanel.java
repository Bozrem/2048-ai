import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameBoardPanel extends JPanel {
    private Game game;
    private boolean gameOver;
    private int modelID;

    public GameBoardPanel(int modelID) {
        this.modelID = modelID;
        this.game = new Game(); // initialize game here
        this.gameOver = false;

        // The timer could still be useful for triggering repaints
        new Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (game.isGameOver()) { // You'll need to add a method to check this in your Game class
                    gameOver = true;
                }
                repaint();
            }
        }).start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (gameOver) {
            g.clearRect(0, 0, getWidth(), getHeight());
            g.drawString("Game Over", getWidth() / 2, getHeight() / 2);
        } else {
            int[][] matrix = game.getMatrix();
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[0].length; j++) {
                    g.drawRect(j * 25, i * 25, 25, 25); // Reduced sizes for smaller cells
                    if (matrix[i][j] != 0) {
                        g.drawString(Integer.toString(matrix[i][j]), j * 25 + 12, i * 25 + 12);
                    }
                }
            }
        }
    }

    public void updateBoard(){

    }
}
