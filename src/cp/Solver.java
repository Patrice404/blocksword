package cp;

import java.util.Map;

import modelling.Variable;

/**
 * Interface for a general solver in a constraint satisfaction problem (CSP).
 * <p>
 * A solver is responsible for finding a valid assignment of values to variables
 * such that all the constraints in the problem are satisfied.
 * </p>
 */
public interface Solver {

    /**
     * Solves the constraint satisfaction problem and returns a valid assignment of variables.
     * <p>
     * This method attempts to find a solution to the CSP by assigning values to the variables
     * such that all constraints are satisfied. The solution is represented as a map of variables
     * to their corresponding values.
     * </p>
     *
     * @return A map representing a valid assignment of variables, where the keys are variables
     *         and the values are the assigned values. If no solution is found, the return value may be null.
     */
    public Map<Variable, Object> solve();
}
