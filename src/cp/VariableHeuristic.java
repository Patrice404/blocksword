package cp;

import java.util.*;
import modelling.Variable;

/**
 * Interface for a variable heuristic used in constraint satisfaction problems (CSP).
 * <p>
 * A variable heuristic is used to determine the order in which variables should be assigned
 * values during the search process. The goal is to guide the search towards solutions
 * by selecting the most appropriate variable to instantiate next.
 * </p>
 */
public interface VariableHeuristic {

    /**
     * Returns the variable that should be chosen next for instantiation.
     * <p>
     * This method selects the next variable to be assigned a value based on a heuristic strategy.
     * The heuristic may prioritize variables based on criteria such as the size of the domain, the number of constraints,
     * or other domain-specific rules.
     * </p>
     *
     * @param variables The set of remaining variables to choose from.
     * @param domains A map of variables to their respective domains (possible values).
     * @return The variable that should be chosen next based on the heuristic.
     */
    public Variable best(Set<Variable> variables, Map<Variable, Set<Object>> domains);
}
