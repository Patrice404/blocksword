package planning;

import java.util.*;

import modelling.Variable;

/**
 * The AbstractAction class provides a base implementation for actions in a
 * planning domain,
 * including preconditions and effects. It partially implements the Action
 * interface,
 * managing the core data and providing methods for retrieving and setting
 * preconditions
 * and effects.
 */
public abstract class AbstractAction implements Action {

    protected Map<Variable, Object> preconditions;
    protected Map<Variable, Object> effects;

    /**
     * Constructs an AbstractAction with specified preconditions and effects.
     *
     * @param preconditions a map of variables to their required values that define
     *                      the conditions under which the action is applicable.
     * @param effects       a map of variables to their new values that represent
     *                      the
     *                      outcome of the action when applied.
     */

    public AbstractAction(Map<Variable, Object> preconditions, Map<Variable, Object> effects) {
        this.preconditions = preconditions;
        this.effects = effects;
    }

    /**
     * Returns the preconditions of this action.
     *
     * @return a map representing the preconditions of this action, where each
     *         variable
     *         is mapped to its required value.
     */

    public Map<Variable, Object> getPreconditions() {
        return preconditions;
    }

    /**
     * Returns the effects of this action.
     *
     * @return a map representing the effects of this action, where each variable
     *         is mapped to the value it will take after the action is applied.
     */
    public Map<Variable, Object> getEffects() {
        return effects;
    }

}
