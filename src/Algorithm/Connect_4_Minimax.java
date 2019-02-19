package Algorithm;

import StateSpace.Connect_4_Model;
import StateSpace.State;

public class Connect_4_Minimax extends Connect_4_Algorithm<State, Integer> implements MiniMaxAlgorithm<State, Integer> {
    Connect_4_Model connect_4_model;
    int stateVisited;

    public Connect_4_Minimax(Connect_4_Model connect_4_model) {
        this.connect_4_model = connect_4_model;
    }

    @Override
    public Integer MiniMax(State state, boolean isMax) {
        this.stateVisited++;

        if (connect_4_model.Goal(state)) {
            if (isMax) return Integer.MIN_VALUE;
            else return Integer.MAX_VALUE;
        } else if (connect_4_model.Draw(state)) return 0;

        int score;
        if (isMax) score = Integer.MIN_VALUE;
        else score = Integer.MAX_VALUE;

        for (Integer action: connect_4_model.AvailableAction(state))
            if (isMax) {
                score = Math.max(score, MiniMax(connect_4_model.ResultForPlayer(state, action, 1), !isMax));
            } else {
                score = Math.min(score, MiniMax(connect_4_model.ResultForPlayer(state, action, 2), !isMax));
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
            int temp = MiniMax(connect_4_model.ResultForPlayer(state, action, 2), true);
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
