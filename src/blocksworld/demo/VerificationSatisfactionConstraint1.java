package blocksworld.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import blocksworld.BWBasicConstraintsBuilder;
import blocksworld.BWCroissanceConstraintsBuilder;
import blocksworld.BWRegularyConstraintsBuilder;
import blocksworld.Function;
import modelling.*;

public class VerificationSatisfactionConstraint1 {
    public static void main(String[] args) {
        //Monde créé à la main
        //Variables des trois blocs
        System.out.println("🔍On a un monde représenté par [[1,2],[],[0]]");
        System.out.println("🔍On crée tous les types de contraintes avec comme ecart 1 pour la contrainte de régularité");

        Set<Object> domain0 = new HashSet<>();
        domain0.add(-3);domain0.add(-2);domain0.add(-1);domain0.add(1);domain0.add(2);
        Variable on0 = new Variable(0,domain0);
        BooleanVariable fixed0 = new BooleanVariable(0);

        Set<Object> domain1 = new HashSet<>();
        domain1.add(-3);domain1.add(-2);domain1.add(-1);domain1.add(0);domain1.add(2);
        Variable on1 = new Variable(1,domain1);
        BooleanVariable fixed1 = new BooleanVariable(1);

        Set<Object> domain2 = new HashSet<>();
        domain2.add(-3);domain2.add(-2);domain2.add(-1);domain2.add(0);domain2.add(1);
        Variable on2 = new Variable(2,domain2);
        BooleanVariable fixed2 = new BooleanVariable(2);

        //Variables des trois piles
        BooleanVariable pile1 = new BooleanVariable(-1);
        BooleanVariable pile2 = new BooleanVariable(-2);
        BooleanVariable pile3 = new BooleanVariable(-3);

        //Une config [[1,2],[],[0]]
        Map<Variable,Object> instanciation = new HashMap<>();
        instanciation.put(pile2,true);
        instanciation.put(pile3,false);
        instanciation.put(pile1,false);

        instanciation.put(on2,1);
        instanciation.put(on1,-1);
        instanciation.put(on0,-3);

        instanciation.put(fixed0,false);
        instanciation.put(fixed1,true);
        instanciation.put(fixed2,false);

        //Recuperation des contraintes de base, de type croissance et regulary
        //Avec l'instanciation fournie tout est satisfait normalement
        Set<Constraint> constraints = new HashSet<>();
        constraints.addAll(new BWBasicConstraintsBuilder(3,3).getConstraints());
        constraints.addAll(new BWCroissanceConstraintsBuilder(3, 3).getConstraints());
        constraints.addAll(new BWRegularyConstraintsBuilder(3,3,1).getConstraints());
        boolean allSatisfied = true;
        for (Constraint constraint : constraints) {
            if(!constraint.isSatisfiedBy(instanciation)){
                System.out.println(constraint + " n'est pas satisfaite ❌");
                allSatisfied = false;
            }
        }
        if(allSatisfied){
            System.out.println("Toutes les contraintes sont satisfaites dans ce monde ✅");
        }

       
        List<List<Integer>> liste = new ArrayList<>();
        List<Integer> stack1 = new ArrayList<>();
        stack1.add(0);stack1.add(1);stack1.add(2);
        List<Integer> stack2 = new ArrayList<>();
        stack2.add(3);stack2.add(7);stack2.add(6);
        List<Integer> stack3 = new ArrayList<>();
        stack3.add(4);stack3.add(5);

        liste.add(stack1);
        liste.add(stack2);
        liste.add(stack3);
        System.out.println("\n\n");
        System.out.println("🔍On a un monde representé par [[0,1,2],[3,7,6],[4,5]]");
        Map<Variable,Object> blocksWord = Function.listToBwState(liste,8);
       
        System.out.println("🔍On crée tous les types de contraintes avec comme ecart 1 pour la contrainte de régularité");
        constraints = new HashSet<>();
        constraints.addAll(new BWBasicConstraintsBuilder(8,3).getConstraints());
        constraints.addAll(new BWRegularyConstraintsBuilder(8,3,1).getConstraints());
        constraints.addAll(new BWCroissanceConstraintsBuilder(8,3).getConstraints());
        allSatisfied = true;
        for (Constraint constraint : constraints) {
            if(!constraint.isSatisfiedBy(blocksWord)){
                System.out.println(constraint + " n'est pas satisfaite ❌");
                allSatisfied = false;
            }
        }
        if(allSatisfied){
            System.out.println("Toutes les contraintes sont satisfaites dans ce monde ✅");
        }
    }
}
