package planning;

import java.util.Map;

import modelling.Variable;

/**
 * Represents a heuristic function for estimating the cost or distance 
 * to reach a goal from a given state in a planning model.
 * 
 * This interface defines a method to evaluate a heuristic value based on 
 * a given state, represented as a map of variables and their current values. 
 * Heuristics are typically used in search algorithms to prioritize nodes 
 * closer to the goal.
 */
public interface Heuristic {
     /**
     * Estimates the heuristic cost or distance to the goal from the given state.
     * 
     * @param var A map representing the current state, where each {@link Variable} 
     *            is mapped to an {@code Object} representing its value.
     * @return A floating-point number representing the heuristic estimate; 
     *         lower values indicate states closer to the goal.
     */
    public float estimate(Map<Variable,Object> var);
}
