package cp;

import java.util.*;

import modelling.Constraint;
import modelling.Variable;

/**
 * Implements a constraint satisfaction problem (CSP) solver that combines a heuristic-based
 * approach with Maintaining Arc Consistency (MAC) during the search process.
 * <p>
 * The {@code HeuristicMACSolver} class uses specified heuristics to determine the order of
 * variable selection and value assignment, aiming to improve search efficiency by reducing
 * the search space and ensuring arc consistency at each step.
 * </p>
 */
public class HeuristicMACSolver extends AbstractSolver {
    private VariableHeuristic variableHeuristic;
    private ValueHeuristic valueHeuristic;

    /**
     * Constructs a {@code HeuristicMACSolver} with the specified variables, constraints, and heuristics.
     *
     * @param variables         The set of variables in the CSP.
     * @param constraints       The set of constraints governing the relationships between variables.
     * @param variableHeuristic The heuristic used to prioritize variable selection.
     * @param valueHeuristic    The heuristic used to prioritize value ordering within domains.
     */
    public HeuristicMACSolver(Set<Variable> variables, Set<Constraint> constraints, 
                              VariableHeuristic variableHeuristic, ValueHeuristic valueHeuristic) {
        super(variables, constraints); 
        this.variableHeuristic = variableHeuristic;
        this.valueHeuristic = valueHeuristic;
    }

    /**
     * Solves the CSP using a backtracking algorithm with Maintaining Arc Consistency (MAC).
     * <p>
     * This method initializes the partial instantiation and domains for all variables,
     * then recursively calls the MAC function to search for a complete and consistent solution.
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
     * Recursively searches for a complete and consistent solution using the MAC algorithm.
     * <p>
     * The MAC algorithm enforces arc consistency using the {@code ArcConsistency} class.
     * It selects variables and values based on the specified heuristics, attempting to
     * build a consistent assignment. If arc consistency fails, the method backtracks.
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

        Variable variable = this.variableHeuristic.best(new HashSet<>(uninstantiatedVariable), domains);
        uninstantiatedVariable.remove(variable);

        for (Object valeur : this.valueHeuristic.ordering(variable, domains.get(variable))) {
            Map<Variable, Object> instanciation = new HashMap<>(instanciationPartielle);
            instanciation.put(variable, valeur);
            if (this.isConsistent(instanciation)) {
                Map<Variable, Object> result = this.MAC(instanciation, uninstantiatedVariable, domains);
                if (result != null) {
                    return result;
                }
            }
        }
        uninstantiatedVariable.add(variable);
        return null;
    }
}
