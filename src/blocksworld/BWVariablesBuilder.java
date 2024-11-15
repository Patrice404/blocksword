package blocksworld;

import java.util.*;
import modelling.*;

/**
 * The BWVariablesBuilder class is responsible for creating and managing the
 * variables used in the Blocks World problem. It constructs variables for each
 * block
 * and stack based on the specified number of blocks and stacks.
 * <p>
 * Each block has two associated variables: an "on" variable representing its
 * position
 * (either on another block or a stack) and a "fixed" boolean variable
 * indicating if
 * it is fixed in place. Each stack is represented by a "free" boolean variable.
 * </p>
 */
public class BWVariablesBuilder {
    private int nbBlocks;
    private int nbStacks;
    private Set<Variable> variables;

    /**
     * Constructs a new instance of BWVariablesBuilder with the specified
     * number of blocks and stacks. Initializes the set of variables accordingly.
     *
     * @param nbBlocks The number of blocks to be created.
     * @param nbStacks The number of stacks to be created.
     * @throws IllegalArgumentException if the number of blocks is negative or
     *                                  the number of stacks is zero or negative.
     */
    public BWVariablesBuilder(int nbBlocks, int nbStacks) {
        if (nbBlocks < 0 || nbStacks < 0) {
            throw new IllegalArgumentException("The number of blocks or stacks cannot be negative.");
        }
        this.nbBlocks = nbBlocks;
        this.nbStacks = nbStacks;
        this.createVariables();
    }

    /**
     * Creates variables for the blocks and stacks in the model.
     * <p>
     * - Each stack has a single "free" boolean variable indicating if it is
     * available.
     * - Each block has two variables: an "on" variable representing its position
     * (either
     * on another block or on a stack) and a "fixed" boolean variable indicating if
     * it is in a fixed position.
     * </p>
     */
    private void createVariables() {
        this.variables = new HashSet<>();

        // Create "free" variables for each stack
        for (int i = 1; i <= nbStacks; i++) {
            BooleanVariable freePile = new BooleanVariable(-i);
            this.variables.add(freePile);
        }

        // Create "on" and "fixed" variables for each block
        for (int i = 0; i < nbBlocks; i++) {
            Set<Object> domain = calculDomain(i, nbBlocks, nbStacks);
            Variable on = new Variable(i, domain);
            this.variables.add(on);

            BooleanVariable fixed = new BooleanVariable(i);
            this.variables.add(fixed);
        }
    }

    /**
     * Calculates the domain of possible positions for a block's "on" variable.
     * The domain includes:
     * - Negative IDs for each stack (indicating the block can be placed on a
     * stack).
     * - The IDs of other blocks (indicating the block can be placed on another
     * block).
     *
     * @param id       The ID of the block for which to calculate the domain.
     * @param nbBlocks The total number of blocks in the model.
     * @param nbStacks The total number of stacks in the model.
     * @return A set containing the possible values for the block's "on" variable.
     */
    public static Set<Object> calculDomain(int id, int nbBlocks, int nbStacks) {
        Set<Object> domain = new HashSet<>();
        for (int i = 1; i <= nbStacks; i++) {
            domain.add(-i); 
        }
        for (int i = 0; i < nbBlocks; i++) {
            if (i != id) {
                domain.add(i); 
            }
        }
        return domain;
    }

    /**
     * Returns the set of variables created by this builder.
     *
     * @return A set containing all variables created for the model.
     */
    public Set<Variable> getVariables() {
        return this.variables;
    }

    /**
     * Gets the total number of blocks in the model.
     *
     * @return The number of blocks.
     */
    public int getNbBlocks() {
        return nbBlocks;
    }

    /**
     * Gets the total number of stacks in the model.
     *
     * @return The number of stacks.
     */
    public int getNbStacks() {
        return nbStacks;
    }
}
