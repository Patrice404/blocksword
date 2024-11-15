package blocksworld;

import java.util.*;

import modelling.BooleanVariable;

/**
 * The {@code DataminingVariableBuilder} class is responsible for creating 
 * and managing boolean variables that represent the state of the Blocks World.
 * It generates variables for relationships such as "on", "onTable", "fixed", and "free",
 * and provides mappings between these variables and their string descriptions for ease of use.
 */
public class DataminingVariableBuilder {
    //[[1,2],[0,3],[]]
    private Map<String, BooleanVariable> variables;
    private Map<BooleanVariable, String> mapping = new HashMap<>();
    private int nbBlocks;
    private int nbStacks;

    /**
     * Constructs a {@code DataminingVariableBuilder} for a Blocks World with the specified
     * number of blocks and stacks.
     *
     * @param nbBlocks the number of blocks in the Blocks World.
     * @param nbStacks the number of stacks in the Blocks World.
     * @throws IllegalArgumentException if the number of blocks or stacks is negative.
     */
    public DataminingVariableBuilder(int nbBlocks, int nbStacks) {
        if (nbBlocks < 0 || nbStacks < 0) {
            throw new IllegalArgumentException("The number of blocks or stacks cannot be negative.");
        }
        this.nbBlocks = nbBlocks;
        this.nbStacks = nbStacks;
        buildVariables();
    }

    /**
     * Builds the boolean variables and their string mappings for the Blocks World.
     * <ul>
     *     <li>Variables of the form {@code fixed_X}, representing whether a block is fixed.</li>
     *     <li>Variables of the form {@code on_X_Y}, representing a block on another block.</li>
     *     <li>Variables of the form {@code onTable_X_Y}, representing a block on a specific stack.</li>
     *     <li>Variables of the form {@code free_X}, representing whether a stack is free.</li>
     * </ul>
     */
    private void buildVariables() {
        this.variables = new HashMap<>();

        for (int i = 0; i < this.nbBlocks; i++) {
            // Create "fixed" variables
            BooleanVariable fixedVariable = new BooleanVariable(i);
            this.variables.put("fixed_" + i, fixedVariable);
            this.mapping.put(fixedVariable, "fixed_" + i);

            for (int j = 0; j < nbBlocks; j++) {
                if (i != j) {
                    // Create "on block" variables
                    BooleanVariable variable = new BooleanVariable(Integer.valueOf(i + "0" + j));
                    this.variables.put("on_" + i + "_" + j, variable);
                    this.mapping.put(variable, "on_" + i + "_" + j);
                }
            }

            for (int k = 1; k <= this.nbStacks; k++) {
                // Create "on table" variables
                BooleanVariable variable = new BooleanVariable(Integer.valueOf(-k + "" + i));
                this.variables.put("onTable_" + i + "_" + -k, variable);
                this.mapping.put(variable, "onTable_" + i + "_" + -k);
            }
        }

        for (int k = 1; k <= this.nbStacks; k++) {
            // Create "free stack" variables
            BooleanVariable variable = new BooleanVariable(-k);
            this.variables.put("free_" + -k, variable);
            this.mapping.put(variable, "free_" + -k);
        }
    }

    /**
     * Generates a set of boolean variables that represent the given state of the Blocks World.
     *
     * @param state a list of stacks, where each stack is a list of block IDs.
     * @return a set of {@code BooleanVariable} instances representing the given state.
     */
    public Set<BooleanVariable> getInstance(List<List<Integer>> state) {
        Set<BooleanVariable> instance = new HashSet<>();
        int i = 0;

        for (List<Integer> stack : state) {
            i++;
            int size = stack.size();
            if (size == 0) {
                // Add variable for free stack
                instance.add(this.variables.get("free_" + -i));
            } else {
                for (int j = 0; j < size; j++) {
                    if (j == 0) {
                        int on = stack.get(j);
                        // Add variable for block on the table
                        instance.add(this.variables.get("onTable_" + on + "_" + -i));
                    } else {
                        int on = stack.get(j);
                        int under = stack.get(j - 1);
                        // Add variable for block on another block
                        instance.add(this.variables.get("on_" + on + "_" + under));
                        // Add variable for fixed block
                        instance.add(this.variables.get("fixed_" + under));
                    }
                }
            }
        }

        return instance;
    }

    /**
     * Returns the set of all boolean variables created by this builder.
     *
     * @return a set of {@code BooleanVariable} instances.
     */
    public Set<BooleanVariable> getVariables() {
        return new HashSet<>(this.variables.values());
    }

    /**
     * Returns the mapping between boolean variables and their string descriptions.
     *
     * @return a map where keys are {@code BooleanVariable} instances and values are their string descriptions.
     */
    public Map<BooleanVariable, String> getMapping() {
        return this.mapping;
    }
}
