package blocksworld;

import java.util.*;

import modelling.*;

/**
 * The BWBasicConstraintsBuilder class models essential constraints in the
 * context of the Blocks World problem. It manages the creation of variables 
 * and defines the constraints that apply between blocks and stacks in the model.
 * 
 * This class incorporates constraints such as {@link OnDifferenceConstraint},
 * {@link FixedConstraint}, and {@link FreeConstraint} to control block placement
 * and movement within the Blocks World simulation.
 */
public class BWBasicConstraintsBuilder {
    private Set<Variable> variables;
    private Set<Constraint> constraints;
    private int nbBlocks;
    private int nbStacks;

    /**
     * Constructs a BWBasicConstraintsBuilder instance with a specified number
     * of blocks and stacks. Initializes the block and stack variables, and
     * generates the corresponding constraints automatically.
     * 
     * @param nbBlocks The total number of blocks in the Blocks World simulation.
     * @param nbStacks The total number of stacks available in the simulation.
     * @throws IllegalArgumentException if the number of blocks is negative
     *         or the number of stacks is non-positive.
     */
    public BWBasicConstraintsBuilder(int nbBlocks, int nbStacks) {
        if(nbBlocks < 0 || nbStacks < 0){
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
     * Creates the basic constraints that govern relationships between
     * block and stack variables in the Blocks World model.
     * <ul>
     * <li>{@link OnDifferenceConstraint}: Ensures that two blocks cannot share
     *      the same "on" position.</li>
     * <li>{@link FixedConstraint}: Associates a block's position with its "fixed" 
     *      variable to maintain placement constraints.</li>
     * <li>{@link FreeConstraint}: Links a block's position with the "free" status 
     *      of a stack.</li>
     * </ul>
     */
    private void createConstraints() {
        // Création des contraintes de type OnDifferenceConstraint pour assurer l'unicité des positions "on"
        for (Variable i : this.variables) {
            if (Variable.isBlockOnVariable(i)) {
                for (Variable j : this.variables) {
                    if (Variable.isBlockOnVariable(j) && !i.equals(j)) {
                        this.constraints.add(new OnDifferenceConstraint(i, j));
                    }
                }
            }
        }

        // Création des contraintes de type FixedConstraint et FreeConstraint pour la gestion des positions fixes et libres
        for (Variable i : this.variables) {
            if (Variable.isBlockOnVariable(i)) {
                for (Variable j : this.variables) {
                    if (BooleanVariable.isBlockFixedVariable(j) && !i.getName().equals(j.getName())) {
                        this.constraints.add(new FixedConstraint(i, j));
                    }
                    if (BooleanVariable.isStackFreeVariable(j)) {
                        this.constraints.add(new FreeConstraint(i, j));
                    }
                }
            }
        }
    }

    /**
     * Returns the set of constraints defined between blocks and stacks.
     * 
     * @return A set of constraints that includes OnDifferenceConstraint, 
     *         FixedConstraint, and FreeConstraint.
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
}
