package planning.utils;

import java.util.*;

import modelling.Variable;

/**
 * Represents a state in the planning process, along with its associated cost.
 * 
 * This class encapsulates a state, represented by a map of variables and their
 * values,
 * and the cost to reach this state. It is commonly used in planning algorithms
 * to
 * evaluate and compare different states based on their respective costs.
 * 
 * @see Variable
 */
public class StateWithDistance {

    private Map<Variable, Object> state;
    private Float cost;

    /**
     * Constructs a `StateWithDistance` with the specified state and cost.
     * 
     * @param state A map representing the state, with each variable mapped to its
     *              current value.
     * @param cost  The cost associated with reaching this state, used for
     *              comparison in planning algorithms.
     */
    public StateWithDistance(Map<Variable, Object> state, Float cost) {
        this.state = state;
        this.cost = cost;
    }

    /**
     * Returns the state associated with this instance.
     * 
     * @return A map representing the current state, where each variable is mapped
     *         to its value.
     */

    public Map<Variable, Object> getState() {
        return state;
    }

    /**
     * Returns the cost associated with reaching this state.
     * 
     * @return The cost of this state as a `Float`.
     */

    public Float getCost() {
        return cost;
    }

}