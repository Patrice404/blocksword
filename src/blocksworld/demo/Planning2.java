package blocksworld.demo;

import java.util.*;

import blocksworld.*;
import blocksworld.utils.Function;
import modelling.*;
import planning.*;

public class Planning2{
    public static void main(String[] args) {
        // On veut créer un monde constitué de 4 piles et de 4 blocks
        // Etat initial
        // [[3,1],[2,0],[],[]]

        BooleanVariable pile1 = new BooleanVariable(-1);
        BooleanVariable pile2 = new BooleanVariable(-2);
        BooleanVariable pile3 = new BooleanVariable(-3);
        BooleanVariable pile4 = new BooleanVariable(-4);

        Set<Object> domain0 = new HashSet<>();
        domain0.add(-4);
        domain0.add(-3);
        domain0.add(-2);
        domain0.add(-1);
        domain0.add(1);
        domain0.add(2);
        domain0.add(3);
        Variable block0On = new Variable(0, domain0);
        BooleanVariable block0Fixed = new BooleanVariable(0);

        Set<Object> domain1 = new HashSet<>();
        domain1.add(-4);
        domain1.add(-3);
        domain1.add(-2);
        domain1.add(-1);
        domain1.add(0);
        domain1.add(2);
        domain1.add(3);
        Variable block1On = new Variable(1, domain1);
        BooleanVariable block1Fixed = new BooleanVariable(1);

        Set<Object> domain2 = new HashSet<>();
        domain2.add(-4);
        domain2.add(-3);
        domain2.add(-2);
        domain2.add(-1);
        domain2.add(0);
        domain2.add(1);
        domain2.add(3);
        Variable block2On = new Variable(2, domain2);
        BooleanVariable block2Fixed = new BooleanVariable(2);

        Set<Object> domain3 = new HashSet<>();
        domain3.add(-4);
        domain3.add(-3);
        domain3.add(-2);
        domain3.add(-1);
        domain3.add(0);
        domain3.add(1);
        domain3.add(2);
        Variable block3On = new Variable(3, domain3);
        BooleanVariable block3Fixed = new BooleanVariable(3);

        // Etat initial
        System.out.println("🔍 Initial state [[3,1],[2,0],[],[]]");
        Map<Variable, Object> initState = new HashMap<>();
        initState.put(pile1, false);
        initState.put(pile2, false);
        initState.put(pile3, true);
        initState.put(pile4, true);

        initState.put(block3On, -1);
        initState.put(block3Fixed, true);
        initState.put(block1On, 3);
        initState.put(block1Fixed, false);
        initState.put(block2On, -2);
        initState.put(block2Fixed, true);
        initState.put(block0On, 2);
        initState.put(block0Fixed, false);

        
        System.out.println("🔍 Goal state [[1],[3],[2],[0]]");
        Map<Variable, Object> goalState = new HashMap<>();
        goalState.put(pile1, false);
        goalState.put(pile2, false);
        goalState.put(pile3, false);
        goalState.put(pile4, false);

        goalState.put(block1On, -1);
        goalState.put(block3On, -2);
        goalState.put(block2On, -3);
        goalState.put(block0On, -4);
        BasicGoal goal = new BasicGoal(goalState);

        // Obtenir toutes les actions possibles
        BWActionsBuilder bwActionsBuilder = new BWActionsBuilder(4, 4);
        Set<Action> actions = bwActionsBuilder.getActions();

        DFSPlanner dfsPlanner = new DFSPlanner(initState, actions, goal);
        dfsPlanner.activateNodeCount(true);
        long start = System.currentTimeMillis();
        List<Action> plan = dfsPlanner.plan();
        long fin = System.currentTimeMillis();
        System.out.println("🔍 Résultat DFSPlanner");
        System.out.println("⏱️ Temps d'exécution : " + (fin - start) + "ms");
        System.out.println("🚀 Nombre d'actions dans le plan : " + plan.size());
        System.out.println("⏳ Nombre de noeuds explorés : " + dfsPlanner.getNbNoeudExplore() + "\n");

        BFSPlanner bfsPlanner = new BFSPlanner(initState, actions, goal);
        bfsPlanner.activateNodeCount(true);
        start = System.currentTimeMillis();
        plan = bfsPlanner.plan();
        fin = System.currentTimeMillis();
        System.out.println("🔍 Résultat BFSPlanner");
        System.out.println("⏱️ Temps d'exécution : " + (fin - start) + "ms");
        System.out.println("🚀 Nombre d'actions dans le plan : " + plan.size());
        System.out.println("⏳ Nombre de noeuds explorés : " + bfsPlanner.getNbNoeudExplore() + "\n");

        DijkstraPlanner dijkstraPlanner = new DijkstraPlanner(initState, actions, goal);
        dijkstraPlanner.activateNodeCount(true);
        start = System.currentTimeMillis();
        plan = dijkstraPlanner.plan();
        fin = System.currentTimeMillis();
        System.out.println("🔍 Résultat DijkstraPlanner");
        System.out.println("⏱️ Temps d'exécution : " + (fin - start) + "ms");
        System.out.println("🚀 Nombre d'actions dans le plan : " + plan.size());
        System.out.println("⏳ Nombre de noeuds explorés : " + dijkstraPlanner.getNbNoeudExplore() + "\n");

        MisplacedBlock misplacedBlockHeurostic = new MisplacedBlock(goal);
        WellplacedBlock wellplacedBlockHeuristic = new WellplacedBlock(goal);
        SimilarityHeuristic similarityHeuristic = new SimilarityHeuristic(goalState, 1);

        AStarPlanner aStarPlanner = new AStarPlanner(initState, actions, goal, misplacedBlockHeurostic);
        aStarPlanner.activateNodeCount(true);
        start = System.currentTimeMillis();
        plan = aStarPlanner.plan();
        fin = System.currentTimeMillis();
        System.out.println("🔍Résultat AStarPlanner");
        System.out.println("⏱️ Temps d'exécution : " + (fin - start) + "ms");
        System.out.println("🚀 Nombre d'actions dans le plan : " + plan.size());
        System.out.println("⏳ Nombre de noeuds explorés : " + aStarPlanner.getNbNoeudExplore() + "\n");

       Function.displayPlan(initState, 4, plan);
       System.out.println(plan);
    }
}
