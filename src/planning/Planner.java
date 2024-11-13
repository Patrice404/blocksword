package planning;

import java.util.*;

import modelling.Variable;

/**
 * Interface representing a planner in an automated planning system.
 * 
 * A planner generates a sequence of actions that transform an initial state into a
 * state that satisfies a given goal. It provides methods to retrieve the initial
 * state, available actions, and the goal to achieve.
 */
public interface Planner {

    /**
     * Generates a plan to reach the goal from the initial state.
     * 
     * @return A list of {@link Action} objects representing the sequence of actions
     *         to reach the goal, or an empty list if no plan exists.
     */
    public List<Action> plan();

    /**
     * Retrieves the initial state for the planning process.
     * 
     * @return A map representing the initial state, where each {@link Variable}
     *         is mapped to its initial value.
     */
    public Map<Variable, Object> getInitialState();

    /**
     * Retrieves the set of actions available in the planning domain.
     * 
     * @return A set of {@link Action} objects that can be performed in the plan.
     */
    public Set<Action> getActions();

    /**
     * Retrieves the goal state that the planner aims to achieve.
     * 
     * @return A {@link Goal} object representing the target state for the plan.
     */
    public Goal getGoal();

    /**
     * Activates or deactivates node counting, if supported, for performance
     * analysis.
     * 
     * @param activate {@code true} to activate node counting, {@code false} to
     *                 deactivate it.
     */
    public void activateNodeCount(boolean activate);
}
