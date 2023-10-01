import java.util.ArrayList;

public class Game {

    private final int[][] matrix;

    public Game(){
        matrix = new int[4][4];
        spawnRandomTile();
        spawnRandomTile();
    }

    private ArrayList<int[]> getEmptyTiles() {
        ArrayList<int[]> emptySpots = new ArrayList<>();
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                if(matrix[i][j] == 0) {
                    emptySpots.add(new int[]{i, j});
                }
            }
        }
        return emptySpots;
    }

    public boolean spawnRandomTile(){
        ArrayList<int[]> emptySpots = getEmptyTiles();
        if(emptySpots.isEmpty()) return false;
        int[] randomSpot = emptySpots.get((int)(Math.random() * emptySpots.size()));
        int newTile = Math.random() < 0.1 ? 4 : 2;
        //System.out.println("x:" + randomSpot[0] + " y:" + randomSpot[1] + " number: " + newTile);
        matrix[randomSpot[0]][randomSpot[1]] = newTile;
        return true;
    }

    public boolean moveUp(){
        if (isGameOver()) {
            //System.out.println("Game Over");
            return false;
        }
        boolean boardChanged = false;
        for (int j = 0; j < 4; j++) {
            int lastMergePosition = 0;
            for (int i = 1; i < 4; i++) {
                if (matrix[i][j] == 0) continue;
                int current = i;
                for (int previous = i - 1; previous >= lastMergePosition; previous--) {
                    if (matrix[previous][j] == 0) {
                        matrix[previous][j] = matrix[current][j];
                        matrix[current][j] = 0;
                        current = previous;
                        boardChanged = true;
                    } else if (matrix[previous][j] == matrix[current][j]) {
                        matrix[previous][j] *= 2;
                        matrix[current][j] = 0;
                        lastMergePosition = previous + 1;
                        boardChanged = true;
                        break;
                    } else {
                        break;
                    }
                }
            }
        }
        if (boardChanged) {
            spawnRandomTile();
        }
        return boardChanged;
    }

    public boolean moveDown(){
        if (isGameOver()) {
            //System.out.println("Game Over");
            return false;
        }
        boolean boardChanged = false;
        for (int j = 0; j < 4; j++) {
            int lastMergePosition = 3;
            for (int i = 2; i >= 0; i--) {
                if (matrix[i][j] == 0) continue;
                int current = i;
                for (int next = i + 1; next <= lastMergePosition; next++) {
                    if (matrix[next][j] == 0) {
                        matrix[next][j] = matrix[current][j];
                        matrix[current][j] = 0;
                        current = next;
                        boardChanged = true;
                    } else if (matrix[next][j] == matrix[current][j]) {
                        matrix[next][j] *= 2;
                        matrix[current][j] = 0;
                        lastMergePosition = next - 1;
                        boardChanged = true;
                        break;
                    } else {
                        break;
                    }
                }
            }
        }
        if (boardChanged) {
            spawnRandomTile();
        }
        return boardChanged;
    }

    public boolean moveLeft(){
        if (isGameOver()) {
            //System.out.println("Game Over");
            return false;
        }
        boolean boardChanged = false;
        for (int i = 0; i < 4; i++) {
            int lastMergePosition = 0;
            for (int j = 1; j < 4; j++) {
                if (matrix[i][j] == 0) continue;
                int current = j;
                for (int previous = j - 1; previous >= lastMergePosition; previous--) {
                    if (matrix[i][previous] == 0) {
                        matrix[i][previous] = matrix[i][current];
                        matrix[i][current] = 0;
                        current = previous;
                        boardChanged = true;
                    } else if (matrix[i][previous] == matrix[i][current]) {
                        matrix[i][previous] *= 2;
                        matrix[i][current] = 0;
                        lastMergePosition = previous + 1;
                        boardChanged = true;
                        break;
                    } else {
                        break;
                    }
                }
            }
        }
        if (boardChanged) {
            spawnRandomTile();
        }
        return boardChanged;
    }

    public boolean moveRight(){
        if (isGameOver()) {
            //System.out.println("Game Over");
            return false;
        }
        boolean boardChanged = false;
        for (int i = 0; i < 4; i++) {
            int lastMergePosition = 3;
            for (int j = 2; j >= 0; j--) {
                if (matrix[i][j] == 0) continue;
                int current = j;
                for (int next = j + 1; next <= lastMergePosition; next++) {
                    if (matrix[i][next] == 0) {
                        matrix[i][next] = matrix[i][current];
                        matrix[i][current] = 0;
                        current = next;
                        boardChanged = true;
                    } else if (matrix[i][next] == matrix[i][current]) {
                        matrix[i][next] *= 2;
                        matrix[i][current] = 0;
                        lastMergePosition = next - 1;
                        boardChanged = true;
                        break;
                    } else {
                        break;
                    }
                }
            }
        }
        if (boardChanged) {
            spawnRandomTile();
        }
        return boardChanged;
    }

    public int[][] getMatrix(){
        return matrix;
    }

    public int[] getMatrixArray(){
        int[] matrixArray = new int[16];
        int index = 0;

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                matrixArray[index] = matrix[row][col];
                index++;
            }
        }
        //for (int num : matrixArray) System.out.println(num);
        return matrixArray;
    }

    public boolean isGameOver(){
        if (!getEmptyTiles().isEmpty()) return false;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int tile = matrix[i][j];
                // Check right neighbor
                if (j < 3 && matrix[i][j + 1] == tile) {
                    return false;
                }
                // Check left neighbor
                if (j > 0 && matrix[i][j - 1] == tile) {
                    return false;
                }
                // Check top neighbor
                if (i > 0 && matrix[i - 1][j] == tile) {
                    return false;
                }
                // Check bottom neighbor
                if (i < 3 && matrix[i + 1][j] == tile) {
                    return false;
                }
            }
        }
        return true;
    }

    public int getScore(){
        int sum = 0;
        for (int i = 0; i < 4; i++){
            for (int e = 0; e < 4; e++){
                sum += matrix[i][e];
            }
        }
        return sum;
    }
}
