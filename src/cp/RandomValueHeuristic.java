package cp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

import modelling.Variable;

/**
 * A heuristic for ordering values in a random order for a given variable's domain.
 * <p>
 * This heuristic randomly shuffles the values in the domain of a variable, and can be used to introduce randomness
 * in search algorithms.
 * </p>
 */
public class RandomValueHeuristic implements ValueHeuristic {
    private Random generator;

    /**
     * Constructs a {@code RandomValueHeuristic} with the specified random number generator.
     * <p>
     * The random number generator is used to shuffle the domain values for a variable in a random order.
     * </p>
     *
     * @param generator The random number generator used for shuffling the values.
     */
    public RandomValueHeuristic(Random generator) {
        this.generator = generator;
    }

    /**
     * Orders the values in the domain of the given variable randomly.
     * <p>
     * This method shuffles the values of the domain and returns the list in a randomized order.
     * It uses the Fisher-Yates shuffle algorithm to ensure uniform randomness.
     * </p>
     *
     * @param variable The variable whose domain values are being ordered.
     * @param domain   The set of possible values for the given variable.
     * @return A list of domain values ordered randomly.
     */
    @Override
    public List<Object> ordering(Variable variable, Set<Object> domain) {
        List<Object> result = new ArrayList<>(domain);
        // Fisher-Yates shuffle
        for (int i = result.size() - 1; i >= 1; i--) {
            int j = this.generator.nextInt(i + 1);
            // Swap the element at the i-th position with the element at index j
            Collections.swap(result, i, j);
        }
        return result;
    }
}
