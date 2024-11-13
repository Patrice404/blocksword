package cp;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import modelling.Constraint;
import modelling.Variable;

/**
 * Implements a Backtracking algorithm to solve a Constraint Satisfaction Problem (CSP).
 * <p>
 * The {@code BacktrackSolver} class uses recursive backtracking to assign values to variables while
 * satisfying the given constraints. It operates by selecting unassigned variables and attempting to assign them
 * values from their domain, backtracking whenever a constraint violation is detected.
 * </p>
 */
public class BacktrackSolver extends AbstractSolver {

    /**
     * Constructs a {@code BacktrackSolver} with a set of variables and constraints.
     *
     * @param variables The set of variables to be assigned values.
     * @param constraints The set of constraints that must be satisfied.
     */
    public BacktrackSolver(Set<Variable> variables, Set<Constraint> constraints) {
        super(variables, constraints);
    }

    /**
     * Initiates the backtracking algorithm to solve the CSP.
     * <p>
     * This method initializes an empty partial instantiation and an ordered queue of unassigned variables, then 
     * calls the recursive backtracking function {@link #BT(Map, Queue)}.
     * </p>
     *
     * @return A map representing a solution where each {@link Variable} is assigned a value from its domain 
     * that satisfies all constraints, or {@code null} if no solution exists.
     */
    @Override
    public Map<Variable, Object> solve() {
        Map<Variable, Object> instanciationPartielle = new HashMap<>();
        Queue<Variable> variablesNonInstancie = new LinkedList<>();
        variablesNonInstancie.addAll(this.variables);
        return BT(instanciationPartielle, variablesNonInstancie);
    }

    /**
     * Recursive backtracking function that attempts to find a valid assignment for the CSP.
     * <p>
     * This method selects the next unassigned variable, tries assigning each possible value, and checks
     * if the partial assignment is consistent. If a consistent assignment is found, it recursively attempts to
     * assign values to the remaining variables.
     * </p>
     *
     * @param instanciationPartielle A partial instantiation mapping variables to assigned values.
     * @param variablesNonInstancie A queue of unassigned variables to be processed.
     * @return A complete valid assignment that satisfies all constraints, or {@code null} if no solution exists.
     */
    public Map<Variable, Object> BT(Map<Variable, Object> instanciationPartielle, Queue<Variable> variablesNonInstancie) {
        if (variablesNonInstancie.isEmpty()) {
            return instanciationPartielle; // Solution found
        }
        Variable variable = variablesNonInstancie.poll();
        for (Object valeur : variable.getDomain()) {
            Map<Variable, Object> newInstanciation = new HashMap<>(instanciationPartielle);
            newInstanciation.put(variable, valeur);
            if (this.isConsistent(newInstanciation)) { // Check if consistent with constraints
                Map<Variable, Object> r = BT(newInstanciation, variablesNonInstancie);
                if (r != null) {
                    return r; // Return solution if found
                }
            }
        }
        variablesNonInstancie.add(variable); // Backtrack
        return null;
    }
}
