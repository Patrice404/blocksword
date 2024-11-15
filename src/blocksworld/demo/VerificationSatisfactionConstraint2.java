package blocksworld.demo;

import java.util.*;

import blocksworld.*;
import modelling.*;

public class VerificationSatisfactionConstraint2 {
     public static void main(String[] args) {
        //Monde créé à la main
        //Variables des trois blocs
        System.out.println("🔍On a un monde représenté par [[0,2,1],[3,5],[6,4]]");
        System.out.println("🔍On crée tous les types de contraintes avec comme ecart 2 pour la contrainte de régularité");

        List<List<Integer>> liste = new ArrayList<>();
        List<Integer> stack1 = new ArrayList<>();
        stack1.add(0);stack1.add(2);stack1.add(1);
        List<Integer> stack2 = new ArrayList<>();
        stack2.add(3);stack2.add(5);
        List<Integer> stack3 = new ArrayList<>();
        stack3.add(6);stack3.add(4);

        liste.add(stack1);
        liste.add(stack2);
        liste.add(stack3);
        System.out.println("\n");
        Map<Variable,Object> blocksWord = Function.listToBwState(liste,7);
       
        Set<Constraint> constraints = new HashSet<>();
        constraints.addAll(new BWBasicConstraintsBuilder(7,3).getConstraints());
        constraints.addAll(new BWRegularyConstraintsBuilder(7,3).getConstraints());
        constraints.addAll(new BWCroissanceConstraintsBuilder(7,3).getConstraints());
        boolean allSatisfied = true;
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
