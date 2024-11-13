package modelling;

import java.util.*;

/**
 * This class represents a constraint between the "on" variable of a block and
 * the "free" variable of a stack.
 * The constraint ensures that if a block is placed on a stack, the stack must
 * not be free.
 * 
 * The "free" variable is typically a boolean, indicating whether the stack is
 * free or occupied.
 * 
 */
public class FreeConstraint implements Constraint {
    private Variable variable1;
    // variable2 est a priorie une variable boolean
    // Elle represente la variable free d'une pile
    private Variable variable2;

    /**
     * Constructs a FreeConstraint between the specified variables.
     * 
     * @param variable1 The "on" variable of a block.
     * @param variable2 The "free" variable (typically a boolean) of a stack (pile).
     * @throws IllegalArgumentException if {@code variable1} and {@code variable2}
     *                                  are the same,
     *                                  or if {@code variable2} is not a valid pile
     *                                  variable.
     */
    public FreeConstraint(Variable variable1, Variable variable2) {
        if(variable1==null || variable2==null){
            throw new IllegalArgumentException("Variables can't not be null");
        }
        if (!Variable.isBlockOnVariable(variable1)){
            throw new IllegalArgumentException("The first argument in FreeConstraint must be block's On variable");
        }
        if (!BooleanVariable.isStackFreeVariable(variable2)) {
            throw new IllegalArgumentException("The second argument in FreeConstraint must be a stack's free  variable");
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
     * then {@code variable2} (the "free" status) must be {@code false} (not free).
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
        if (v1 == null || v2 == null)
            throw new IllegalArgumentException("Bad instanciation");

        if (v1.equals((int) this.variable2.getName())) {
            if (v2.equals(false)) {
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
     * @return A string describing the constraint between {@code variable1} and
     *         {@code variable2}.
     */
    @Override
    public String toString() {
        return "Contrainte de type Free entre " + variable1.getName() + " et " + variable2.getName();
    }

    @Override
    public int hashCode() {
        return this.variable1.hashCode() + this.variable2.hashCode() + FreeConstraint.class.hashCode();
    }

}
