package datamining;

import java.util.*;

import modelling.BooleanVariable;

/**
 * Abstract class for mining itemsets from a Boolean database.
 * <p>
 * This class provides a common method for calculating the frequency of itemsets
 * in the given Boolean database. It serves as the base class for specific
 * implementations
 * of itemset miners.
 * </p>
 */
public abstract class AbstractItemsetMiner implements ItemsetMiner {

    /** Comparator used to compare Boolean variables by their name */
    public static final Comparator<BooleanVariable> COMPARATOR = (var1, var2) -> var1.getName()
            .compareTo(var2.getName());

    /** The Boolean database used for mining itemsets */
    protected BooleanDatabase database;

    /**
     * Constructs a new AbstractItemsetMiner with the specified Boolean database.
     *
     * @param database The Boolean database containing the transactions used for
     *                 mining itemsets.
     */
    public AbstractItemsetMiner(BooleanDatabase database) {
        this.database = database;
    }

    /**
     * Calculates the frequency of a given itemset in the Boolean database.
     * <p>
     * The frequency is defined as the proportion of transactions in the database
     * in which the itemset appears. If the database is empty, the frequency is
     * returned as 0.
     * </p>
     *
     * @param items The set of Boolean variables representing the itemset whose
     *              frequency is to be calculated.
     * @return The frequency of the itemset, expressed as a fraction of the total
     *         number of transactions in the database.
     */
    public float frequency(Set<BooleanVariable> items) {
        List<Set<BooleanVariable>> dbTransactions = this.database.getTransactions();
        int dbSize = dbTransactions.size();
        if (dbSize == 0) {
            return 0;
        }
        int occurrence = 0;
        for (Set<BooleanVariable> transaction : dbTransactions) {
            if (transaction.containsAll(items)) {
                occurrence++;
            }
        }
        return (float) occurrence / dbSize;
    }

    /**
     * Returns the Boolean database used for mining itemsets.
     *
     * @return The Boolean database.
     */
    @Override
    public BooleanDatabase getDatabase() {
        return this.database;
    }
}
