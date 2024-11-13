package cp;

import java.util.*;

import modelling.Variable;

/**
 * Implements a heuristic for selecting variables based on the size of their domains.
 * <p>
 * The {@code DomainSizeVariableHeuristic} class provides a way to prioritize variables in constraint
 * satisfaction problems (CSPs) by either selecting the variable with the largest or smallest domain.
 * This can help guide the search process by using different heuristics depending on whether a 
 * larger or smaller domain is preferred.
 * </p>
 */
public class DomainSizeVariableHeuristic implements VariableHeuristic {
    private boolean largest;

    /**
     * Constructs a {@code DomainSizeVariableHeuristic} with a specified domain size preference.
     *
     * @param largest If {@code true}, selects the variable with the largest domain; if {@code false},
     * selects the variable with the smallest domain.
     */
    public DomainSizeVariableHeuristic(boolean largest) {
        this.largest = largest;
    }

    /**
     * Selects the best variable according to the domain size heuristic.
     * <p>
     * If {@code largest} is set to {@code true}, this method returns the variable with the largest domain.
     * Otherwise, it returns the variable with the smallest domain. This can help optimize the order of variable
     * assignments in CSPs to potentially reduce search time and improve performance.
     * </p>
     *
     * @param variables The set of variables to choose from.
     * @param domains A map linking each variable to its domain of possible values.
     * @return The variable with the largest or smallest domain depending on the heuristic setting, or
     * {@code null} if no variables are provided.
     */
    @Override
    public Variable best(Set<Variable> variables, Map<Variable, Set<Object>> domains) {
        if (domains == null || domains.isEmpty()) {
            return null;
        }
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        Variable maxVariable = null;
        Variable minVariable = null;

        for (Variable variable : variables) {
            // A priori toutes les variables ont leur domaine dans domains
            int domainSize = domains.get(variable).size();
            if (domainSize > max) {
                max = domainSize;
                maxVariable = variable;
            }
            if (domainSize < min) {
                min = domainSize;
                minVariable = variable;
            }
        }
        return this.largest ? maxVariable : minVariable;
    }
}
