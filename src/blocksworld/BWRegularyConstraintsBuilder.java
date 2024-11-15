package blocksworld;

import java.util.*;
import modelling.*;

/**
 * The {@code BWRegularyConstraintsBuilder} class is responsible for creating 
 * regularity constraints for the Blocks World model. These constraints enforce 
 * rules about how blocks can be positioned relative to each other within a 
 * defined set of stacks.
 */
public class BWRegularyConstraintsBuilder {
    private Set<Variable> variables;
    private Set<Constraint> constraints;
    private int nbBlocks;
    private int nbStacks;

    /**
     * Constructs a {@code BWRegularyConstraintsBuilder} instance with a specified 
     * number of blocks and stacks. The constructor initializes the model variables 
     * and generates constraints based on predefined rules.
     *
     * @param nbBlocks the number of blocks in the Blocks World model.
     * @param nbStacks the number of stacks in the Blocks World model.
     * @throws IllegalArgumentException if the number of blocks or stacks is negative.
     */
    public BWRegularyConstraintsBuilder(int nbBlocks, int nbStacks) {
        if (nbBlocks < 0 || nbStacks < 0) {
            throw new IllegalArgumentException("The number of blocks or stacks cannot be negative.");
        }
        this.nbBlocks = nbBlocks;
        this.nbStacks = nbStacks;

        // Initialize the variables and constraints
        BWVariablesBuilder bwvariables = new BWVariablesBuilder(nbBlocks, nbStacks);
        this.variables = bwvariables.getVariables();
        this.constraints = new HashSet<>();
        createConstraints();
    }

    /**
     * Creates the regularity constraints for the Blocks World model.
     * <p>
     * The constraints define valid configurations of blocks. Specifically:
     * <ul>
     *     <li>If a block is on another block, the block below is restricted in terms 
     *     of which other blocks it can be positioned on, based on a calculated difference.</li>
     *     <li>Ensures consistency in the domain values of related variables.</li>
     * </ul>
     */
    private void createConstraints() {
        for (Variable i : this.variables) {
            if (Variable.isBlockOnVariable(i)) {
                for (Variable j : this.variables) {
                    if (Variable.isBlockOnVariable(j)) {
                        if (i.getName() != j.getName()) {
                            Set<Object> s1 = new HashSet<>();
                            s1.add(j.getName());
                            Set<Object> s2 = new HashSet<>(j.getDomain());
                            s2.remove(i.getName());

                            // Si un bloc a est posé sur un autre b, b ne peut être poser que 
                            // sur un bloc c dont b-c = a-b
                            int ecart = i.getName() - j.getName();
                            for (int k = 0; k < this.nbBlocks; k++) {
                                if (j.getName() - k != ecart) {
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
     * <p>
     * These constraints ensure that the relationships between blocks adhere to 
     * predefined rules about how blocks can be stacked and positioned.
     *
     * @return a set containing all the regularity constraints.
     */
    public Set<Constraint> getConstraints() {
        return constraints;
    }

    /**
     * Returns the total number of blocks in the model.
     *
     * @return the number of blocks.
     */
    public int getNbBlocks() {
        return nbBlocks;
    }

    /**
     * Returns the total number of stacks in the model.
     *
     * @return the number of stacks.
     */
    public int getNbStacks() {
        return nbStacks;
    }
}
