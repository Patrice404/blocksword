package blocksworld.demo;

import java.util.*;

import blocksworld.*;
import blocksworld.utils.Function;
import cp.*;
import modelling.*;

public class CreationConfiguration2 {
    public static void main(String[] args) {

        System.out.println(" On cherche une configuration de 5 blocks et 2 piles\n  Contraintes : basiques et croissantes\n");
        Set<Variable> variables = new HashSet<>();
        Set<Constraint> constraints = new HashSet<>();
        BWVariablesBuilder bwVariablesBuilder = new BWVariablesBuilder(6, 3);
        BWBasicConstraintsBuilder bwBasicConstraintsBuilder = new BWBasicConstraintsBuilder(6, 3);
        BWCroissanceConstraintsBuilder bwCroissanceConstraintsBuilder = new BWCroissanceConstraintsBuilder(6, 3);

        variables.addAll(bwVariablesBuilder.getVariables());
        constraints.addAll(bwBasicConstraintsBuilder.getConstraints());
        constraints.addAll(bwCroissanceConstraintsBuilder.getConstraints());

        MACSolver macSolver = new MACSolver(variables, constraints);
        long start = System.currentTimeMillis();
        Map<Variable, Object> monde = macSolver.solve();
        long end = System.currentTimeMillis();
        System.out.println("🔍 Résultat MACSolver");
        System.out.println("⏱️  Temps d'exécution : " + (end - start) + " ms\n");
        if (monde != null) {
            Function.afficher(monde, 6, "Monde MACSolver");
        } else {
            System.out.println("🔍Il n'existe pas un état pour ces contriantes");
        }

        BacktrackSolver backtrackSolver = new BacktrackSolver(variables, constraints);
        start = System.currentTimeMillis();
        monde = backtrackSolver.solve();
        end = System.currentTimeMillis();
        System.out.println("🔍 Résultat BacktrackSolver");
        System.out.println("⏱️  Temps d'exécution : " + (end - start) + " ms\n");
        if (monde != null) {
            Function.afficher(monde, 6, "Monde BacktrackSolver");
        } else {
            System.out.println("🔍Il n'existe pas un état pour ces contriantes");
        }

        NbConstraintsVariableHeuristic nbConstraintsVariableHeuristic = new NbConstraintsVariableHeuristic(constraints,
                true);
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
            Function.afficher(monde, 6, "HeuristicMACSolver");
        } else {
            System.out.println("🔍Il n'existe pas un état pour ces contriantes");
        }

    }
}
