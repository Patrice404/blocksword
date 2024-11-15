package blocksworld;

import java.util.*;

import modelling.*;

/**
 * Builds "regularity" constraints for a Blocks World model, enforcing a specified
 * condition on the relationships between all pairs of block variables.
 * <p>
 * This class initializes {@link Variable} objects for each variable in the model
 * and applies {@link RegularyConstraint} constraints to enforce regularity conditions
 * based on a specified expected difference between blocks in the model.
 * </p>
 */
public class BWRegularyConstraintsBuilder {
    private Set<Variable> variables;
    private Set<Constraint> constraints;
    private int nbBlocks;
    private int nbStacks;
    private int differentExpected;

    /**
     * Constructs a BWRegularyConstraintsBuilder for a Blocks World model with a
     * specified number of blocks and stacks. Initializes the regularity constraints
     * by setting the expected difference between blocks.
     *
     * @param nbBlocks          The total number of blocks in the model.
     * @param nbStacks          The total number of stacks (or piles) in the model.
     
     * @throws IllegalArgumentException if the number of blocks is negative or
     *         the number of stacks is zero or negative.
     */
    public BWRegularyConstraintsBuilder(int nbBlocks, int nbStacks) {
        if (nbBlocks < 0 || nbStacks < 0 || differentExpected < 0 ) {
            throw new IllegalArgumentException("The number of blocks or stacks cannot be negative.");
        }
        this.nbBlocks = nbBlocks;
        this.nbStacks = nbStacks;

        // Initialize the variables and constraints
        BWVariablesBuilder bwvariables = new BWVariablesBuilder(nbBlocks, nbStacks);
        this.variables = bwvariables.getVariables();
        this.constraints = new HashSet<>();
        createConstraints();
        //System.err.println(constraints);
    }

    /**
     * Creates regularity constraints between all pairs of "block on" variables,
     * ensuring that each pair of distinct block variables has a {@link RegularyConstraint}
     * added to enforce the specified expected difference between the blocks.
     */
    private void createConstraints() {
        for (Variable i : this.variables) {
            if (Variable.isBlockOnVariable(i)) {
                for (Variable j : this.variables) {
                    if (Variable.isBlockOnVariable(j)) {
                        if (i.getName()!=j.getName()) {
                            Set<Object> s1 = new HashSet<>();
                            s1.add(j.getName());
                            Set<Object> s2 = new HashSet<>(j.getDomain());
                            s2.remove(i.getName());
                            for(int k=0; k<this.nbBlocks; k++){
                                if(Math.abs(k-j.getName())!=Math.abs(i.getName()-j.getName())){
                                    s2.remove(k);
                                }
                            }

                            this.constraints.add(new Implication(i, s1, j, s2));
                        }
                    }
                }
            }
        }
    }

    /**
     * Returns the set of regularity constraints created for the Blocks World model.
     *
     * @return A set containing all the regularity constraints that enforce the 
     *         expected difference rule between block variables.
     */
    public Set<Constraint> getConstraints() {
        return constraints;
    }

    /**
     * Returns the total number of blocks in the model.
     *
     * @return The number of blocks.
     */
    public int getNbBlocks() {
        return nbBlocks;
    }

    /**
     * Returns the total number of stacks in the model.
     *
     * @return The number of stacks.
     */
    public int getNbStacks() {
        return nbStacks;
    }

    /**
     * Returns the expected difference value that the regularity constraints enforce
     * between block variables.
     *
     * @return The expected difference for the regularity constraint.
     */
    public int getDifferentExpected() {
        return differentExpected;
    }
}
