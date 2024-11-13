package cp;

import java.util.Map;
import java.util.Set;

import modelling.Constraint;
import modelling.Variable;

/**
 * An abstract class representing a solver for a constraint satisfaction problem (CSP).
 * <p>
 * This class provides a basic structure and utility methods for checking consistency 
 * of partial assignments in the CSP, and storing sets of variables and constraints.
 * </p>
 * Subclasses are expected to implement specific solving techniques.
 * @see Solver
 */
public abstract class AbstractSolver implements Solver {

    /** The set of variables involved in the CSP. */
    protected Set<Variable> variables;

    /** The set of constraints that define the CSP. */
    protected Set<Constraint> constraints;

    /**
     * Constructs an {@code AbstractSolver} with a specified set of variables and constraints.
     *
     * @param variables The set of {@link Variable} instances representing the CSP's variables.
     * @param constraints The set of {@link Constraint} instances representing the CSP's constraints.
     */
    public AbstractSolver(Set<Variable> variables, Set<Constraint> constraints) {
        this.variables = variables;
        this.constraints = constraints;
    }

    /**
     * Checks if a given partial assignment is consistent with all applicable constraints.
     * <p>
     * The method iterates through each constraint and verifies if the assignment satisfies 
     * all constraints whose variables are fully assigned in the given partial assignment.
     * </p>
     *
     * @param instanciationPartielle A map representing a partial assignment of variables to values.
     * @return {@code true} if the partial assignment satisfies all applicable constraints; 
     *         {@code false} otherwise.
     */
    public boolean isConsistent(Map<Variable, Object> instanciationPartielle) {
        for (Constraint constraint : this.constraints) {
            if (instanciationPartielle.keySet().containsAll(constraint.getScope())) {
                if (!constraint.isSatisfiedBy(instanciationPartielle)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Gets the set of variables associated with this solver.
     *
     * @return The set of {@link Variable} instances representing the CSP's variables.
     */
    public Set<Variable> getVariables() {
        return variables;
    }

    /**
     * Sets the set of variables for this solver.
     *
     * @param variables The set of {@link Variable} instances to be assigned.
     */
    public void setVariables(Set<Variable> variables) {
        this.variables = variables;
    }

    /**
     * Gets the set of constraints associated with this solver.
     *
     * @return The set of {@link Constraint} instances representing the CSP's constraints.
     */
    public Set<Constraint> getConstraints() {
        return constraints;
    }

    /**
     * Sets the set of constraints for this solver.
     *
     * @param constraints The set of {@link Constraint} instances to be assigned.
     */
    public void setConstraints(Set<Constraint> constraints) {
        this.constraints = constraints;
    }
}
