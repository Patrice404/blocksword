package datamining;

import java.util.Set;

/**
 * Interface for mining itemsets from a Boolean database.
 * <p>
 * An itemset miner is responsible for extracting frequent itemsets from a
 * Boolean database.
 * A frequent itemset is a collection of items that appears together in a given
 * percentage of transactions.
 * </p>
 */
public interface ItemsetMiner {

    /**
     * Gets the Boolean database used by this itemset miner.
     *
     * @return The Boolean database containing the transactions.
     */
    public BooleanDatabase getDatabase();

    /**
     * Extracts frequent itemsets from the database.
     * <p>
     * Frequent itemsets are sets of items that appear together in a minimum number
     * of transactions.
     * The threshold for considering an itemset as frequent is given by the
     * minimalFrequency parameter.
     * </p>
     *
     * @param minFrequency The minimum frequency threshold for an itemset to be
     *                         considered frequent.
     *                         It must be a value between 0 and 1, representing the
     *                         proportion of transactions.
     * @return A set of frequent itemsets with frequency greater than or equal to
     *         the minimalFrequency.
     */
    public Set<Itemset> extract(float minFrequency);
}
