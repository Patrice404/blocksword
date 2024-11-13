package modelling;

import java.util.*;

/**
 * This class specifies a constraint between the "on" variable of one block and
 * the
 * "fixed" variable of another block. The constraint ensures that either the
 * blocks
 * are not positioned on the same spot, or if they are, the second block must be
 * "fixed"
 * in place.
 * 
 * The "fixed" variable is typically a boolean, indicating whether the second
 * block is
 * fixed in position.
 * 
 */
public class FixedConstraint implements Constraint {

    private Variable variable1;
    private Variable variable2;

    /**
     * Constructs a FixedConstraint between the given variables.
     * 
     * @param variable1 The "on" variable of one block.
     * @param variable2 The "fixed" variable of another block.
     * @throws IllegalArgumentException if {@code variable1} and {@code variable2}
     *                                  are the same,
     *                                  or if {@code variable2} is not valid (e.g.,
     *                                  does not represent a block).
     */
    public FixedConstraint(Variable variable1, Variable variable2) {
        if (variable1 == null || variable2 == null) {
            throw new IllegalArgumentException("Variables can't not be null");
        }
        if (!Variable.isBlockOnVariable(variable1)) {
            throw new IllegalArgumentException("The first argument in FixedConstraint must be block's On variable");
        }
        if (!BooleanVariable.isBlockFixedVariable(variable2)) {
            throw new IllegalArgumentException(
                    "The second argument in FixedConstraint must be a block's fixed  variable");
        }
        if (variable1.getName().equals(variable2.getName())) {
            throw new IllegalArgumentException("Variables in Fixed Constraint must have a different name");
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
     * 1. The value of {@code variable1} is not equal to the name of
     * {@code variable2}.
     * 2. If the value of {@code variable1} equals the name of {@code variable2},
     * then the value of {@code variable2} must be true (the block must be fixed).
     * 
     * @param instanciation A map representing the assignment of values to
     *                      variables.
     * @return {@code true} if the constraint is satisfied, {@code false} otherwise.
     * @throws IllegalArgumentException if the instantiation is incomplete or
     *                                  invalid.
     */
    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> instanciation) throws IllegalArgumentException {
        Object v1 = instanciation.get(this.variable1);
        Object v2 = instanciation.get(this.variable2);
        // L'un deux n'a pas de valeur dans instanciation : On renvoie une exception
        if (v1 == null || v2 == null) {
            throw new IllegalArgumentException("Bad instanciation");
        }

        if (v1.equals(this.variable2.getName())) {
            if (v2.equals(true)) {
                return true;
            }
            return false;
        }
        // La valeur attribuée a block1 n'est pas l'id de block2
        return true;
    }

    /**
     * Returns a string representation of this constraint, indicating the variables
     * involved.
     * 
     * @return A string describing the constraint between the two variables.
     */
    @Override
    public String toString() {
        return "Contrainte de type Fixed entre " + variable1.getName() + " et " + variable2.getName();
    }

    @Override
    public int hashCode() {
        return this.variable1.hashCode() + this.variable2.hashCode() + FixedConstraint.class.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        FixedConstraint c = (FixedConstraint) obj;
        return this.getScope().equals(c.getScope()) && obj.getClass().equals(FixedConstraint.class);
    }

}
