package Algorithm;

import StateSpace.Connect_4_Model;
import StateSpace.State;

/**
 * Mimax Alogrithm:
 *      + User is Max
 *      + AI is Mini
 **/
@SuppressWarnings("ALL")
public class Connect_4_H_Minimax extends Connect_4_Algorithm<State, Integer> implements H_MinimaxAlgorithm<State, Integer> {
    Connect_4_Model connect_4_model;
    int maxDepth;
    int stateVisited;

    public Connect_4_H_Minimax(Connect_4_Model connect_4_model, int maxDepth) {
        this.connect_4_model = connect_4_model;
        this.maxDepth = maxDepth;
    }

    /**
     * The Utility function is based on the value of each cell and the the value of segment of markers
     * The value of a cell is the number of way to win containing the cell
     * The value of segment is the length of the segment and whether it's end point is blocked
     **/
    @Override
    public int Utility(State state) {
        int score = 0;

        for (int i = 0; i < state.gridWidth; i++)
            for (int j = 0; j < state.gridHeight; j++) {
                int value = 0;
                value += Math.min(Math.min(i + 1, state.gridWidth - i), state.winLength);
                value += Math.min(Math.min(j + 1, state.gridHeight - j), state.winLength);
                value += Math.min(Math.min(Math.min(i + 1, state.gridWidth - i), Math.min(j + 1, state.gridHeight - j)), state.winLength);
                value += Math.min(Math.min(Math.min(i + 1, state.gridWidth - i), Math.min(j + 1, state.gridHeight - j)), state.winLength);

                if (state.grid[i][j] == 1)
                    score += value;
                if (state.grid[i][j] == 2)
                    score -= value;
            }

        for (int i = 0; i < state.gridWidth; i++)
            for (int j = 0; j < state.gridHeight; j++)
                if (state.grid[i][j] != 0) {
                    int value, endPoint;

                    //check horizontal
                    value = 0;
                    endPoint = 0;
                    for (int k = 1; k < Math.min(state.winLength, state.gridWidth - i); k++)
                        if (state.grid[i + k][j] != state.grid[i][j]) {
                            if (state.grid[i + k][j] == 0) endPoint++;
                            break;
                        } else
                            value += 5;
                    if (i > 0)
                        if (state.grid[i - 1][j] == 0)
                            endPoint++;
                        else if (state.grid[i - 1][j] == state.grid[i][j])
                            endPoint = 0;
                    if (state.grid[i][j] == 1)
                        score += value * endPoint;
                    else
                        score -= value * endPoint;

                    //check vertical
                    value = 0;
                    endPoint = 0;
                    for (int k = 1; k < Math.min(state.winLength, state.gridHeight - j); k++)
                        if (state.grid[i][j + k] != state.grid[i][j]) {
                            if (state.grid[i][j + k] == 0) endPoint++;
                            break;
                        } else
                            value += 5;
                    if (j > 0)
                        if (state.grid[i][j - 1] == 0)
                            endPoint++;
                        else if (state.grid[i][j - 1] == state.grid[i][j])
                            endPoint = 0;
                    if (state.grid[i][j] == 1)
                        score += value * endPoint;
                    else
                        score -= value * endPoint;

                    //check diagonal bottom left to top right
                    value = 0;
                    endPoint = 0;
                    for (int k = 1; k < Math.min(state.winLength, Math.min(state.gridWidth - i, state.gridHeight - j)); k++)
                        if (state.grid[i + k][j + k] != state.grid[i][j]) {
                            if (state.grid[i + k][j + k] == 0) endPoint++;
                            break;
                        } else
                            value += 5;
                    if (i > 0 && j > 0)
                        if (state.grid[i - 1][j - 1] == 0)
                            endPoint++;
                        else if (state.grid[i - 1][j - 1] == state.grid[i][j])
                            endPoint = 0;
                    if (state.grid[i][j] == 1)
                        score += value * endPoint;
                    else
                        score -= value * endPoint;

                    //check diagonal top left to bottom right
                    value = 0;
                    endPoint = 0;
                    for (int k = 1; k < Math.min(state.winLength, Math.min(state.gridWidth - i, j + 1)); k++)
                        if (state.grid[i + k][j - k] != state.grid[i][j]) {
                            if (state.grid[i + k][j - k] == 0) endPoint++;
                            break;
                        } else
                            value += 5;
                    if (i > 0 && j < state.gridHeight - 1)
                        if (state.grid[i - 1][j + 1] == 0)
                            endPoint++;
                        else if (state.grid[i - 1][j + 1] == state.grid[i][j])
                            endPoint = 0;
                    if (state.grid[i][j] == 1)
                        score += value * endPoint;
                    else
                        score -= value * endPoint;
                }

        return score;
    }

    @Override
    public Integer MiniMax(State state, int depth, int alpha, int beta, boolean isMax) {
        this.stateVisited++;

        if (connect_4_model.Goal(state)) {
            if (isMax) return Integer.MIN_VALUE;
            else return Integer.MAX_VALUE;
        } else if (connect_4_model.Draw(state)) return 0;
        if (depth == 0)
            return Utility(state);

        int score;
        if (isMax) score = Integer.MIN_VALUE;
        else score = Integer.MAX_VALUE;

        for (Integer action: connect_4_model.AvailableAction(state))
            if (isMax) {
                score = Math.max(score, MiniMax(connect_4_model.ResultForPlayer(state, action, 1), depth-1, alpha, beta, !isMax));
                alpha = Math.max(alpha, score);
                if (score >= beta)
                    return score;
            } else {
                score = Math.min(score, MiniMax(connect_4_model.ResultForPlayer(state, action, 2), depth-1, alpha, beta, !isMax));
                beta = Math.min(beta, score);
                if (score <= alpha)
                    return score;
            }

        return score;
    }

    /**find best move for AI
     * AI is Mini
     **/
    @Override
    public Integer findBestMove(State state) {
        System.out.println("Thinking.... (AI is Mini)");
        this.stateVisited = 0;

        int score = Integer.MAX_VALUE;
        int bestMove = connect_4_model.AvailableAction(state).get(0);
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        for (Integer action: connect_4_model.AvailableAction(state)) {
            int temp = MiniMax(connect_4_model.ResultForPlayer(state, action, 2), maxDepth, alpha, beta, true);
            if (temp < score) {
                score = temp;
                bestMove = action;
            }
        }

        System.out.println("   visited " + this.stateVisited + " states");
        System.out.println("   best move " + bestMove + ", value: " + score);
        return bestMove;
    }
}
