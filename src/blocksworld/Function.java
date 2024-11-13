package blocksworld;

import java.util.*;

import javax.swing.*;

import bwmodel.BWState;
import bwmodel.BWStateBuilder;
import bwui.BWComponent;
import bwui.BWIntegerGUI;
import datamining.AssociationRule;
import datamining.Itemset;
import modelling.BooleanVariable;
import modelling.Variable;
import planning.Action;

/**
 * The Function class provides utility methods for handling Block World states,
 * GUI representation, and data mining tasks, such as displaying frequent itemsets
 * and association rules.
 */
public class Function {

    /**
     * Converts a list of block stacks into a Blocks World state.
     * 
     * @param stacks    A list of stacks where each stack is a list of block IDs.
     * @param nbBlocks  The total number of blocks in the world.
     * @return A map representing the Blocks World state with variable assignments.
     */
    public static Map<Variable, Object> listToBwState(List<List<Integer>> stacks, int nbBlocks) {
        Map<Variable, Object> bwState = new HashMap<>();
        int nbPiles = stacks.size();

        for (int i = 0; i < nbPiles; i++) {
            List<Integer> stack = stacks.get(i);

            // If the stack is empty, mark it as free
            if (stack.isEmpty()) {
                bwState.put(new BooleanVariable(-(i + 1)), true);
            } else {
                bwState.put(new BooleanVariable(-(i + 1)), false);

                for (int j = 0; j < stack.size(); j++) {
                    int blockId = stack.get(j);
                    if (j == 0) {
                        bwState.put(new BooleanVariable(blockId), stack.size() > 1);

                        Set<Object> domain = BWVariablesBuilder.calculDomain(blockId, nbBlocks, nbPiles);
                        Variable v = new Variable(blockId, domain);
                        bwState.put(v, -(i + 1));

                    } else if (j == stack.size() - 1) {
                        bwState.put(new BooleanVariable(blockId), false);

                        Set<Object> domain = BWVariablesBuilder.calculDomain(blockId, nbBlocks, nbPiles);
                        Variable v = new Variable(blockId, domain);
                        bwState.put(v, j == 0 ? -(i + 1) : stack.get(j - 1));

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

    /**
     * Converts a Blocks World state map into a BWState object for GUI representation.
     * 
     * @param monde     The current Blocks World state as a map.
     * @param nbBlocks  The number of blocks in the world.
     * @return A BWState object representing the world state for GUI display.
     */
    public static BWState<Integer> makeBWStateForGUI(Map<Variable, Object> monde, int nbBlocks) {
        BWStateBuilder<Integer> builder = BWStateBuilder.makeBuilder(nbBlocks);
        for (int b = 0; b < nbBlocks; b++) {
            Variable onB = new Variable(b, null);
            int under = (int) monde.get(onB);
            if (under >= 0) { // if the value is a block (not a stack)
                builder.setOn(b, under);
            }
        }
        return builder.getState();
    }

    /**
     * Displays a Blocks World plan in a GUI and animates the plan's actions.
     * 
     * @param initState The initial state of the Blocks World.
     * @param nbBlocks  The number of blocks in the world.
     * @param plan      A list of actions representing the plan to display.
     */
    public static void displayPlan(Map<Variable, Object> initState, int nbBlocks, List<Action> plan) {
        BWIntegerGUI gui = new BWIntegerGUI(nbBlocks);
        JFrame frame = new JFrame("Blocks World");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BWState<Integer> bwState = Function.makeBWStateForGUI(initState, nbBlocks);
        BWComponent<Integer> component = gui.getComponent(bwState);
        frame.add(component);
        frame.pack();
        frame.setVisible(true);

        // Execute each action in the plan with a delay
        Map<Variable, Object> state = initState;
        for (Action a : plan) {
            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            state = a.successor(state);
            component.setState(Function.makeBWStateForGUI(state, nbBlocks));
        }
        System.out.println("Simulation of plan: done.");
    }

    /**
     * Displays a Blocks World state in a GUI with a given title.
     * 
     * @param state     The BWState object to display.
     * @param nbBlocks  The number of blocks in the world.
     * @param title     The window title for the GUI display.
     */
    public static void showBWState(BWState<Integer> state, int nbBlocks, String title){
        BWIntegerGUI gui = new BWIntegerGUI(nbBlocks);
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.add(gui.getComponent(state));
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Creates and displays a BWState from a Blocks World state map in a GUI.
     * 
     * @param monde     The Blocks World state map.
     * @param nbBlocks  The number of blocks in the world.
     * @param title     The window title for the GUI display.
     */
    public static void afficher(Map<Variable, Object> monde, int nbBlocks, String title){
        BWState<Integer> state = Function.makeBWStateForGUI(monde, nbBlocks);
        Function.showBWState(state, nbBlocks, title);
    }

    /**
     * Displays frequent itemsets from data mining in a readable format.
     * 
     * @param itemFrequent  A set of frequent itemsets to display.
     * @param mapping       A map linking BooleanVariables to descriptive strings.
     */
    public static void showFrequentItem(Set<Itemset> itemFrequent, Map<BooleanVariable, String> mapping){
        if (itemFrequent.isEmpty()) {
            System.out.println("No frequent patterns found");
            return;
        }
        for (Itemset itemset : itemFrequent) {
            for (BooleanVariable bVariable : itemset.getItems()) {
                System.out.println(mapping.get(bVariable));
            }
            System.out.println("Frequency = " + itemset.getFrequency() + "\n");
        }
    }

    /**
     * Displays valid association rules in a readable format.
     * 
     * @param rules    A set of association rules to display.
     * @param mapping  A map linking BooleanVariables to descriptive strings.
     */
    public static void showValideRules(Set<AssociationRule> rules, Map<BooleanVariable, String> mapping){
        if (rules.isEmpty()) {
            System.out.println("No valid rules found");
            return;
        }
        for (AssociationRule associationRule : rules) {
            System.out.println("Premise: ");
            for (BooleanVariable variable : associationRule.getPremise()) {
                System.out.println(mapping.get(variable));
            }

            System.out.println("Conclusion: ");
            for (BooleanVariable variable : associationRule.getConclusion()) {
                System.out.println(mapping.get(variable));
            }
            System.out.println("Frequency = " + associationRule.getFrequency());
            System.out.println("Confidence = " + associationRule.getConfidence());
            System.out.println("\n");
        }
    }
}
