package planning;

import java.util.*;

import modelling.Variable;

/**
 * The BasicAction class represents a simple action in a planning domain, with
 * defined
 * preconditions and effects. It extends AbstractAction, implementing the
 * methods
 * to check applicability and compute the successor state after the action is
 * applied.
 */
public class BasicAction extends AbstractAction {

    /**
     * Constructs a BasicAction with specified preconditions and effects.
     *
     * @param preconditions a map of variables to their required values that define
     *                      the conditions under which this action can be applied.
     * @param effects       a map of variables to their values after this action is
     *                      applied,
     *                      representing the result of the action.
     */
    public BasicAction(Map<Variable, Object> preconditions, Map<Variable, Object> effects) {
        super(preconditions, effects);
    }

    /**
     * Determines if this action is applicable in a given state represented by
     * {@code instanciation}.
     * The action is applicable if all variables in the preconditions match those in
     * the given instantiation.
     *
     * @param instanciation a map representing the current state, where each
     *                      variable maps to its value.
     * @return {@code true} if the action's preconditions are met in the provided
     *         state; {@code false} otherwise.
     */
    @Override
    public boolean isApplicable(Map<Variable, Object> instanciation) {
        for (Map.Entry<Variable, Object> entry : this.preconditions.entrySet()) {
            Variable variable = entry.getKey();
            Object value = entry.getValue();
            if (instanciation.get(variable) == null) {
                return false;
            }
            if (!instanciation.get(variable).equals(value)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Computes the successor state resulting from applying this action to the
     * current state.
     *
     * @param instanciation a map representing the current state, where each
     *                      variable maps to its value.
     * @return a new map representing the state after applying this action, with
     *         effects applied
     *         to the original state.
     */
    @Override
    public Map<Variable, Object> successor(Map<Variable, Object> instanciation) {
        Map<Variable, Object> next = new HashMap<>(instanciation);
        for (Map.Entry<Variable, Object> entry : this.effects.entrySet()) {
            Variable variable = entry.getKey();
            Object value = entry.getValue();
            next.put(variable, value);
        }
        return next;
    }

    /**
     * Provides a string representation of this action, including its preconditions
     * and effects.
     *
     * @return a string representing the preconditions and effects of this action.
     */
    @Override
    public String toString() {
        return "Préconditions : " + this.preconditions.toString() + "\n Effets : " + this.effects.toString() + "\n";
    }

    /**
     * Returns the cost of performing this action. In this implementation, all
     * actions
     * have a fixed cost of 1.
     *
     * @return the cost of this action, which is always 1.
     */
    @Override
    public int getCost() {
        return 1;
    }

}
