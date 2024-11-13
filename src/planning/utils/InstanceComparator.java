package planning.utils;

import java.util.Comparator;

/**
 * Comparator for comparing two instances of {@link StateWithDistance} based on
 * their cost.
 * 
 * This comparator is typically used to order instances of `StateWithDistance`
 * in a priority queue or
 * sorted list, favoring states with lower costs. It compares two
 * `StateWithDistance` objects and returns
 * an integer indicating the relative ordering:
 * - Returns a negative integer if the first state has a lower cost.
 * - Returns zero if the two states have equal costs.
 * - Returns a positive integer if the first state has a higher cost.
 * 
 * @see StateWithDistance
 */
public class InstanceComparator implements Comparator<StateWithDistance> {

    /**
     * Compares two {@link StateWithDistance} instances based on their cost.
     * 
     * @param arg0 The first state to compare.
     * @param arg1 The second state to compare.
     * @return -1 if {@code arg0} has a lower cost than {@code arg1},
     *         0 if they have equal costs, or 1 if {@code arg0} has a higher cost
     *         than {@code arg1}.
     */
    @Override
    public int compare(StateWithDistance arg0, StateWithDistance arg1) {
        if (arg0.getCost() < arg1.getCost()) {
            return -1;
        } else if (arg0.getCost() == arg1.getCost()) {
            return 0;
        } else {
            return 1;
        }
    }

}