package blocksworld;

import java.util.Map;

import modelling.Variable;
import planning.BasicGoal;
import planning.Heuristic;

/**
 * BlocBienPoseHeuristic calculates a heuristic estimate based on the number of
 * correctly placed
 * blocks relative to a target goal state in the Blocks World. This heuristic is
 * useful for search
 * algorithms by providing a measure of how close the current state is to the
 * goal.
 */
public class WellplacedBlock implements Heuristic {
    private BasicGoal goal;

    /**
     * Initializes the heuristic with the specified goal state.
     *
     * @param goal the goal state representing the target arrangement of blocks
     */

    public WellplacedBlock(BasicGoal goal) {
        this.goal = goal;
    }

    /**
     * Estimates the heuristic value by counting the number of blocks that are
     * correctly positioned
     * according to the goal state.
     *
     * @param instanciation the current state of blocks as a map of variables and
     *                      their current values
     * @return a float representing the count of correctly positioned blocks,
     *         providing a positive measure
     *         of closeness to the goal
     */
    @Override
    public float estimate(Map<Variable, Object> instanciation) {
        float nbBlocks = 0;
        for (Map.Entry<Variable, Object> entry : this.goal.getState().entrySet()) {
            Variable variable = entry.getKey();
            Object valueInGoal = entry.getValue();
            if (Variable.isBlockOnVariable(variable)) {
                Object valueInInstanciation = instanciation.get(variable);
                if (valueInInstanciation.equals(valueInGoal)) {
                    nbBlocks++;
                }
            }
        }
        return nbBlocks;
    }

}
