
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GUI {
    private static boolean gameOver = false;
    public static void main(String[] args) {
        JFrame frame = new JFrame("2048 Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(200, 200);

        Game testGame = new Game();

        JPanel panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                if (gameOver){
                    g.clearRect(0,0,200, 200);
                    g.drawString("Game Over", 100, 100);
                }
                else {
                    super.paintComponent(g);
                    int[][] matrix = testGame.getMatrix();
                    for (int i = 0; i < matrix.length; i++) {
                        for (int j = 0; j < matrix[0].length; j++) {
                            g.drawRect(j * 50, i * 50, 50, 50);
                            if (matrix[i][j] != 0) {
                                g.drawString(Integer.toString(matrix[i][j]), j * 50 + 25, i * 50 + 25);
                            }
                        }
                    }
                }
            }
        };

        panel.setFocusable(true);
        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                switch (keyCode) {
                    case KeyEvent.VK_UP:
                        if (!testGame.moveUp()) gameOver=true;
                        break;
                    case KeyEvent.VK_DOWN:
                        if (!testGame.moveDown()) gameOver=true;
                        break;
                    case KeyEvent.VK_LEFT:
                        if (!testGame.moveLeft()) gameOver=true;
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (!testGame.moveRight()) gameOver=true;
                        break;
                }
                testGame.spawnRandomTile();
                panel.repaint();
            }
        });

        frame.add(panel);
        frame.setVisible(true);

        // Timer to redraw the panel
        new Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                panel.repaint();
            }
        }).start();
    }
}

