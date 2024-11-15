package blocksworld.demo;

import java.util.*;

import blocksworld.*;
import blocksworld.utils.Function;
import cp.*;
import modelling.*;

public class CreationConfiguration1 {
    public static void main(String[] args) {
       
        System.out.println("On cherche une configuration composée de 6 blocks et 3 piles\n" + 
                        "Contraintes : basiques et de regularités\n");
        Set<Variable> variables = new HashSet<>();
        Set<Constraint> constraints = new HashSet<>();

        BWVariablesBuilder bwVariablesBuilder = new BWVariablesBuilder(6, 3);
        BWBasicConstraintsBuilder bwBasicConstraintsBuilder = new BWBasicConstraintsBuilder(6, 3);
        BWRegularyConstraintsBuilder bwRegularyConstraintsBuilder = new BWRegularyConstraintsBuilder(6, 3);

        variables = bwVariablesBuilder.getVariables();
        constraints.addAll(bwBasicConstraintsBuilder.getConstraints());
        constraints.addAll(bwRegularyConstraintsBuilder.getConstraints());

        BacktrackSolver backtrackSolver = new BacktrackSolver(variables, constraints);
        long start = System.currentTimeMillis();
        Map<Variable, Object> monde = backtrackSolver.solve();
        long end = System.currentTimeMillis();
        System.out.println("🔍 Résultat BacktrackSolver");
        System.out.println("⏱️  Temps d'exécution : " + (end - start) + " ms\n");
        if (monde != null) {
            Function.afficher(monde, 6, "World with BacktrackSolver");
        } else {
            System.out.println("🔍 Il n'existe pas un état pour ces contraintes");
        }


        MACSolver macSolver = new MACSolver(variables, constraints);
        start = System.currentTimeMillis();
        monde = macSolver.solve();
        end = System.currentTimeMillis();
        System.out.println("🔍 Résultat MACSolver");
        System.out.println("⏱️  Temps d'exécution : " + (end - start) + " ms\n");
        if (monde != null) {
            Function.afficher(monde, 6, "World with MACSolver");
        } else {
            System.out.println("🔍 Il n'existe pas un état pour ces contraintes");
        }
 

        //NbConstraintsVariableHeuristic nbConstraintsVariableHeuristic = new NbConstraintsVariableHeuristic(constraints,true);
        DomainSizeVariableHeuristic domainSizeVariableHeuristic = new DomainSizeVariableHeuristic(true);
        RandomValueHeuristic randomValueHeuristic = new RandomValueHeuristic(new Random());

        HeuristicMACSolver heuristicMACSolver = new HeuristicMACSolver(variables, constraints,
        domainSizeVariableHeuristic, randomValueHeuristic);
        start = System.currentTimeMillis();
        monde = heuristicMACSolver.solve();
        end = System.currentTimeMillis();
        System.out.println("🔍 Résultat HeuristicMACSolver");
        System.out.println("⏱️  Temps d'exécution : " + (end - start) + " ms\n");
        if (monde != null) {
            Function.afficher(monde, 6, "World with HeuristicMACSolver");
        } else {
            System.out.println("🔍Il n'existe pas un état pour ces contraintes");
        }
    }
}
