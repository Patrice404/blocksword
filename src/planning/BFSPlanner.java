package planning;

import java.util.*;
import modelling.Variable;

/**
 * BFSPlanner is an implementation of the Planner interface that uses Breadth-First Search (BFS) 
 * to find a sequence of actions that transform the initial state of a Blocks World into the target goal state.
 */
public class BFSPlanner implements Planner{

    private Map<Variable, Object> initState;
    private Set<Action> actions;
    private Goal goal;
    private boolean activateNode = false;
    private int nbNoeudExplore = 0;


    /**
     * Constructs a BFSPlanner with the specified initial state, set of possible actions, and goal state.
     *
     * @param initState the starting configuration of the blocks
     * @param actions      the set of actions that can be performed to transform the state
     * @param goal         the target configuration of blocks that the planner aims to reach
     */
    public BFSPlanner(Map<Variable, Object> initState, Set<Action> actions, Goal goal) {
        this.initState = initState;
        this.actions = actions;
        this.goal = goal;
    }

     /**
     * Executes the BFS planning algorithm to generate a sequence of actions that leads to the goal state.
     * @return a list of actions that lead from the initial state to the goal state if the goal is reachable; 
     *         {@code null} otherwise
     */
    @Override
    public List<Action> plan() {
        Map<Map<Variable, Object>,Map<Variable, Object>> father = new HashMap<>();
        Map<Map<Variable, Object>,Action> plan = new HashMap<>();
        Set<Map<Variable, Object>> closed = new HashSet<>();
        LinkedList<Map<Variable, Object>> open = new LinkedList<>();
        open.add(this.initState);
        father.put(this.initState, null);
        if(this.goal.isSatisfiedBy(this.initState)){
            if(this.activateNode) nbNoeudExplore = 1;
            return new ArrayList<Action>();
        }
        while (!open.isEmpty()) {
            Map<Variable, Object> instantiation =  open.pollFirst();
            closed.add(instantiation);
            for(Action action : this.actions){
                if(action.isApplicable(instantiation)){
                    Map<Variable,Object> next = action.successor(instantiation);                   
                    if(!closed.contains(next) && !open.contains(next)){
                        father.put(next, instantiation);
                        plan.put(next,action);
                        if(this.goal.isSatisfiedBy(next)){
                            if(this.activateNode) nbNoeudExplore =closed.size();
                            return getBfsPlan(father,plan,next);
                        }else{
                            open.addLast(next);
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Constructs the BFS plan by backtracking from the goal state to the initial state.
     *
     * @param father a map tracking the parent of each state in the search tree
     * @param plan   a map associating each state with the action that led to it
     * @param goal   the goal state to backtrack from
     * @return the ordered list of actions from the initial state to the goal state
     */
    public static List<Action> getBfsPlan(Map< Map<Variable, Object>, Map<Variable, Object> >father,Map< Map<Variable, Object>, Action >plan,Map<Variable,Object> goal ){
        List<Action> list = new ArrayList<>();
        while(father.get(goal)!=null){
            list.add(plan.get(goal));
            goal = father.get(goal);
        }
        Collections.reverse(list);
        return list;
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
