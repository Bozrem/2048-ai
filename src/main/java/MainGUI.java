import java.awt.*;
import javax.swing.*;

public class MainGUI {
    private static final int[] statistics = new int[4]; // generation, best score, last gen score, this gen score
    private static final String[] statisticLabels = new String[]{"Generation: ", "Best Score: ", "Last Gen Score: ", "This Gen Score: "};

    private static final JLabel[] intLabels = new JLabel[4];
    private static final GameBoardPanel[] gameBoardPanels = new GameBoardPanel[35];
    private static final JFrame frame = new JFrame("2048 AI Trainer");
    private static boolean generationRunning;


    public static void main(String[] args) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);

        statistics[0] = -1;
        setInformationPanel(frame); // add information area to left
        initializeNextGeneration();


        frame.setVisible(true);

        // Update info and game boards
        new Timer(200, evt -> {
            // Update the infoLine with real stats
            // Update each game board panel
            updateInformation();
            generationRunning = false;
            for (GameBoardPanel gameBoard : gameBoardPanels){
                gameBoard.updateBoard();
                if (!gameBoard.isGameOver()) {
                    generationRunning = true;
                }
                if (gameBoard.getScore() > statistics[1]) {
                    statistics[1] = gameBoard.getScore();
                }
                if (gameBoard.getScore() > statistics[3]){
                    statistics[3] = gameBoard.getScore();
                }
            }
            if (!generationRunning){
                System.out.println("Ended generation " + statistics[0]);
                initializeNextGeneration();
            }
        }).start();
    }

    private static void initializeNextGeneration(){
        if (statistics[3] > statistics[1]) statistics[1] = statistics[3];
        statistics[0]++; // increment generation
        statistics[2] = statistics[3]; // move current gen best score to next
        statistics[3] = 0; // reset current gen best score
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setLayout(new GridLayout(5, 7));
        setGameBoards(0, rightPanel);
        frame.add(rightPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private static void setGameBoards(int generation, JPanel panel){
        ModelManager manager = ModelManager.getInstance();
        manager.createNewGeneration(generation, null);
        for (int i = 0; i < gameBoardPanels.length; i++){
            int modelID = i + (generation*100);
            gameBoardPanels[i] = new GameBoardPanel(modelID);
            gameBoardPanels[i].setPreferredSize(new Dimension(200, 200));
            panel.add(gameBoardPanels[i]);
        }

    }

    private static void updateInformation() {
        for (int i = 0; i < 4; i++) {
            System.out.print(statisticLabels[i] + statistics[i] + " ");
            intLabels[i].setText(String.valueOf(statistics[i]));
        }
        System.out.println();
    }

    private static void setInformationPanel(JFrame frame){
        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(250, 900));
        leftPanel.setLayout(new GridLayout(4, 1));  // 4 rows, 1 column

        // Create sub-panels and labels, then add them to the left panel
        for (int i = 0; i < 4; i++) {
            JPanel subPanel = new JPanel(new BorderLayout());
            JLabel infoLabel = new JLabel(statisticLabels[i]);
            infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
            infoLabel.setVerticalAlignment(SwingConstants.CENTER);
            infoLabel.setFont(new Font("Arial", Font.PLAIN, 24));

            intLabels[i] = new JLabel(String.valueOf(statistics[i]));  // Initialize JLabel with current statistic
            intLabels[i].setHorizontalAlignment(SwingConstants.CENTER);
            intLabels[i].setFont(new Font("Arial", Font.PLAIN, 30));


            subPanel.add(infoLabel, BorderLayout.NORTH);
            subPanel.add(intLabels[i], BorderLayout.CENTER);  // Note the change here

            leftPanel.add(subPanel);
        }
        // Add the left panel to the main frame
        frame.add(leftPanel, BorderLayout.WEST);
    }
}

