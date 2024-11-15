package Tests.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import blocksworld.BWVariablesBuilder;
import modelling.BooleanVariable;
import modelling.Constraint;
import modelling.DifferenceConstraint;
import modelling.Implication;
import modelling.Variable;

public class Function {

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

    public static Set<Variable> createVariables(int nbBlocks, int nbStacks) {
        Set<Variable> variables = new HashSet<>();

        for (int i = 1; i <= nbStacks; i++) {
            BooleanVariable freePile = new BooleanVariable(-i);
            variables.add(freePile);
        }

        for (int i = 0; i < nbBlocks; i++) {

            Set<Object> domain = calculDomain(i, nbBlocks, nbStacks);
            Variable on = new Variable(i, domain);
            variables.add(on);

            BooleanVariable fixed = new BooleanVariable(i);
            variables.add(fixed);
        }

        return variables;
    }

        public static Set<Constraint> createCroissanceConstraints(int nbBlocks, int nbStacks) {
        Set<Constraint> constraints = new HashSet<>();
        Set<Variable> variables = new HashSet<>();

        BWVariablesBuilder bwVariablesBuilder = new BWVariablesBuilder(nbBlocks, nbStacks);
        variables.addAll(bwVariablesBuilder.getVariables());

        for (Variable i : variables) {
            if (Variable.isBlockOnVariable(i)) {
                Set<Object> s = new HashSet<>( i.getDomain());
                for(int k=nbBlocks; k>=(int)i.getName();k--){
                    s.remove(k);
                }
                constraints.add(new Implication(i, i.getDomain(), i, s) );

                for (Variable j : variables) {
                    if (Variable.isBlockOnVariable(j) && !i.equals(j) && i.getName()>j.getName()) {
                        Set<Object> s1 = new HashSet<>();
                        s1.add(j.getName());
                        Set<Object> s2 = BWVariablesBuilder.calculDomain(j.getName(), nbBlocks, nbStacks);
                        for(int k=nbBlocks; k>=(int)j.getName();k--){
                            s2.remove(k);
                        }
                        constraints.add(new Implication(i, s1, j, s2) );
                    }
                }
            }
        }
        return constraints;
    }
/* 
    public static Set<Constraint> createRegularyConstraints(int nbBlocks, int nbStacks, int differentExpected) {
        Set<Constraint> constraints = new HashSet<>();
        Set<Variable> variables = new HashSet<>();

        BWVariablesBuilder bwVariablesBuilder = new BWVariablesBuilder(nbBlocks, nbStacks);
        variables.addAll(bwVariablesBuilder.getVariables());

        for (Variable i : variables) {
            if (Variable.isBlockOnVariable(i)) {
                for (Variable j : variables) {
                    if (Variable.isBlockOnVariable(j)) {
                        if (!i.equals(j)) {
                            constraints.add(new RegularyConstraint(i, j, differentExpected));
                        }
                    }
                }
            }

        }
        return constraints;
    }*/

    public static Set<Constraint> createBasicConstraints(int nbBlocks, int nbStacks) {
        Set<Constraint> constraints = new HashSet<>();
        Set<Variable> variables = new HashSet<>();

        BWVariablesBuilder bwVariablesBuilder = new BWVariablesBuilder(nbBlocks, nbStacks);
        variables.addAll(bwVariablesBuilder.getVariables());

        for (Variable i : variables) {
            if (Variable.isBlockOnVariable(i)) {
                for (Variable j : variables) {
                    if (Variable.isBlockOnVariable(j)) {
                        if (!i.equals(j)) {
                            constraints.add(new DifferenceConstraint(i, j));
                        }
                    }
                }
            }
        }

        // Creation des contraintes de type FixedConstraint et FreeConstraint
        for (Variable i : variables) {
            if (Variable.isBlockOnVariable(i)) {
                for (Variable j : variables) {
                    if (BooleanVariable.isBlockFixedVariable(j) && !i.getName().equals(j.getName())) {
                        Set<Object> s1 = new HashSet<>();s1.add(j.getName());
                        Set<Object> s2 = new HashSet<>();s2.add(true);
                        constraints.add(new Implication(i,s1,j,s2));//new  FixedConstraint(i, j)
                    }
                    if (BooleanVariable.isStackFreeVariable(j)) {
                        Set<Object> s1 = new HashSet<>();s1.add(j.getName());
                        Set<Object> s2 = new HashSet<>();s2.add(false);
                        constraints.add(new Implication(i,s1,j,s2));//new  FreeConstraint(i, j)
                    }
                }
            }
        }
        return constraints;
    }

    public static Set<BooleanVariable> createDataminingVariable(int nbBlocks, int nbStacks){
        Set<BooleanVariable> variables = new HashSet<>();

        for (int i = 0; i < nbBlocks; i++) {
            BooleanVariable fixedVariable = new BooleanVariable(i);
            variables.add(fixedVariable);
            for (int j = 0; j < nbBlocks; j++) {
                if (i != j) {
                    BooleanVariable variable = new BooleanVariable(Integer.valueOf(i + "0" + j));
                    variables.add(variable);
                }
            }
            for (int k = 1; k <= nbStacks; k++) {
                BooleanVariable variable = new BooleanVariable(Integer.valueOf(-k + "" + i));
                variables.add(variable);
            }
        }
        for (int k = 1; k <= nbStacks; k++) {
            BooleanVariable variable = new BooleanVariable(-k);
            variables.add(variable);
        }

        return variables;
    }
    
    public static Map<Variable, Object> listToBwState(List<List<Integer>> stacks, int nbBlocks) {
        Map<Variable, Object> bwState = new HashMap<>();
        int nbPiles = stacks.size();

        for (int i = 0; i < nbPiles; i++) {
            List<Integer> stack = stacks.get(i);

            // Si la pile est vide, on indique qu'elle est libre
            if (stack.isEmpty()) {
                bwState.put(new BooleanVariable(-(i + 1)), true);
            } else {
                bwState.put(new BooleanVariable(-(i + 1)), false);

                for (int j = 0; j < stack.size(); j++) {
                    int blockId = stack.get(j);
                    if (j == 0) {
                        if (stack.size() > 1) {
                            bwState.put(new BooleanVariable(blockId), true);
                        } else {
                            bwState.put(new BooleanVariable(blockId), false);
                        }

                        Set<Object> domain = BWVariablesBuilder.calculDomain(blockId, nbBlocks, nbPiles);
                        Variable v = new Variable(blockId, domain);
                        bwState.put(v, -(i + 1));

                    } else if (j == stack.size() - 1) {
                        bwState.put(new BooleanVariable(blockId), false);

                        Set<Object> domain = BWVariablesBuilder.calculDomain(blockId, nbBlocks, nbPiles);
                        Variable v = new Variable(blockId, domain);
                        if (j == 0) {
                            bwState.put(v, -(i + 1));
                        } else {
                            bwState.put(v, stack.get(j - 1));
                        }

                    } else {
                        Set<Object> domain = BWVariablesBuilder.calculDomain(blockId, nbBlocks, nbPiles);
                        Variable v = new Variable(blockId, domain);
                        bwState.put(v, stack.get(j - 1));

                        bwState.put(new BooleanVariable(blockId), true);
                    }
                }
            }
        }
        return bwState;
    }

}
