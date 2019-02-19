package Algorithm;

public interface AB_MinimaxAlgorithm<STATE, ACTION> {

    /**
     * Run MiniMax Graph explore
     **/
    ACTION MiniMax(STATE state, int alpha, int beta, boolean isMax);
}
