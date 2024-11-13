package planning;

import java.util.*;

import modelling.Variable;
import planning.utils.InstanceComparator;
import planning.utils.StateWithDistance;

/**
 * DijkstraPlanner implements a planner that uses Dijkstra's algorithm to search for the optimal 
 * sequence of actions from the initial state to a goal state in the Blocks World.
 */
public class DijkstraPlanner implements Planner{
    private Map<Variable, Object> initState;
    private Set<Action> actions;
    private Goal goal;
    private boolean activateNode = false;
    private int nbNoeudExplore = 0;
    
     /**
     * Constructs a DijkstraPlanner with the specified initial state, set of actions, and goal.
     *
     * @param initState the initial state as a map of variables and values
     * @param actions the set of possible actions for transitioning between states
     * @param goal the target goal state that this planner aims to achieve
     */
    public DijkstraPlanner(Map<Variable, Object> initState, Set<Action> actions, Goal goal) {
        this.initState = initState;
        this.actions = actions;
        this.goal = goal;
    }

    /**
     * Executes Dijkstra's algorithm to find the least-cost sequence of actions to reach the goal.
     * @return a list of actions representing the optimal plan to reach the goal, or {@code null} 
     *         if no solution exists
     */

    @Override
    public List<Action> plan() {

        Map<Map<Variable, Object>,Action> plan = new HashMap<>();
        Map<Map<Variable, Object>,Float> distance = new HashMap<>();

        Map<Map<Variable, Object>,Map<Variable, Object>> father = new HashMap<>();
        //On utilise le type PriorityQueue pour gérer les ajouts et accès dans open
        PriorityQueue<StateWithDistance> open = new PriorityQueue<StateWithDistance>(new InstanceComparator());
        Set<Map<Variable, Object>> goals = new HashSet<>();
        
        father.put(this.initState, null);
        distance.put(this.initState,0f);

        open.add(new StateWithDistance(initState, 0f));

        while(!open.isEmpty()){
            StateWithDistance instantiation = open.remove();
            if (this.activateNode) this.nbNoeudExplore++;
            //open.remove(instantiation);
            if(this.goal.isSatisfiedBy(instantiation.getState())){
                goals.add(instantiation.getState());
            }
            for(Action action : this.actions){
                if(action.isApplicable(instantiation.getState())){
                    Map<Variable,Object> next = action.successor(instantiation.getState());
                    if(!distance.containsKey(next)){
                        distance.put(next, Float.MAX_VALUE);
                    }
                    if(distance.get(next)>distance.get(instantiation.getState())+action.getCost()){
                        Float newDistance = distance.get(instantiation.getState()) + action.getCost();
                        distance.put(next, newDistance);
                        father.put(next,instantiation.getState());
                        plan.put(next,action);
                        StateWithDistance newState = new StateWithDistance(next, newDistance);
                        open.add(newState);
                    }
                }
            }
        }
        if(goals.isEmpty()){
            return null;
        }else{
            return getDijkstraPlan(father,plan,goals,distance);
        }
    }

    /**
     * Constructs the optimal action sequence by backtracking from the goal state to the initial state.
     *
     * @param father a map representing each state's predecessor in the optimal path
     * @param plan a map of actions taken to reach each state
     * @param goals a set of possible goal states
     * @param distance a map of accumulated costs to reach each state
     * @return a list of actions from the initial state to the goal state, in the correct order
     */
    private List<Action> getDijkstraPlan(Map< Map<Variable, Object>, Map<Variable, Object> >father,Map< Map<Variable, Object>, Action >plan,Set<Map<Variable, Object>> goals, Map<Map<Variable, Object>,Float> distance ){
        LinkedList<Action> list = new LinkedList<>();
        Map<Variable,Object> goal = getInstanceWithSmallDistance(goals,distance);
        while(father.get(goal)!=null){
            list.addLast(plan.get(goal));
            goal = father.get(goal);
        }
        Collections.reverse(list);
        return list;
    }

     /**
     * Identifies the state with the smallest accumulated distance.
     *
     * @param instances a set of goal states to choose from
     * @param distance a map of accumulated distances for each state
     * @return the goal state with the minimum distance
     */
    private Map<Variable,Object> getInstanceWithSmallDistance (Set<Map<Variable, Object>>instances,Map<Map<Variable, Object>,Float> distance){
        Float min=Float.MAX_VALUE;
        Map<Variable, Object> minInstance = null;
        for(Map<Variable, Object> instance : instances){
            if(distance.get(instance)<min){
                min = distance.get(instance);
                minInstance = instance;
            }
        }
        return minInstance;
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
