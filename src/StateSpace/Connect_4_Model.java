package StateSpace;

import java.util.ArrayList;
import java.util.List;

public class Connect_4_Model implements StateSpaceModel<State, Integer> {
    public int player;
    public State currentState;

    public Connect_4_Model(int gridWidth, int gridHeight, int winLength) {
        currentState = new State(gridWidth, gridHeight, winLength);
    }

    /**
     * Set which player is playing
     * 1 is User
     * 2 is AI
     **/
    public void setPlayer(int player) {
        this.player = player;
    }


    /**
     * Return the result state if apply action a on state s by payer
     **/
    public State ResultForPlayer(State s, Integer a, int player) {
        int temp = this.player;
        this.setPlayer(player);
        State res = Result(s, a);
        this.setPlayer(temp);
        return res;
    }

    @Override
    public List<Integer> AvailableAction(State state) {
        List<Integer> actionsList = new ArrayList<>();

        for (int i = 0; i < state.gridWidth; i++)
            if (state.grid[i][state.gridHeight - 1] == 0)
                actionsList.add(i);

        return actionsList;
    }

    @Override
    public State Result(State s, Integer a) {
        State newState = new State(s.gridWidth, s.gridHeight, s.winLength, s.grid);
        for (int j = 0; j < s.gridHeight; j++)
            if (newState.grid[a][j] == 0) {
                newState.grid[a][j] = player;
                break;
            }
        return newState;
    }

    @Override
    public int Cost(State s, Integer a) {
        return 0;
    }

    @Override
    public boolean Goal(State s) {
        for (int i = 0; i < s.gridWidth; i++)
            for (int j = 0; j < s.gridHeight; j++)
                if (s.grid[i][j] != 0) {
                    //check horizontal
                    if (i + s.winLength <= s.gridWidth) {
                        boolean ok = true;
                        for (int k = 0; k < s.winLength; k++)
                            if (s.grid[i + k][j] != s.grid[i][j]) {
                                ok = false;
                                break;
                            }
                        if (ok) return true;
                    }

                    //check vertical
                    if (j + s.winLength <= s.gridHeight) {
                        boolean ok = true;
                        for (int k = 0; k < s.winLength; k++)
                            if (s.grid[i][j + k] != s.grid[i][j]) {
                                ok = false;
                                break;
                            }
                        if (ok) return true;
                    }

                    //check diagonal bottom left to top right
                    if (i + s.winLength <= s.gridWidth && j + s.winLength <= s.gridHeight) {
                        boolean ok = true;
                        for (int k = 0; k < s.winLength; k++)
                            if (s.grid[i + k][j + k] != s.grid[i][j]) {
                                ok = false;
                                break;
                            }
                        if (ok) return true;
                    }

                    //check diagonal top left to bottom right
                    if (i + s.winLength <= s.gridWidth && j - s.winLength >= -1) {
                        boolean ok = true;
                        for (int k = 0; k < s.winLength; k++)
                            if (s.grid[i + k][j - k] != s.grid[i][j]) {
                                ok = false;
                                break;
                            }
                        if (ok) return true;
                    }
                }

        return false;
    }

    /**
     * Check is state is draw or not
     **/
    public boolean Draw(State s) {
        boolean draw = true;
        for (int i = 0; i < s.gridWidth; i++)
            for (int j = 0; j < s.gridHeight; j++)
                if (s.grid[i][j] == 0)
                    draw = false;
        return draw;
    }
}
