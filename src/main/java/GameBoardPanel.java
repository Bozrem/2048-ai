import javax.swing.*;
import java.awt.*;

public class GameBoardPanel extends JPanel {
    private final Game game;
    private boolean gameOver;
    private final int modelID;
    private int score = 0;
    private static final int[] FONT_SIZES = {25, 23, 19, 13, 11};

    public GameBoardPanel(int modelID) {
        this.modelID = modelID;
        this.game = new Game(); // initialize game here
        this.gameOver = false;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int cellSize = 35;
        score = game.getScore();
        if (gameOver) {
            Color bgColor = getBackground();
            g.setColor(bgColor);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 18));
            g.drawString("Game Over! Score:", (getWidth() / 2) - 85, (getHeight() / 2) - 30);
            g.drawString(String.valueOf(game.getScore()), (getWidth() / 2) - 75, (getHeight() / 2));
        } else {
            int[][] matrix = game.getMatrix();
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[0].length; j++) {
                    g.drawRect(j * cellSize, i * cellSize, cellSize, cellSize);

                    if (matrix[i][j] != 0) {
                        String text = Integer.toString(matrix[i][j]);
                        int numDigits = text.length();

                        // Lookup font size based on the number of digits
                        int fontSize = FONT_SIZES[Math.min(numDigits - 1, FONT_SIZES.length - 1)];

                        Font font = new Font("Arial", Font.BOLD, fontSize);
                        g.setFont(font);

                        FontMetrics metrics = g.getFontMetrics(font);
                        int textWidth = metrics.stringWidth(text);
                        int textHeight = metrics.getHeight();

                        int x = 1 + j * cellSize + (cellSize - textWidth) / 2;
                        int y = i * cellSize + ((cellSize - textHeight) / 2) + metrics.getAscent();

                        g.drawString(text, x, y);
                    }
                }
            }
        }
    }

    public void updateBoard() {
        ModelManager manager = ModelManager.getInstance();
        keyOutput[] moves = manager.getNextMoveProbability(modelID, game.getMatrixArray());
        if (game.isGameOver()) {
            gameOver = true;
        }
        boolean boardChanged = false;
        for (keyOutput move : moves) {
            switch (move) {
                case UP -> boardChanged = game.moveUp();
                case DOWN -> boardChanged = game.moveDown();
                case LEFT -> boardChanged = game.moveLeft();
                case RIGHT -> boardChanged = game.moveRight();
                default -> System.out.println("Error in updateBoard()");
            }
            if (boardChanged) {
                break;
            }
        }
        repaint();
    }

    public boolean isGameOver() {
        return game.isGameOver();
    }

    public int getScore() {
        return score;
    }

    public int getModelID(){
        return modelID;
    }
}
