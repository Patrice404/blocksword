package blocksworld.demo;

import java.util.*;

import blocksworld.*;
import blocksworld.utils.Function;
import cp.*;
import modelling.*;

public class CreationConfiguration3 {
    public static void main(String[] args) {
        System.out.println("On cherche à créer une configuration ayant 2 pile et 8 block avec les\n" + 
                        "contraints basiques, de croissance et de régularité avec comme écart de 1\n" + //
                        "entre chaque bloc sur toutes les piles\n");

        Set<Variable> variables = new HashSet<>();
        Set<Constraint> constraints = new HashSet<>();
        BWVariablesBuilder bwVariablesBuilder = new BWVariablesBuilder(8, 2);
        BWBasicConstraintsBuilder bwBasicConstraintsBuilder = new BWBasicConstraintsBuilder(8, 2);
        BWCroissanceConstraintsBuilder bwCroissanceConstraintsBuilder = new BWCroissanceConstraintsBuilder(8, 2);
        BWRegularyConstraintsBuilder bwRegularyConstraintsBuilder = new BWRegularyConstraintsBuilder(8, 2);

        variables.addAll(bwVariablesBuilder.getVariables());
        constraints.addAll(bwBasicConstraintsBuilder.getConstraints());
        constraints.addAll(bwCroissanceConstraintsBuilder.getConstraints());
        constraints.addAll(bwRegularyConstraintsBuilder.getConstraints());

        MACSolver macSolver = new MACSolver(variables, constraints);
        long start = System.currentTimeMillis();
        Map<Variable, Object> monde = macSolver.solve();
        long end = System.currentTimeMillis();
        System.out.println("🔍 Résultat MACSolver");
        System.out.println("⏱️  Temps d'exécution : " + (end - start) + " ms\n");
        if (monde != null) {
            Function.afficher(monde, 8, "World with MACSolver");
        } else {
            System.out.println("🔍 Il n'existe pas un état pour ces contriantes");
        }

        BacktrackSolver backtrackSolver = new BacktrackSolver(variables, constraints);
        start = System.currentTimeMillis();
        monde = backtrackSolver.solve();
        end = System.currentTimeMillis();
        System.out.println("🔍 Résultat BacktrackSolver");
        System.out.println("⏱️  Temps d'exécution : " + (end - start) + " ms\n");
        if (monde != null) {
            Function.afficher(monde, 8, "World with BacktrackSolver");
        } else {
            System.out.println("🔍 Il n'existe pas un état pour ces contriantes");
        }

        NbConstraintsVariableHeuristic nbConstraintsVariableHeuristic = new NbConstraintsVariableHeuristic(constraints,
                true);
        DomainSizeVariableHeuristic domainSizeVariableHeuristic = new DomainSizeVariableHeuristic(true);
        RandomValueHeuristic randomValueHeuristic = new RandomValueHeuristic(new Random());

        HeuristicMACSolver heuristicMACSolver = new HeuristicMACSolver(variables, constraints,
                nbConstraintsVariableHeuristic, randomValueHeuristic);
        start = System.currentTimeMillis();
        monde = heuristicMACSolver.solve();
        end = System.currentTimeMillis();
        System.out.println("🔍 Résultat HeuristicMACSolver");
        System.out.println("⏱️  Temps d'exécution : " + (end - start) + " ms\n");
        if (monde != null) {
            Function.afficher(monde, 8, "World with HeuristicMACSolver");
        } else {
            System.out.println("🔍Il n'existe pas un état pour ces contriantes");
        }

    }
}
