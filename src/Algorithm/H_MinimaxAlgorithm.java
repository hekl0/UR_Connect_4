package Algorithm;

public interface H_MinimaxAlgorithm<STATE, ACTION> {
    /**
     * Return heuristic cost of cut-off test
     **/
    int Utility(STATE state);

    /**
     * Run MiniMax Graph explore
     **/
    ACTION MiniMax(STATE state, int depth, int alpha, int beta, boolean isMax);
}
