package blocksworld;

import java.util.Map;

import modelling.Variable;
import planning.Heuristic;

/**
 * This class defines a heuristic based on the similarity between a given state
 * and a goal state in the Blocks World model.
 * 
 * The similarity is computed using cosine similarity, providing an estimate of
 * how close the current state is to the goal state. The heuristic can apply a 
 * coefficient to scale the similarity score.
 */
public class SimilarityHeuristic implements Heuristic {

    private Map<Variable, Object> goal;
    private int coef;

    /**
     * Constructs a {@code SimilarityHeuristic} with the specified goal state and 
     * a scaling coefficient.
     * 
     * @param goal The target state represented as a map of variables and their 
     *             desired values.
     * @param coef A scaling factor applied to the similarity score in the heuristic.
     */
    public SimilarityHeuristic(Map<Variable, Object> goal, int coef) {
        this.goal = goal;
        this.coef = coef;
    }

    /**
     * Constructs a {@code SimilarityHeuristic} with the specified goal state and
     * a default coefficient of {@code 1}.
     * 
     * @param goal The target state represented as a map of variables and their 
     *             desired values.
     */
    public SimilarityHeuristic(Map<Variable, Object> goal) {
        this(goal, 1); // Default coefficient changed to 1 for positive scaling
    }

    /**
     * Estimates the similarity between the current state and the goal state,
     * scaled by the specified coefficient.
     * 
     * @param state The current state represented as a map of variables and their
     *              values.
     * @return The similarity estimate scaled by the coefficient, as a floating-point value.
     */
    @Override
    public float estimate(Map<Variable, Object> state) {
        return this.coef * cosineSimilarity(this.goal, state);
    }

    /**
     * Calculates the dot product between the goal and current state vectors, considering 
     * both boolean and integer variables.
     * 
     * For boolean variables, each pair is given a value of {@code 1} if both 
     * goal and state values are {@code true}, otherwise {@code 0}.
     * 
     * @param goal  The goal state vector.
     * @param state The current state vector.
     * @return The dot product as a floating-point value.
     */
    private float product(Map<Variable, Object> goal, Map<Variable, Object> state) {
        float product = 0;
        for (Variable variable : goal.keySet()) {
            Object goalValue = goal.get(variable);
            Object stateValue = state.get(variable);

            if (goalValue instanceof Boolean && stateValue instanceof Boolean) {
                product += (goalValue.equals(stateValue)) ? 1 : 0;
            } else if (goalValue instanceof Integer && stateValue instanceof Integer) {
                product += ((Integer) goalValue) * ((Integer) stateValue);
            }
        }
        return product;
    }

    /**
     * Calculates the norm (magnitude) of the state vector, which is used in the cosine
     * similarity calculation.
     * 
     * @param state The state vector for which the norm is calculated.
     * @return The norm of the state vector as a floating-point value.
     */
    private float norm(Map<Variable, Object> state) {
        return (float) Math.sqrt(product(state, state));
    }

    /**
     * Computes the cosine similarity between the goal state and the current state.
     * 
     * The cosine similarity measures the cosine of the angle between two vectors, 
     * giving a value between -1 and 1. A higher value indicates greater similarity.
     * 
     * @param goal  The goal state vector.
     * @param state The current state vector.
     * @return The cosine similarity as a floating-point value, or 0 if one of the norms is zero.
     */
    private float cosineSimilarity(Map<Variable, Object> goal, Map<Variable, Object> state) {
        float product = this.product(goal, state);
        float norm1 = this.norm(goal);
        float norm2 = this.norm(state);
        if (norm1 == 0 || norm2 == 0) return 0;
        return product / (norm1 * norm2);
    }
}
