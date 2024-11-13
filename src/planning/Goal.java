package planning;


import java.util.Map;

import modelling.Variable;
/**
 * Represents a goal in a planning model.
 * 
 * A goal defines a condition that must be satisfied by a given state in a 
 * planning problem. This interface requires implementing classes to specify
 * the {@link #isSatisfiedBy(Map)} method to evaluate whether a state satisfies 
 * the goal condition.
 * 
 */

public interface Goal {

     /**
     * Checks if the specified state satisfies the goal.
     * 
     * @param state A map representing the current state, where each {@link Variable} 
     *              is mapped to an {@code Object} that represents its value in the state.
     * @return {@code true} if the state satisfies the goal condition; 
     *         {@code false} otherwise.
     */
    public boolean isSatisfiedBy(Map<Variable,Object> state);
}
