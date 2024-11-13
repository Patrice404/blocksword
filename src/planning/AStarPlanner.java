package planning;

import java.util.*;

import modelling.Variable;
import planning.utils.InstanceComparator;
import planning.utils.StateWithDistance;

/**
 * AStarPlanner is an A* search algorithm implementation for the Blocks World problem.
 * It finds an optimal plan to achieve a target configuration of blocks from an initial 
 * configuration, given a set of actions and a heuristic function.
 * 
 * In this Blocks World context, each state represents a configuration of blocks, and each 
 * action manipulates the arrangement of the blocks. The planner can also keep track of 
 * explored nodes, and it can display the number of nodes explored if node count activation 
 * is enabled.
 * 
 */
public class AStarPlanner implements Planner {
    private Map<Variable, Object> initialState;
    private Set<Action> actions;
    private Goal goal;
    private Heuristic heuristic;
    private boolean activateNode = false;
    private int nbNoeudExplore = 0;

    /**
     * Constructs an AStarPlanner for solving the Blocks World planning problem, initialized
     * with the starting state, available actions, goal state, and heuristic function.
     *
     * @param initialState the initial configuration of blocks as a map of variables to their states
     * @param actions      the set of actions available to manipulate the blocks configuration
     * @param goal         the target configuration of blocks, represented by a Goal object
     * @param heuristic    the heuristic function estimating the cost to reach the goal configuration
     */
    public AStarPlanner(Map<Variable, Object> initialState, Set<Action> actions, Goal goal, Heuristic heuristic) {
        this.initialState = initialState;
        this.actions = actions;
        this.goal = goal;
        this.heuristic = heuristic;
    }


    /**
     * Executes the A* search algorithm to determine an optimal sequence of actions 
     * for rearranging the blocks from the initial configuration to the goal configuration.
     * The algorithm explores states with the lowest estimated total cost (distance + heuristic) first.
     *
     * @return a list of actions representing the optimal plan to achieve the goal configuration,
     *         or {@code null} if no plan is found.
     */
    @Override
    public List<Action> plan() {

        Map<Map<Variable, Object>, Action> plan = new HashMap<>();
        Map<Map<Variable, Object>, Map<Variable, Object>> father = new HashMap<>();

        Map<Map<Variable, Object>, Float> distance = new HashMap<>();
        Map<Map<Variable, Object>, Float> value = new HashMap<>();
        value.put(this.initialState, heuristic.estimate(this.initialState));

        PriorityQueue<StateWithDistance> open = new PriorityQueue<StateWithDistance>(new InstanceComparator());
        open.add(new StateWithDistance(initialState, heuristic.estimate(this.initialState)));

        father.put(this.initialState, null);
        distance.put(this.initialState, 0f);

        while (!open.isEmpty()) {
            StateWithDistance instantiation = open.remove();
            if (this.activateNode){
                this.nbNoeudExplore++;
            }
            if (this.goal.isSatisfiedBy(instantiation.getState())) {
                return BFSPlanner.getBfsPlan(father, plan, instantiation.getState());
            } else {
               // open.remove(instantiation);
                for (Action action : this.actions) {
                    if (action.isApplicable(instantiation.getState())) {
                        Map<Variable, Object> next = action.successor(instantiation.getState());
                       
                        if (!distance.containsKey(next)) {
                            distance.put(next, Float.MAX_VALUE);
                        }
                        if (distance.get(next) > distance.get(instantiation.getState()) + action.getCost()) {
                            Float newDistance = distance.get(instantiation.getState()) + action.getCost();
                            distance.put(next, newDistance);
                            Float newValue = newDistance + heuristic.estimate(next);
                            value.put(next, newValue);
                            father.put(next, instantiation.getState());
                            plan.put(next, action);
                            open.add(new StateWithDistance(next, newValue));
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Returns the number of nodes explored during the A* search.
     *
     * @return the number of nodes explored
     */
    public int getNbNoeudExplore() {
        return nbNoeudExplore;
    }

     /**
     * Returns the initial block configuration from which the planner starts.
     *
     * @return the initial configuration as a map of variables to values
     */
    @Override
    public Map<Variable, Object> getInitialState() {
        return this.initialState;
    }

    /**
     * Returns the set of actions available to the planner to manipulate the blocks.
     *
     * @return the set of actions available for planning
     */
    @Override
    public Set<Action> getActions() {
        return this.actions;
    }


    /**
     * Returns the goal configuration that the planner aims to achieve in the Blocks World.
     *
     * @return the goal object defining the target configuration of blocks
     */
    @Override
    public Goal getGoal() {
        return this.goal;
    }

     /**
     * Activates or deactivates the node count feature, which if enabled, displays the
     * number of nodes explored at the end of the search. 
     * @param activate {@code true} to activate node count display, {@code false} to deactivate
     */
    @Override
    public void activateNodeCount(boolean activate) {
        this.activateNode = activate;
    }

}
