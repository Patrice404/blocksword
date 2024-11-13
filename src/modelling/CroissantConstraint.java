package modelling;

import java.util.*;

/**
 * This class represents a constraint that enforces an ordering between two
 * variables in an "increasing" or "croissant" order.
 * Specifically, the "on" value of {@code variable1} must be greater than the
 * "on" value of {@code variable2}.
 * 
 */
public class CroissantConstraint implements Constraint {

    private Variable variable1;
    private Variable variable2;

    /**
     * Constructs a {@code CroissantConstraint} with the specified "on" variables
     * for two distinct blocks.
     * 
     * This constructor ensures that the provided variables are valid "block on"
     * variables. Each variable must be distinct, non-null, and represent a 
     * "block on" relationship, identified by a non-negative name.
     * 
     * @param variable1 The "on" variable associated with the first block,
     *                  representing a non-null instance of {@code Variable} with a non-negative name.
     * @param variable2 The "on" variable associated with the second block,
     *                  representing a non-null instance of {@code Variable} with a non-negative name.
     * 
     * @throws NullPointerException     if either variable is {@code null}.
     * @throws IllegalArgumentException if either variable is not a valid "block on"
     *                                  variable (i.e., not an instance of {@code Variable}
     *                                  with a non-negative name), or if {@code variable1} and
     *                                  {@code variable2} are the same.
     */
    public CroissantConstraint(Variable variable1, Variable variable2) {
        if (variable1 == null || variable2 == null) {
            throw new IllegalArgumentException("Variable in CroissantConstraint can't be null");
        }
        if (!Variable.isBlockOnVariable(variable1)) {
            throw new IllegalArgumentException(
                    "Variables in CroissantConstraint must be block's on variable");
        }
        if (!Variable.isBlockOnVariable(variable2)) {
            throw new IllegalArgumentException(
                    "Variables in CroissantConstraint must be block's on variable");
        }
        if (variable1.equals(variable2)) {
            throw new IllegalArgumentException("Variables in CroissantConstraint must be different");
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
     * 
     * The constraint is satisfied if:
     * 1. The value of {@code variable1} is not equal to the name of {@code variable2}.
     * 2. If the value of {@code variable1} equals the name of {@code variable2},
     * then the name of {@code variable1} must be greater than the name of
     * {@code variable2}.
     * 
     * @param instanciation A map representing the assignment of values to variables.
     * @return {@code true} if the constraint is satisfied, {@code false} otherwise.
     * @throws IllegalArgumentException if the instantiation is incomplete or invalid.
     */
    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> instanciation) throws IllegalArgumentException {
        Object v1 = instanciation.get(this.variable1);
        if (v1 == this.variable2.getName()) {
            if (this.variable1.getName() > this.variable2.getName()) {
                return true;
            }
            return false;
        }
        return true;
    }

    /**
     * Returns a string representation of this constraint, indicating the variables
     * involved.
     * 
     * @return A string describing the constraint between {@code variable1} and
     *         {@code variable2}.
     */
    @Override
    public String toString() {
        return "Croissant contraintd entre " + variable1.getName() + " et " + variable2.getName();
    }

    /**
     * Computes the hash code for this constraint based on its variables and class type.
     * 
     * @return An integer representing the hash code of this constraint.
     */
    @Override
    public int hashCode() {
        return this.variable1.hashCode() + this.variable2.hashCode() + CroissantConstraint.class.hashCode();
    }      
}
