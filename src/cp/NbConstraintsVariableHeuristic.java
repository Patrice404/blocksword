package cp;

import java.util.*;

import modelling.Constraint;
import modelling.Variable;

/**
 * Heuristic for selecting the most or least constrained variable based on the number of constraints in which it is involved.
 * <p>
 * This heuristic is typically used in constraint satisfaction problems (CSP) to decide which variable to assign a value to next.
 * The variable with the highest or lowest number of constraints can be chosen to optimize the search process.
 * </p>
 */
public class NbConstraintsVariableHeuristic implements VariableHeuristic {

    private Set<Constraint> constraints;
    private boolean variableWithMostConstraints;

    /**
     * Constructs a {@code NbConstraintsVariableHeuristic} with the given set of constraints.
     * The heuristic will select the variable with either the most or the least constraints, depending on the specified flag.
     *
     * @param constraints               The set of constraints that are involved in the CSP.
     * @param variableWithMostConstraints If {@code true}, the variable with the most constraints will be selected.
     *                                    If {@code false}, the variable with the least constraints will be selected.
     */
    public NbConstraintsVariableHeuristic(Set<Constraint> constraints, boolean variableWithMostConstraints) {
        this.constraints = constraints;
        this.variableWithMostConstraints = variableWithMostConstraints;
    }

    /**
     * Selects the variable that is involved in the most or least constraints, depending on the configuration.
     * This heuristic is commonly used for variable ordering in CSP solvers.
     * <p>
     * The method counts the number of constraints each variable appears in, and depending on the configuration,
     * either returns the variable with the most constraints or the least.
     * </p>
     *
     * @param variables The set of variables to choose from.
     * @param domains   The domains of each variable (not used in this heuristic, but passed to comply with the interface).
     * @return The most or least constrained variable.
     */
    @Override
    public Variable best(Set<Variable> variables, Map<Variable, Set<Object>> domains) {
        if (variables == null || variables.size() == 0) {
            return null;
        }

        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        Variable maxVariable = null;
        Variable minVariable = null;

        for (Variable variable : variables) {
            int occurrence = 0;

            // Count how many constraints the variable is involved in
            for (Constraint constraint : this.constraints) {
                if (constraint.getScope().contains(variable)) {
                    occurrence++;
                }
            }

            // Track the variable with the maximum number of constraints
            if (occurrence > max) {
                max = occurrence;
                maxVariable = variable;
            }

            // Track the variable with the minimum number of constraints
            if (occurrence < min) {
                min = occurrence;
                minVariable = variable;
            }
        }

        // Return the variable with the most or least constraints based on the configuration
        if (this.variableWithMostConstraints) {
            return maxVariable;
        }
        return minVariable;
    }
}
