package modelling;

import java.util.*;

/**
 * This class represents a constraint that enforces a regular difference between
 * two variables.
 * Specifically, it ensures that the absolute difference between the names of
 * the two variables
 * equals a specified value.
 * 
 * This constraint is typically applied to block positioning variables (e.g.,
 * "on" variables).
 * 
 * @param variable1         The first variable to be constrained.
 * @param variable2         The second variable to be constrained.
 * @param differentExpected The expected absolute difference between
 *                          {@code variable1} and {@code variable2}.
 * 
 * @author [Your Name]
 */
public class RegularyConstraint implements Constraint {
    private Variable variable1;
    private Variable variable2;
    private int differentExpected;

    /**
     * Constructs a {@code RegularyConstraint} that enforces a specific condition
     * between two
     * "on" variables associated with distinct blocks.
     * 
     * This constructor checks that the provided variables are valid "on" variables
     * for blocks,
     * ensuring they are non-null, distinct, and meet specific requirements to form
     * a valid constraint.
     * It also sets an expected difference condition for these variables, as
     * indicated by {@code differentExpected}.
     * 
     * @param variable1         The first "on" variable associated with a block,
     *                          required to be a
     *                          non-null {@code Variable} with a non-negative name.
     * @param variable2         The second "on" variable associated with another
     *                          block, also required
     *                          to be a non-null {@code Variable} with a
     *                          non-negative name.
     * @param differentExpected An integer specifying the expected difference
     *                          condition to be enforced
     *                          between the two variables.
     * 
     * @throws NullPointerException     if either {@code variable1} or
     *                                  {@code variable2} is {@code null}.
     * @throws IllegalArgumentException if either variable is not a valid block "on"
     *                                  variable
     *                                  (i.e., not an instance of {@code Variable}
     *                                  with a non-negative name),
     *                                  or if {@code variable1} and
     *                                  {@code variable2} refer to the same
     *                                  variable.
     */
    public RegularyConstraint(Variable variable1, Variable variable2, int differentExpected) {
        if (variable1 == null || variable2 == null) {
            throw new IllegalArgumentException("Variable in RegularyConstraint can't not be  null");
        }
        if (!Variable.isBlockOnVariable(variable1)) {
            throw new IllegalArgumentException("Variables in RegularyConstraint must be block's on variable");
        }
        if (!Variable.isBlockOnVariable(variable2)) {
            throw new IllegalArgumentException("Variables in RegularyConstraint must be block's on variable");
        }
        if (variable1.equals(variable2)) {
            throw new IllegalArgumentException("Variables in RegularyConstraint must be different");
        }

        this.variable2 = variable2;
        this.variable1 = variable1;
        this.differentExpected = differentExpected;

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
     * 1. If {@code variable1} is assigned to the name of {@code variable2}, then
     * the absolute difference
     * between the names of the two variables must equal {@code differentExpected}.
     * 2. If {@code variable1} is not assigned to the name of {@code variable2}, the
     * constraint is trivially satisfied.
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
        // L'un deux n'a pas de valeur dans instanciation : On renvoie une exception
        if (v1 == null) {
            throw new IllegalArgumentException("Bad instanciation");
        }
        if (v1 == this.variable2.getName()) {
            if (Math.abs(this.variable1.getName() - this.variable2.getName()) == this.differentExpected) {
                return true;
            }
            return false;
        }
        return true;
    }

    /**
     * Returns a string representation of this constraint, indicating the variables
     * involved and the expected difference.
     * 
     * @return A string describing the constraint between {@code variable1} and
     *         {@code variable2}, along with the expected difference.
     */
    @Override
    public String toString() {
        return "Contrainte de type Regulary entre " + variable1.getName() + " et " + variable2.getName()
                + " avec l'ecart : " + this.differentExpected;
    }

    @Override
    public int hashCode() {
        return this.variable1.hashCode() + this.variable2.hashCode()+ RegularyConstraint.class.hashCode();
    }


}
