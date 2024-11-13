package blocksworld;

import java.util.*;
import modelling.BooleanVariable;
import modelling.Variable;
import planning.Action;
import planning.BasicAction;

/**
 * The {@code BWActionsBuilder} class is responsible for creating and storing a
 * set of basic
 * actions that can be performed in the Blocks World model, given a specified
 * number of blocks
 * and stacks. These actions define possible block movements between stacks and
 * other blocks.
 * 
 * The class generates all feasible actions by setting preconditions and effects
 * based on the
 * current state of the blocks and stacks in the model.
 * 
 */
public class BWActionsBuilder {
    /** The set of generated actions for the Blocks World model. */
    private Set<Action> actions;

    /**
     * Constructs a {@code BWActionsBuilder} that initializes a set of possible actions
     * for a Blocks World configuration with a specified number of blocks and stacks.
     * 
     * @param nbBlocks The number of blocks in the model.
     * @param nbStacks The number of stacks available in the model.
     * @throws IllegalArgumentException if {@code nbBlocks} or {@code nbStacks} is negative.
     */
    public BWActionsBuilder(int nbBlocks, int nbStacks) {
        if (nbBlocks < 0 || nbStacks < 0) {
            throw new IllegalArgumentException("The block's or stack's number can't not be negative.");
        }
        createBasicActions(nbBlocks, nbStacks);
    }

    /**
     * Creates all possible actions for moving blocks within the model based on the
     * specified number of blocks and stacks. This method builds actions such as moving
     * a block onto another block, onto a stack, or from a stack to another position.
     * 
     * @param nbBlocks The number of blocks in the model.
     * @param nbStacks The number of stacks in the model.
     */
    private void createBasicActions(int nbBlocks, int nbStacks) {
        this.actions = new HashSet<>();
        BWVariablesBuilder bwVariablesBuilder = new BWVariablesBuilder(nbBlocks, nbStacks);
        Set<Variable> variables = bwVariablesBuilder.getVariables();
        for (Variable on : variables) {
            if (Variable.isBlockOnVariable(on)) {
                for (Variable under : variables) {
                    if (Variable.isBlockOnVariable(under) && !under.equals(on)) {
                        for (Variable destination : variables) {
                            if (Variable.isBlockOnVariable(destination)
                                    && (!destination.equals(on)) && (!destination.equals(under))) {
                                // bloc posé sur bloc vers bloc
                                createBasicActionBToB(on, under, destination);

                            } else if (destination.getName() < 0) {
                                // bloc posé sur bloc vers pile
                                createBasicActionBToP(on, under, destination);
                            }
                        }

                    } else if (BooleanVariable.isStackFreeVariable(under)) {
                        for (Variable destination : variables) {
                            if (Variable.isBlockOnVariable(destination)
                                    && !destination.equals(on)) {
                                // bloc posé sur pile vers bloc
                                createBasicActionPToB(on, under, destination);

                            } else if (BooleanVariable.isStackFreeVariable(destination)
                                    && !destination.equals(under)) {
                                // bloc posé sur pile vers pile
                                createBasicActionPToP(on, under, destination);
                            }
                        }
                    }
                }
            }
        }
    }

     /**
     * Creates an action to move a block from another block to a destination block.
     * 
     * @param on         The block being moved.
     * @param under      The block currently beneath {@code on}.
     * @param destination The block where {@code on} will be placed.
     */
    private void createBasicActionBToB(Variable on, Variable under, Variable destination) {
        Map<Variable, Object> precondition = new HashMap<>();

        BooleanVariable onFixed = new BooleanVariable(on.getName());
        BooleanVariable destinationFixed = new BooleanVariable(destination.getName());

        precondition.put(on, under.getName());
        precondition.put(onFixed, false);
        precondition.put(destinationFixed, false);

        Map<Variable, Object> effects = new HashMap<>();
        BooleanVariable underFixed = new BooleanVariable(under.getName());

        effects.put(on, destination.getName());
        effects.put(underFixed, false);
        effects.put(destinationFixed, true);

        this.actions.add(new BasicAction(precondition, effects));
    }

     /**
     * Creates an action to move a block from another block to a destination stack.
     * 
     * @param on          The block being moved.
     * @param under       The block currently beneath {@code on}.
     * @param destination The stack where {@code on} will be placed.
     */

    private void createBasicActionBToP(Variable on, Variable under, Variable destination) {
        Map<Variable, Object> precondition = new HashMap<>();

        BooleanVariable onFixed = new BooleanVariable(on.getName());
        BooleanVariable destinationFree = new BooleanVariable(destination.getName());

        precondition.put(on, under.getName());
        precondition.put(onFixed, false);
        precondition.put(destinationFree, true);

        Map<Variable, Object> effects = new HashMap<>();
        BooleanVariable underFixed = new BooleanVariable(under.getName());

        effects.put(on, destination.getName());
        effects.put(underFixed, false);
        effects.put(destinationFree, false);

        this.actions.add(new BasicAction(precondition, effects));
    }

      /**
     * Creates an action to move a block from a stack to a destination block.
     * 
     * @param on          The block being moved.
     * @param under       The stack currently beneath {@code on}.
     * @param destination The block where {@code on} will be placed.
     */

    private void createBasicActionPToB(Variable on, Variable under, Variable destination) {
        Map<Variable, Object> precondition = new HashMap<>();

        BooleanVariable onFixed = new BooleanVariable(on.getName());
        BooleanVariable destinationFixed = new BooleanVariable(destination.getName());

        precondition.put(on, under.getName());
        precondition.put(onFixed, false);
        precondition.put(destinationFixed, false);

        Map<Variable, Object> effects = new HashMap<>();
        BooleanVariable underFree = new BooleanVariable(under.getName());

        effects.put(on, destination.getName());
        effects.put(underFree, true);
        effects.put(destinationFixed, true);

        this.actions.add(new BasicAction(precondition, effects));
    }

     /**
     * Creates an action to move a block from a stack to another stack.
     * 
     * @param on          The block being moved.
     * @param under       The stack currently beneath {@code on}.
     * @param destination The stack where {@code on} will be placed.
     */

    private void createBasicActionPToP(Variable on, Variable under, Variable destination) {
        Map<Variable, Object> precondition = new HashMap<>();

        BooleanVariable onFixed = new BooleanVariable(on.getName());
        BooleanVariable destinationFree = new BooleanVariable(destination.getName());

        precondition.put(on, under.getName());
        precondition.put(onFixed, false);
        precondition.put(destinationFree, true);

        Map<Variable, Object> effects = new HashMap<>();
        BooleanVariable underFree = new BooleanVariable(under.getName());

        effects.put(on, destination.getName());
        effects.put(underFree, true);
        effects.put(destinationFree, false);

        this.actions.add(new BasicAction(precondition, effects));
    }

     /**
     * Returns the set of all actions created for the Blocks World model.
     * 
     * @return A set containing all possible actions for the Blocks World configuration.
     */
    public Set<Action> getActions() {
        return actions;
    }
}
