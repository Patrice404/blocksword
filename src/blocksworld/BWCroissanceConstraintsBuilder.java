package blocksworld;

import java.util.*;
import modelling.*;

/**
 * The {@code BWCroissanceConstraintsBuilder} class is responsible for creating
 * constraints in the Blocks World model that enforce a rule of growth or
 * ordering
 * among blocks. This ensures that blocks can only be placed on blocks with
 * lower
 * indices, enforcing a structured stacking order.
 */
public class BWCroissanceConstraintsBuilder {
    private Set<Variable> variables;
    private Set<Constraint> constraints;
    private int nbBlocks;
    private int nbStacks;

    /**
     * Constructs a {@code BWCroissanceConstraintsBuilder} instance with the
     * specified
     * number of blocks and stacks. It initializes the variables and generates
     * constraints
     * to enforce a rule that higher-indexed blocks cannot be placed on
     * lower-indexed blocks.
     *
     * @param nbBlocks the number of blocks in the model.
     * @param nbStacks the number of stacks in the model.
     * @throws IllegalArgumentException if the number of blocks or stacks is
     *                                  negative.
     */
    public BWCroissanceConstraintsBuilder(int nbBlocks, int nbStacks) {
        if (nbBlocks < 0 || nbStacks < 0) {
            throw new IllegalArgumentException("The number of blocks or stacks can't be negative.");
        }
        this.nbBlocks = nbBlocks;
        this.nbStacks = nbStacks;
        createConstraints();
    }

    /**
     * Creates constraints for the Blocks World model that enforce a "growth" rule.
     * <p>
     * The constraints ensure that:
     * <ul>
     * <li>Each block can only be placed on blocks with lower indices.</li>
     * <li>The domain of variables representing block placement is adjusted to
     * remove
     * invalid configurations.</li>
     * </ul>
     * This enforces a structured stacking order among the blocks.
     */
    private void createConstraints() {
        BWVariablesBuilder bwvariables = new BWVariablesBuilder(nbBlocks, nbStacks);
        this.variables = bwvariables.getVariables();
        this.constraints = new HashSet<>();

        for (Variable i : this.variables) {
            if (Variable.isBlockOnVariable(i)) {
                Set<Object> s = new HashSet<>(i.getDomain());
                // Forcer les block a se poser que sur des block d'id plus petit

                for (int k = nbBlocks; k >= (int) i.getName(); k--) {
                    s.remove(k);
                }
                this.constraints.add(new Implication(i, i.getDomain(), i, s));
            }
        }
    }

    /**
     * Returns the set of constraints created for this Blocks World model.
     * <p>
     * These constraints enforce the rule that blocks can only be placed on blocks
     * with lower indices, ensuring a structured growth in the stack.
     *
     * @return A set containing all constraints enforcing the block ordering rules.
     */
    public Set<Constraint> getConstraints() {
        return constraints;
    }

    /**
     * Gets the number of blocks used in the model.
     *
     * @return The number of blocks.
     */
    public int getNbBlocks() {
        return nbBlocks;
    }

    /**
     * Gets the number of stacks available in the model.
     *
     * @return The number of stacks.
     */
    public int getNbStacks() {
        return nbStacks;
    }
}
