package blocksworld.demo;

import java.util.*;

import blocksworld.*;
import blocksworld.utils.Function;
import modelling.*;
import planning.*;

public class Planning1 {
    public static void main(String[] args) {
        // Init [[0,2,1],[4,3],[]]
        System.out.println("Init :  [[0,2,1],[4,3],[]]");

        List<List<Integer>> iliste = new ArrayList<>();
        List<Integer> istack1 = new ArrayList<>();
        istack1.add(0);
        istack1.add(2);
        istack1.add(1);

        List<Integer> istack2 = new ArrayList<>();
        istack2.add(4);
        istack2.add(3);

        List<Integer> istack3 = new ArrayList<>();

        iliste.add(istack1);
        iliste.add(istack2);
        iliste.add(istack3);

        Map<Variable, Object> initState = Function.listToBwState(iliste, 5);
        Function.afficher(initState, 5, "Init state");

        // Goal [[4,2],[1],[3,0]]
        System.out.println("Goal :  [[4,2],[1],[3,0]]");
        List<List<Integer>> gliste = new ArrayList<>();
        List<Integer> gstack1 = new ArrayList<>();
        gstack1.add(4);
        gstack1.add(2);

        List<Integer> gstack2 = new ArrayList<>();
        gstack2.add(1);

        List<Integer> gstack3 = new ArrayList<>();
        gstack3.add(3);
        gstack3.add(0);

        gliste.add(gstack1);
        gliste.add(gstack2);
        gliste.add(gstack3);

        Map<Variable, Object> goalState = Function.listToBwState(gliste, 5);
        Function.afficher(goalState, 5, "Goal state");

        BasicGoal goal = new BasicGoal(goalState);

        // Obtenir toutes les actions possibles
        BWActionsBuilder bwActionsBuilder = new BWActionsBuilder(5, 3);
        Set<Action> actions = bwActionsBuilder.getActions();

        DFSPlanner dfsPlanner = new DFSPlanner(initState, actions, goal);
        dfsPlanner.activateNodeCount(true);
        long start = System.currentTimeMillis();
        List<Action> plan = dfsPlanner.plan();
        long fin = System.currentTimeMillis();
        System.out.println("🔍 Résultat DFSPlanner");
        System.out.println("⏱️  Temps d'exécution : " + (fin - start) + "ms");
        System.out.println("🚀 Nombre d'actions dans le plan : " + plan.size());
        System.out.println("⏳ Nombre de noeuds explorés : " + dfsPlanner.getNbNoeudExplore() + "\n");

        BFSPlanner bfsPlanner = new BFSPlanner(initState, actions, goal);
        bfsPlanner.activateNodeCount(true);
        start = System.currentTimeMillis();
        plan = bfsPlanner.plan();
        fin = System.currentTimeMillis();
        System.out.println("🔍 Résultat BFSPlanner");
        System.out.println("⏱️  Temps d'exécution : " + (fin - start) + "ms");
        System.out.println("🚀 Nombre d'actions dans le plan : " + plan.size());
        System.out.println("⏳ Nombre de noeuds explorés : " + bfsPlanner.getNbNoeudExplore() + "\n");

        DijkstraPlanner dijkstraPlanner = new DijkstraPlanner(initState, actions, goal);
        dijkstraPlanner.activateNodeCount(true);
        start = System.currentTimeMillis();
        plan = dijkstraPlanner.plan();
        fin = System.currentTimeMillis();
        System.out.println("🔍 Résultat DijkstraPlanner");
        System.out.println("⏱️  Temps d'exécution : " + (fin - start) + "ms");
        System.out.println("🚀 Nombre d'actions dans le plan : " + plan.size());
        System.out.println("⏳ Nombre de noeuds explorés : " + dijkstraPlanner.getNbNoeudExplore() + "\n");

        MisplacedBlockHeuristic misplacedBlockHeurostic = new MisplacedBlockHeuristic(goal);
        WellplacedBlockHeuristic wellplacedBlockHeuristic2 = new WellplacedBlockHeuristic(goal);
        SimilarityHeuristic similarityHeuristic = new SimilarityHeuristic(goalState, 1);

        AStarPlanner aStarPlanner2 = new AStarPlanner(initState, actions, goal,misplacedBlockHeurostic);
        aStarPlanner2.activateNodeCount(true);
        start = System.currentTimeMillis();
        plan = aStarPlanner2.plan();
        fin = System.currentTimeMillis();
        System.out.println("🔍 Résultat AStarPlanner");
        System.out.println("⏱️  Temps d'exécution : " + (fin - start) + "ms");
        System.out.println("🚀 Nombre d'actions dans le plan : " + plan.size());
        System.out.println("⏳ Nombre de noeuds explorés : " +
                aStarPlanner2.getNbNoeudExplore() + "\n");
        Function.displayPlan(initState, 5, plan);
        System.out.println(plan);

    }
}
