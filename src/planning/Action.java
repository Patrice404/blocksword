package planning;

import java.util.Map;

import modelling.Variable;

/**
 * The Action interface represents an action that can be applied within a planning context.
 * It defines methods to check the applicability of the action, generate a successor
 * state based on the action, and obtain the cost of performing the action.
 */
public interface Action {
    /**
     * Checks if the action is applicable for a given instantiation of variables.
     *
     * @param instantiation a mapping of variables represented by {@code Map<Variable, Object>},
     *                      where each key is a variable and the value is its current assignment.
     * @return {@code true} if the action is applicable in the state represented by
     *         the given instantiation, {@code false} otherwise.
     */
    public boolean isApplicable(Map<Variable, Object> instanciation);

    /**
     * Generates a new successor state by applying the action to the current instantiation.
     *
     * @param instantiation the current state represented by {@code Map<Variable, Object>}
     *                      where each variable is assigned its current value.
     * @return a new {@code Map<Variable, Object>} representing the successor state
     *         after applying the action.
     */
    public Map<Variable, Object> successor(Map<Variable, Object> instanciation);

     /**
     * Returns the cost of the action.
     * @return an integer representing the cost of the action.
     */
    public int getCost();
}
