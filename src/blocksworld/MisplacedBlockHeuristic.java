package blocksworld;

import java.util.Map;

import modelling.Variable;
import planning.BasicGoal;
import planning.Heuristic;

/**
 * MisplacedBlockHeuristic calculates a heuristic estimate based on the number of
 * misplaced blocks
 * relative to a target goal state in the Blocks World. This heuristic helps
 * guide search algorithms
 * by providing a rough cost of how far the current state is from the goal.
 */
public class MisplacedBlockHeuristic implements Heuristic {
    private BasicGoal goal;

    /**
     * Initializes the heuristic with the specified goal state.
     *
     * @param goal the goal state representing the target arrangement of blocks
     */
    public MisplacedBlockHeuristic(BasicGoal goal) {
        this.goal = goal;
    }

    /**
     * Estimates the heuristic cost by counting the number of blocks that are not in
     * their correct
     * positions according to the goal state.
     * 
     * @param instanciation the current state of blocks as a map of variables and
     *                      their current values
     * @return a float representing the count of misplaced blocks, providing a rough
     *         measure of the distance to the goal
     */

    @Override
    public float estimate(Map<Variable, Object> instanciation) {
        float nbBlocks = 0;
        for (Map.Entry<Variable, Object> entry : this.goal.getState().entrySet()) {
            Variable variable = entry.getKey();
            Object valueInGoal = entry.getValue();
            if (Variable.isBlockOnVariable(variable)) {
                Object valueInInstanciation = instanciation.get(variable);
                if(!valueInInstanciation.equals(valueInGoal)){
                    nbBlocks++;
                }
            }
        }
        return nbBlocks;
    }

}
