package Algorithm;

public abstract class Connect_4_Algorithm<STATE, ACTION> {
    abstract public ACTION findBestMove(STATE state);
}
