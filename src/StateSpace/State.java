package StateSpace;

public class State {
    /**
     * Size of the grid
     **/
    public int gridWidth, gridHeight, winLength;

    /**
     * Data of the grid
     * 0 is empty
     * 1 is user's marker
     * 2 is AI's marker
     **/
    public int[][] grid;

    public State(int gridWidth, int gridHeight, int winLength) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.winLength = winLength;
        this.grid = new int[gridWidth][gridHeight];
    }

    public State(int gridWidth, int gridHeight, int winLength, int[][] grid) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.winLength = winLength;
        this.grid = new int[gridWidth][gridHeight];
        for (int i = 0; i < gridWidth; i++)
            for (int j = 0; j < gridHeight; j++)
                this.grid[i][j] = grid[i][j];
    }
}
