package planning;

import java.util.*;
import modelling.Variable;

/**
 * DFSPlanner is an implementation of the Planner interface that uses Depth-First Search (DFS)
 * to find a sequence of actions leading from an initial state of blocks to a goal state in a Blocks World.
 */
public class DFSPlanner implements Planner{
    private Map<Variable, Object> initState;
    private Set<Action> actions;
    private Goal goal;
    private boolean activateNode = false;
    private int nbNoeudExplore = 0;


    /**
     * Constructs a DFSPlanner with a specified initial state, a set of available actions, and a target goal state.
     *
     * @param initState the starting configuration of the blocks
     * @param actions   the set of possible actions that can be applied
     * @param goal      the goal configuration of blocks to reach
     */
    public DFSPlanner(Map<Variable, Object> initState, Set<Action> actions, Goal goal) {
        this.initState = initState;
        this.actions = actions;
        this.goal = goal;
    }

    /**
     * Initiates the DFS planning algorithm to find an action sequence that reaches the goal state.
     * @return a stack of actions that lead from the initial state to the goal state, or {@code null} if no path is found
     */

    @Override
    public Stack<Action> plan() {
        return DFS(this,this.initState,new Stack<Action>(),new HashSet<Map<Variable,Object>>());
    }

     /**
     * Recursive DFS method that explores the state space of the Blocks World in a depth-first manner.
     * 
     * This method explores each possible action from the current state, diving deeper with each step until 
     * either the goal state is found or all paths are exhausted. If the goal is reached, the sequence of actions 
     * is returned; otherwise, it backtracks by popping the last action.
     * 
     *
     * @param problem the DFSPlanner instance containing the goal, actions, and initial state
     * @param state   the current state of the blocks in the search process
     * @param plan    the current stack of actions taken from the initial state
     * @param closed  the set of states that have been explored to avoid revisiting states
     * @return a stack of actions leading to the goal state if reachable, or {@code null} otherwise
     */
    private Stack<Action> DFS(DFSPlanner problem , Map<Variable, Object> state,Stack<Action> plan,Set<Map<Variable, Object>> closed){

        closed.add(state);
        if(problem.getGoal().isSatisfiedBy(state)){
            if(this.activateNode) this.nbNoeudExplore = closed.size();
            return plan;
        }else{
            for (Action action : this.actions) {
                if(action.isApplicable(state)){
                    Map<Variable, Object> nextState = action.successor(state);
                    if(!closed.contains(nextState)){
                        plan.add(action);
                        Stack<Action> subPlan = DFS(this, nextState, plan, closed);
                        if(!(subPlan==null)){
                            return subPlan;
                        }else{
                            plan.pop();
                        }
                    }
                }
            }
            return null;
        }
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
        return this.initState;
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
