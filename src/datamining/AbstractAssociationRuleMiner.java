package datamining;

import java.util.*;
import modelling.BooleanVariable;

/**
 * Abstract class for mining association rules from a Boolean database.
 * <p>
 * This class provides common methods for association rule mining algorithms,
 * including the calculation of itemset frequencies and rule confidence.
 * It serves as the base class for specific implementations of association rule
 * miners.
 * </p>
 */
public abstract class AbstractAssociationRuleMiner implements AssociationRuleMiner {

    /** The Boolean database used for mining association rules */
    protected BooleanDatabase database;

    /**
     * Constructs a new AbstractAssociationRuleMiner with the specified Boolean
     * database.
     *
     * @param database The Boolean database containing the transactions used for
     *                 mining rules.
     */
    public AbstractAssociationRuleMiner(BooleanDatabase database) {
        this.database = database;
    }

    /**
     * Returns the Boolean database used for mining association rules.
     *
     * @return The Boolean database.
     */
    @Override
    public BooleanDatabase getDatabase() {
        return this.database;
    }

    /**
     * Calculates the frequency of a given itemset in the provided set of itemsets.
     * <p>
     * The frequency is defined as the proportion of transactions in which the
     * itemset appears.
     * </p>
     *
     * @param items    The set of Boolean variables representing the itemset whose
     *                 frequency is to be calculated.
     * @param itemsets The collection of itemsets from which the frequency is
     *                 computed.
     * @return The frequency of the itemset, or -1 if the itemset is not found.
     */
    public static float frequency(Set<BooleanVariable> items, Set<Itemset> itemsets) {
        for (Itemset itemset : itemsets) {
            if (itemset.getItems().equals(items)) {
                return itemset.getFrequency();
            }
        }
        return -1;
    }

    /**
     * Calculates the confidence of an association rule.
     * <p>
     * Confidence is defined as the ratio of the frequency of the union of the
     * premise and conclusion
     * to the frequency of the premise alone. It indicates the strength of the rule.
     * </p>
     *
     * @param premisse   The set of Boolean variables representing the premise
     *                   (left-hand side) of the rule.
     * @param conclusion The set of Boolean variables representing the conclusion
     *                   (right-hand side) of the rule.
     * @param itemsets   The collection of itemsets used to calculate the
     *                   confidence.
     * @return The confidence of the association rule.
     */
    public static float confidence(Set<BooleanVariable> premisse, Set<BooleanVariable> conclusion,
            Set<Itemset> itemsets) {
        Set<BooleanVariable> union = new HashSet<>();
        union.addAll(premisse);
        union.addAll(conclusion);
        float unionFrequency = AbstractAssociationRuleMiner.frequency(union, itemsets);
        float premisseFrequency = AbstractAssociationRuleMiner.frequency(premisse, itemsets);
        return unionFrequency / premisseFrequency;
    }
}
