package StateSpace;

import java.util.List;

public interface StateSpaceModel<STATE, ACTION> {
    /**
     * Return list of actions appliable in the state
     **/
    List<ACTION> AvailableAction(STATE state);

    /**
     * Return the result state if apply action a on state s
     **/
    STATE Result(STATE s, ACTION a);

    /**
     * Return the cost of applying action a on state s
     **/
    int Cost(STATE s, ACTION a);

    /**
     * Check if state s is goal state or not
     **/
    boolean Goal(STATE s);
}
