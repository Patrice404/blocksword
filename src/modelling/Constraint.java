package modelling;

import java.util.*;


/**
 * This interface represents a constraint in a constraint satisfaction problem
 * (CSP).
 * A constraint defines a relation between a set of variables, and it is
 * satisfied
 * when those variables take values that respect the relation.
 * 
 * Implementations of this interface should specify how the scope of the
 * constraint
 * is determined and how satisfaction is checked.
 * 
 */
public interface Constraint {
    /**
     * Returns the set of variables that are involved in this constraint.
     * This set defines the "scope" of the constraint.
     * 
     * @return A set of {@link Variable} objects representing the scope of the
     *         constraint.
     */
    public Set<Variable> getScope();

    /**
     * Checks whether the given instantiation satisfies this constraint.
     * The instantiation is a mapping from variables to their respective values.
     * This method returns true if the provided instantiation satisfies the
     * constraint,
     * and false otherwise.
     * 
     * @param instanciation A map representing the current assignment of variables
     *                      to values.
     *                      The keys are {@link Variable} objects, and the values
     *                      are their assigned values.
     * @return {@code true} if the constraint is satisfied by the given
     *         instantiation,
     *         {@code false} otherwise.
     * @throws IllegalArgumentException if the instantiation is invalid, such as if
     *                                  it
     *                                  does not contain all variables in the scope
     *                                  of the constraint.
     */
    public boolean isSatisfiedBy(Map<Variable, Object> instanciation) throws IllegalArgumentException;
}
