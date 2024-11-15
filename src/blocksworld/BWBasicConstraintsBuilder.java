package blocksworld;

import java.util.*;
import modelling.*;

/**
 * The {@code BWBasicConstraintsBuilder} class is responsible for defining 
 * fundamental constraints in the Blocks World model. These constraints ensure 
 * that blocks and stacks interact correctly, following the specified rules:
 * <ul>
 *   <li>Blocks cannot overlap (difference constraints).</li>
 *   <li>Fixed and free stack conditions are properly enforced.</li>
 * </ul>
 */
public class BWBasicConstraintsBuilder {
    private Set<Variable> variables;
    private Set<Constraint> constraints;
    private int nbBlocks;
    private int nbStacks;

    /**
     * Constructs a {@code BWBasicConstraintsBuilder} instance with a specified 
     * number of blocks and stacks. It initializes the variables and generates the 
     * basic constraints required for the Blocks World model.
     *
     * @param nbBlocks the number of blocks in the model.
     * @param nbStacks the number of stacks in the model.
     * @throws IllegalArgumentException if the number of blocks or stacks is negative or zero.
     */
    public BWBasicConstraintsBuilder(int nbBlocks, int nbStacks) {
        if (nbBlocks < 0 || nbStacks < 0) {
            throw new IllegalArgumentException("The number of blocks or stacks cannot be negative or zero.");
        }
        BWVariablesBuilder bwvariables = new BWVariablesBuilder(nbBlocks, nbStacks);
        this.variables = bwvariables.getVariables();
        this.constraints = new HashSet<>();
        this.nbBlocks = nbBlocks;
        this.nbStacks = nbStacks;
        createConstraints();
    }

    /**
     * Defines the basic constraints for the Blocks World model.
     * <p>
     * The constraints include:
     * <ul>
     *   <li><b>Difference Constraints</b>: Ensures that two blocks cannot occupy 
     *       the same position in a stack.</li>
     *   <li><b>Fixed Constraints</b>: If a block <i>a</i> is placed on another block 
     *       <i>b</i>, the <i>fixed</i> variable of block <i>b</i> must be <code>true</code>.</li>
     *   <li><b>Free Constraints</b>: If a block is placed on a stack, the <i>free</i> 
     *       variable of the stack must be <code>false</code>.</li>
     * </ul>
     */
    private void createConstraints() {
        for (Variable i : this.variables) {
            if (Variable.isBlockOnVariable(i)) {
                for (Variable j : this.variables) {
                    if (Variable.isBlockOnVariable(j) && !i.equals(j)) {

                        this.constraints.add(new DifferenceConstraint(i, j));
                    }
                }
            }
        }

        for (Variable i : this.variables) {
            if (Variable.isBlockOnVariable(i)) {
                for (Variable j : this.variables) {
                    if (BooleanVariable.isBlockFixedVariable(j) && !i.getName().equals(j.getName())) {

                        Set<Object> s1 = new HashSet<>();
                        s1.add(j.getName());
                        Set<Object> s2 = new HashSet<>();
                        s2.add(true);
                        this.constraints.add(new Implication(i, s1, j, s2));
                    }
                    if (BooleanVariable.isStackFreeVariable(j)) {

                        Set<Object> s1 = new HashSet<>();
                        s1.add(j.getName());
                        Set<Object> s2 = new HashSet<>();
                        s2.add(false);
                        this.constraints.add(new Implication(i, s1, j, s2));
                    }
                }
            }
        }
    }

    /**
     * Retrieves the set of constraints defined for the Blocks World model.
     * @return a set of constraints defined in the model.
     */
    public Set<Constraint> getConstraints() {
        return constraints;
    }

    /**
     * Gets the number of blocks in the model.
     * 
     * @return the total number of blocks.
     */
    public int getNbBlocks() {
        return nbBlocks;
    }

    /**
     * Gets the number of stacks in the model.
     * 
     * @return the total number of stacks.
     */
    public int getNbStacks() {
        return nbStacks;
    }
}
