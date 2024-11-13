package cp;

import java.util.*;
import modelling.Variable;

/**
 * Interface for a value heuristic used in constraint satisfaction problems (CSP).
 * <p>
 * A value heuristic is responsible for determining the order in which values should be assigned
 * to a variable during the solving process, typically to guide the search in a more efficient way.
 * </p>
 */
public interface ValueHeuristic {

    /**
     * Returns an ordering of possible values for a given variable.
     * <p>
     * This method provides an ordering of values for a variable's domain. The ordering can be based
     * on various strategies such as value preferences, randomization, or domain-specific heuristics.
     * The returned list represents the values in the preferred order for assignment.
     * </p>
     *
     * @param variable The variable whose values need to be ordered.
     * @param domain The set of possible values for the variable.
     * @return A list representing the values in the preferred order for the variable.
     */
    public List<Object> ordering(Variable variable, Set<Object> domain);
}
