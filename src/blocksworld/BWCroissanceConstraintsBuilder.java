package blocksworld;

import java.util.*;

import modelling.*;

/**
 * Builds growth (or ordering) constraints for a Blocks World model,
 * enforcing that blocks are arranged in ascending order across specified stacks.
 */
public class BWCroissanceConstraintsBuilder {
    private Set<Variable> variables;
    private Set<Constraint> constraints;
    private int nbBlocks;
    private int nbStacks;

    /**
     * Constructs a BWCroissanceConstraintsBuilder for a Blocks World model
     * with a specified number of blocks and stacks. Initializes the variables 
     * and sets up ordering constraints to enforce growth rules between blocks.
     *
     * @param nbBlocks The total number of blocks in the model.
     * @param nbStacks The total number of stacks (or piles) available in the model.
     * @throws IllegalArgumentException if the number of blocks is negative
     *         or the number of stacks is negative.
     */
    public BWCroissanceConstraintsBuilder(int nbBlocks, int nbStacks) {
        if (nbBlocks < 0 || nbStacks < 0) {
            throw new IllegalArgumentException("The number of blocks or stacks cannot be negative.");
        }
        this.nbBlocks = nbBlocks;
        this.nbStacks = nbStacks;
        createConstraints();
    }

    /**
     * Creates ordering constraints between all pairs of "block on" variables.
     * <p>
     * For each distinct pair of block variables, adds a {@link CroissantConstraint}
     * to the constraints set to ensure that blocks follow an ascending order 
     * across the stacks.
     */
    private void createConstraints() {
        BWVariablesBuilder bwvariables = new BWVariablesBuilder(nbBlocks, nbStacks);
        this.variables = bwvariables.getVariables();
        this.constraints = new HashSet<>();
        
        for (Variable i : this.variables) {
            if (Variable.isBlockOnVariable(i)) {
                for (Variable j : this.variables) {
                    if (Variable.isBlockOnVariable(j) && !i.equals(j)) {
                        this.constraints.add(new CroissantConstraint(i, j));
                    }
                }
            }
        }
    }

    /**
     * Returns the set of constraints created for this Blocks World model.
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
