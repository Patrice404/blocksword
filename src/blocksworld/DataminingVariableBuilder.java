package blocksworld;

import java.util.*;

import modelling.BooleanVariable;

public class DataminingVariableBuilder {

    private Map<String, BooleanVariable> variables;
    private Map<BooleanVariable, String> mapping = new HashMap<>();
    private int nbBlocks;
    private int nbStacks;
    public DataminingVariableBuilder(int nbBlocks, int nbStacks) {
        if(nbBlocks<0 || nbStacks <0){
            throw new IllegalArgumentException("The number of blocks or stacks cannot be negative.");
        }
        this.nbBlocks = nbBlocks;
        this.nbStacks = nbStacks;
        buildVariables();
    }

    private void buildVariables() {
        this.variables = new HashMap<>();

        for (int i = 0; i < this.nbBlocks; i++) {
            //Création des variables fixed
            BooleanVariable fixedVariable = new BooleanVariable(i);
            this.variables.put("fixed_" + i, fixedVariable);
            this.mapping.put(fixedVariable, "fixed_" + i);
            for (int j = 0; j < nbBlocks; j++) {
                if (i != j) {
                    // Création des variables on block block
                    BooleanVariable variable = new BooleanVariable(Integer.valueOf(i + "0" + j));
                    this.variables.put("on_" + i + "_" + j, variable);
                    this.mapping.put(variable, "on_" + i + "_" + j);
                }
            }
            for (int k = 1; k <= this.nbStacks; k++) {
                // Création des variables on table block
                BooleanVariable variable = new BooleanVariable(Integer.valueOf(-k + "" + i));
                this.variables.put("onTable_" + i + "_" + -k, variable);
                this.mapping.put(variable, "onTable_" + i + "_" + -k);
            }
        }
        for (int k = 1; k <= this.nbStacks; k++) {
            // Création des variables free des piles
            BooleanVariable variable = new BooleanVariable(-k);
            this.variables.put("free_" + -k, variable);
            this.mapping.put(variable,"free_" + -k);
        }
    }

    public Set<BooleanVariable> getInstance(List<List<Integer>> state) {
        Set<BooleanVariable> instance = new HashSet<>();
        int i = 0;
        for (List<Integer> stack : state) {
            i++;
            int size = stack.size();
            if(size==0){
                instance.add(this.variables.get("free_" + -i));
            }else {
                for (int j = 0; j < size; j++) {
                    if(j==0){
                        int on = stack.get(j);
                        instance.add(this.variables.get("onTable_" + on + "_" + -i));
                    }
                    else{
                        int on = stack.get(j);
                        int under = stack.get(j-1);
                        instance.add(this.variables.get("on_" + on + "_" + under));

                        instance.add(this.variables.get("fixed_" + under));
                    }
                }
            }
        }
        return instance;
    }

    public Set<BooleanVariable> getVariables() {
        return new HashSet<>(this.variables.values());
    }

    public Map<BooleanVariable, String> getMapping(){
        return this.mapping;
    }
    

}
