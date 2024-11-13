package planning;

import java.util.*;

import modelling.Variable;
/**
 * BasicGoal defines a goal state for the Blocks World planning problem. 
 * It holds a target configuration of blocks and can check if a given state meets this goal.
 * 
 * This goal is represented by a map of Variables to their target values, specifying 
 * the desired final positions or states of the blocks.
 * 
 */
public class BasicGoal implements Goal {
    private Map<Variable, Object> state;

    /**
     * Constructs a BasicGoal with the specified target block configuration.
     *
     * @param state a map representing the target state, where each Variable key 
     *              is associated with its desired value in the final configuration.
     */
    public BasicGoal(Map<Variable, Object> state) {
        this.state = state;
    }

    
    /**
     * Determines if the provided instantiation of blocks matches the goal configuration.
     *
     * @param instanciation the current configuration of blocks to check against the goal
     * @return {@code true} if the instantiation matches the goal configuration, {@code false} otherwise
     */
    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> instanciation) {
        //Vérifier si toutes les variables dans but ont la bonne valeur dans state
        for (Map.Entry<Variable, Object> entry : this.state.entrySet()) {
            Variable key = entry.getKey();
            Object value = entry.getValue();
            if(instanciation.get(key)==null){
                return false;
            }
            if(!instanciation.get(key).equals(value)){
                return false;
            }

        }
        return true;
    }

    /**
     * Returns the target state configuration of the goal.
     *
     * @return the goal configuration as a map of variables to desired values
     */
    public Map<Variable, Object> getState() {
        return state;
    }
}
