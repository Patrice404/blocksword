package blocksworld.demo;

import java.util.*;

import blocksworld.*;
import cp.*;
import modelling.*;

public class CreationConfiguration1 {
    public static void main(String[] args) {
       
        System.out.println("On cherche une configuration composée de 4 blocks et 3 piles\n" + 
                        "Contraintes : basiques et de regularités avec écart 2 dans chaque pile\n");
        Set<Variable> variables = new HashSet<>();
        Set<Constraint> constraints = new HashSet<>();

        BWVariablesBuilder bwVariablesBuilder = new BWVariablesBuilder(4, 3);
        BWBasicConstraintsBuilder bwBasicConstraintsBuilder = new BWBasicConstraintsBuilder(4, 3);
        BWRegularyConstraintsBuilder bwRegularyConstraintsBuilder = new BWRegularyConstraintsBuilder(4, 3, 2);

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
            Function.afficher(monde, 4, "World with BacktrackSolver");
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
            Function.afficher(monde, 4, "World with MACSolver");
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
            Function.afficher(monde, 4, "World with HeuristicMACSolver");
        } else {
            System.out.println("🔍Il n'existe pas un état pour ces contraintes");
        }
    }
}
