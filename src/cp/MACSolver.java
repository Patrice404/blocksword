package cp;

import java.util.*;
import modelling.Constraint;
import modelling.Variable;

/**
 * Implements a constraint satisfaction problem (CSP) solver using the Maintaining Arc Consistency (MAC) algorithm.
 * <p>
 * The {@code MACSolver} enforces arc consistency at each step of the search, pruning variable domains
 * based on constraints, to improve efficiency and reduce backtracking in finding a solution.
 * </p>
 */
public class MACSolver extends AbstractSolver {

    /**
     * Constructs a {@code MACSolver} with the specified variables and constraints.
     *
     * @param variables   The set of variables in the CSP.
     * @param constraints The set of constraints governing the relationships between variables.
     */
    public MACSolver(Set<Variable> variables, Set<Constraint> constraints) {
        super(variables, constraints);
    }

    /**
     * Solves the CSP using the MAC algorithm.
     * <p>
     * Initializes the domains and partial instantiation, then calls the recursive {@code MAC} function to search
     * for a solution by assigning values to variables while maintaining arc consistency.
     * </p>
     *
     * @return A map representing a complete and consistent assignment of values to variables,
     *         or {@code null} if no solution exists.
     */
    @Override
    public Map<Variable, Object> solve() {
        Map<Variable, Object> instanciationPartielle = new HashMap<>();
        Map<Variable, Set<Object>> domains = new HashMap<>();
        LinkedList<Variable> variables = new LinkedList<>();
        
        for (Variable variable : this.variables) {
            domains.put(variable, new HashSet(variable.getDomain()));
            variables.push(variable);
        }
        
        return this.MAC(instanciationPartielle, variables, domains);
    }

    /**
     * Recursively attempts to solve the CSP by assigning values to variables while maintaining arc consistency.
     * <p>
     * This function enforces arc consistency on the current set of domains. If arc consistency holds,
     * it assigns values to variables from the pruned domains. If a complete solution is found, it returns
     * the solution; otherwise, it backtracks and continues with the next possible assignment.
     * </p>
     *
     * @param instanciationPartielle The current partial assignment of variables to values.
     * @param uninstantiatedVariable The queue of variables yet to be instantiated.
     * @param domains                A map linking each variable to its current domain of possible values.
     * @return A complete and consistent assignment of values to variables, or {@code null} if no solution exists.
     */
    private Map<Variable, Object> MAC(Map<Variable, Object> instanciationPartielle, 
                                      LinkedList<Variable> uninstantiatedVariable, 
                                      Map<Variable, Set<Object>> domains) {
        if (uninstantiatedVariable.isEmpty()) {
            return instanciationPartielle;
        }
        
        ArcConsistency arcConsistency = new ArcConsistency(this.constraints);
        if (!arcConsistency.ac1(domains)) {
            return null;
        }
        
        Variable variable = uninstantiatedVariable.pollFirst();
        
        for (Object valeur : domains.get(variable)) {
            Map<Variable, Object> instanciation = new HashMap<>(instanciationPartielle);
            instanciation.put(variable, valeur);
            
            if (this.isConsistent(instanciation)) {
                Map<Variable, Object> result = this.MAC(instanciation, uninstantiatedVariable, domains);
                if (result != null) {
                    return result;
                }
            }
        }
        
        uninstantiatedVariable.addLast(variable);
        return null;
    }
}
