package modelling;

import java.util.*;

/**
 * This class represents a constraint that ensures two blocks are placed on
 * different
 * locations. Specifically, it enforces that the "on" variable of one block
 * differs from
 * the "on" variable of another block.
 * 
 * The constraint is satisfied if the two specified variables have different
 * values
 * in a given instantiation.
 */
public class DifferenceConstraint implements Constraint {
    private Variable variable1;
    private Variable variable2;

    /**
     * Constructs an {@code OnDifferenceConstraint} with the specified "on"
     * variables
     * for two distinct blocks.
     * 
     * This constructor enforces constraints to ensure that each variable represents
     * a valid "on" relationship associated with a block. The two variables must be
     * distinct
     * and not null, and each must be a {@code Variable} instance with a
     * non-negative name,
     * indicating a block's "on" status.
     * 
     * @param variable1 The "on" variable associated with the first block. Must be a
     *                  non-null instance of {@code Variable} with a non-negative
     *                  name.
     * @param variable2 The "on" variable associated with the second block. Must be
     *                  a
     *                  non-null instance of {@code Variable} with a non-negative
     *                  name.
     * 
     * @throws IllegalArgumentException if either variable is {@code null}, not a
     *                                  valid "on"
     *                                  variable for a block, or if
     *                                  {@code variable1} and {@code variable2} are
     *                                  the same.
     */
    public DifferenceConstraint(Variable variable1, Variable variable2) {
        if (variable1 == null || variable2 == null) {
            throw new IllegalArgumentException("Variables can't not be null");
        }
        if (!Variable.isBlockOnVariable(variable1)) {
            throw new IllegalArgumentException("Variables in OnDifferenceConstraint must be block's on variable");
        }
        if (!Variable.isBlockOnVariable(variable2)) {
            throw new IllegalArgumentException("Variables in OnDifferenceConstraint must be block's on variable");
        }
        if (variable1.equals(variable2)) {
            throw new IllegalArgumentException("Variables in OnDifferenceConstraint must be different");
        }

        this.variable1 = variable1;
        this.variable2 = variable2;
    }

    /**
     * Returns the set of variables involved in this constraint.
     * 
     * @return A set containing {@code variable1} and {@code variable2}.
     */
    @Override
    public Set<Variable> getScope() {
        Set<Variable> variables = new HashSet<>();
        variables.add(this.variable1);
        variables.add(this.variable2);
        return variables;
    }

    /**
     * Checks if the provided instantiation satisfies this constraint.
     * The constraint is satisfied if the two variables have different values.
     * 
     * @param instanciation A map representing the assignment of values to
     *                      variables.
     * @return {@code true} if the values of {@code variable1} and {@code variable2}
     *         in the given instantiation are different; {@code false} otherwise.
     * @throws IllegalArgumentException if the instantiation does not contain both
     *                                  variables or if the values are null.
     */
    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> instanciation) throws IllegalArgumentException {
        Object v1 = instanciation.get(this.variable1);
        Object v2 = instanciation.get(this.variable2);
        if (v1 == null || v2 == null) {
            throw new IllegalArgumentException("Bad instanciation");
        }
        if (v1.equals(v2)) {
            return false;
        }
        return true;
    }

    /**
     * Returns a string representation of this constraint, indicating the two
     * variables
     * involved in the "on" difference constraint.
     * 
     * @return A string describing the constraint between the two variables.
     */
    @Override
    public String toString() {
        return "Contrainte de type OnDiff entre " + variable1.getName() + " et " + variable2.getName();
    }

    @Override
    public int hashCode() {
        return this.variable1.hashCode() + this.variable2.hashCode() + DifferenceConstraint.class.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        DifferenceConstraint c = (DifferenceConstraint) obj;
        return this.getScope().equals(c.getScope()) && obj.getClass().equals(DifferenceConstraint.class);
    }

}
