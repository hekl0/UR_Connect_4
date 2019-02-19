package Algorithm;

public interface MiniMaxAlgorithm<STATE, ACTION> {
    /**
     * Run MiniMax Graph explore
     **/
    ACTION MiniMax(STATE state, boolean isMax);
}
