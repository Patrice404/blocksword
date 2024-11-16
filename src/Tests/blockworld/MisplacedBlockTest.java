package Tests.blockworld;

import modelling.*;
import planning.BasicGoal;

import org.junit.Before;
import org.junit.Test;

import Tests.utils.Function;
import blocksworld.MisplacedBlockHeuristic;

import java.util.*;

import static org.junit.Assert.*;

public class MisplacedBlockTest {

    private MisplacedBlockHeuristic heuristic;
    private Map<Variable, Object> state1;
    private Map<Variable, Object> state2;
    private Map<Variable, Object> state3;

    @Before
    public void setUp() {
        // State [[0,2,1],[4,3],[]]
        List<List<Integer>> liste = new ArrayList<>();
        List<Integer> stack1 = new ArrayList<>();
        stack1.add(0);
        stack1.add(2);
        stack1.add(1);

        List<Integer> stack2 = new ArrayList<>();
        stack2.add(4);
        stack2.add(3);

        List<Integer> stack3 = new ArrayList<>();

        liste.add(stack1);
        liste.add(stack2);
        liste.add(stack3);
        state1 = Function.listToBwState(liste, 5);

        // State [[0,1,2],[4,3],[]]
        List<List<Integer>> liste2 = new ArrayList<>();
        List<Integer> pile1 = new ArrayList<>();
        pile1.add(0);
        pile1.add(1);
        pile1.add(2);
        liste2.add(pile1);
        liste2.add(stack2);
        liste2.add(stack3);
        state2 = Function.listToBwState(liste2, 5);

        // State [[0,1,2],[3,4],[]]
        List<List<Integer>> liste3 = new ArrayList<>();
        List<Integer> pile2 = new ArrayList<>();
        pile2.add(3);
        pile2.add(4);

        liste3.add(pile1);
        liste3.add(pile2);
        liste3.add(stack3);

        state3 = Function.listToBwState(liste3, 5);

        heuristic = new MisplacedBlockHeuristic(new BasicGoal(state1));
    }

    @Test
    public void testEstimate() {
        assertTrue("L'heuristic estime mal le nombre de bloc mal placé", heuristic.estimate(state3) == 4);
        assertTrue("L'heuristic estime mal le nombre de bloc mal placé", heuristic.estimate(state2) == 2);
    }
}
