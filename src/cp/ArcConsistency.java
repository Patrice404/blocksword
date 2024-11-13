package cp;

import java.util.*;

import modelling.*;

/**
 * Implements the Arc Consistency (AC) algorithm to enforce consistency on a constraint satisfaction problem (CSP).
 * <p>
 * This class supports both binary and unary constraints, verifying that each variable's domain contains only values 
 * that satisfy the constraints with respect to the domains of other variables. It offers methods for arc consistency (AC1) 
 * and node consistency.
 * </p>
 */
public class ArcConsistency {

    private Set<Constraint> constraints;

    /**
     * Constructs an {@code ArcConsistency} instance with a given set of constraints.
     * Constraints must be either unary or binary.
     * 
     * @param constraints The set of {@link Constraint} instances to be checked.
     * @throws IllegalArgumentException if a constraint is not unary or binary.
     */
    public ArcConsistency(Set<Constraint> constraints) {
        for (Constraint constraint : constraints) {
            if (constraint.getScope().size() != 2 && constraint.getScope().size() != 1) {
                throw new IllegalArgumentException("ArcConsistency constraints must be unary or binary");
            }
        }
        this.constraints = constraints;
    }

    /**
     * Enforces arc consistency between two variables by revising {@code domain1} of {@code variable1}.
     * <p>
     * This method removes values from {@code domain1} that do not have a corresponding 
     * "support" value in {@code domain2} (i.e., values in {@code domain1} that cannot satisfy
     * the constraints with any value in {@code domain2}).
     * </p>
     *
     * @param variable1 The first {@link Variable} in the constraint.
     * @param domain1 The domain of {@code variable1}.
     * @param variable2 The second {@link Variable} in the constraint.
     * @param domain2 The domain of {@code variable2}.
     * @return {@code true} if values were removed from {@code domain1}; {@code false} otherwise.
     */
    public boolean revise(Variable variable1, Set<Object> domain1, Variable variable2, Set<Object> domain2) {
        boolean del = false;

        Set<Variable> variables = new HashSet<>();
        variables.add(variable1);
        variables.add(variable2);

        Set<Object> domain1Copy = new HashSet<>(domain1);

        for (Object valeur1 : domain1Copy) {
            boolean viable = false;
            for (Object valeur2 : domain2) {
                boolean satisfiedAll = true;

                for (Constraint constraint : this.constraints) {
                    Set<Variable> scope = constraint.getScope();
                    if (scope.equals(variables)) {
                        Map<Variable, Object> instanciation = new HashMap<>();
                        instanciation.put(variable1, valeur1);
                        instanciation.put(variable2, valeur2);
                        if (!constraint.isSatisfiedBy(instanciation)) {
                            satisfiedAll = false;
                            break;
                        }
                    }
                }
                if (satisfiedAll) {
                    viable = true;
                    break;
                }
            }
            if (!viable) {
                domain1.remove(valeur1);
                del = true;
            }
        }
        return del;
    }

    /**
     * Applies the AC-1 algorithm to enforce arc consistency on all variables and domains in the CSP.
     * <p>
     * The method iteratively enforces consistency between pairs of variables until no more values can be removed. 
     * It also applies node consistency on all variables before beginning arc consistency checks.
     * </p>
     *
     * @param domains A map representing the domains of each variable in the CSP.
     * @return {@code true} if arc consistency was successfully enforced; {@code false} if any domain becomes empty.
     */
    public boolean ac1(Map<Variable, Set<Object>> domains) {
        if (!enforceNodeConsistency(domains)) {
            return false;
        }
        boolean change;
        do {
            change = false;
            for (Variable v1 : domains.keySet()) {
                for (Variable v2 : domains.keySet()) {
                    if (!v1.equals(v2)) {
                        if (this.revise(v1, domains.get(v1), v2, domains.get(v2))) {
                            change = true;
                        }
                    }
                }
            }
        } while (change);

        for (Variable variable : domains.keySet()) {
            if (domains.get(variable).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Enforces node consistency on all variables by ensuring that all values in each variable's domain 
     * satisfy unary constraints.
     * <p>
     * The method iterates through each variable and removes values from its domain that do not satisfy
     * unary constraints, if any are present.
     * </p>
     *
     * @param domains A map representing the domains of each variable in the CSP.
     * @return {@code true} if node consistency was successfully enforced; {@code false} if any domain becomes empty.
     */
    public boolean enforceNodeConsistency(Map<Variable, Set<Object>> domains) {
        for (Map.Entry<Variable, Set<Object>> entry : domains.entrySet()) {
            Variable variable = entry.getKey();
            Set<Object> domain = entry.getValue();
            Set<Object> domainCopy = new HashSet<>(domain);
            for (Object valeur : domain) {
                Map<Variable, Object> instanciation = new HashMap<>();
                instanciation.put(variable, valeur);
                for (Constraint constraint : this.constraints) {
                    // Check if the constraint is unary
                    if (constraint.getScope().size() == 1) {
                        Set<Variable> scope = constraint.getScope();
                        Variable v = scope.iterator().next();
                        // Check if the constraint is relevant to the current variable
                        if (v.equals(variable)) {
                            if (!constraint.isSatisfiedBy(instanciation)) {
                                domainCopy.remove(valeur);
                            }
                        }
                    }
                }
            }
            domains.put(variable, domainCopy);
        }

        for (Map.Entry<Variable, Set<Object>> entry : domains.entrySet()) {
            if (entry.getValue().isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
