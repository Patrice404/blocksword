package datamining;

import java.util.Set;

/**
 * Interface for association rule mining algorithms.
 * <p>
 * Association rule mining is a technique used to discover interesting
 * relationships (rules) between items in large datasets.
 * This interface defines the basic structure for algorithms that extract
 * association rules from a {@link BooleanDatabase}.
 * </p>
 */
public interface AssociationRuleMiner {

    /**
     * Returns the {@link BooleanDatabase} associated with this miner.
     * <p>
     * A Boolean database contains transactions, each consisting of a set of items
     * represented by {@link BooleanVariable}.
     * This method allows access to the dataset being mined for association rules.
     * </p>
     *
     * @return The Boolean database being used by the miner.
     */
    public BooleanDatabase getDatabase();

    /**
     * Extracts a set of association rules from the database.
     * <p>
     * This method generates association rules where each rule has a premise and a
     * conclusion, and both frequency and confidence values.
     * The rules are filtered by a minimum frequency and a minimum confidence
     * threshold.
     * </p>
     *
     * @param minFrequency  The minimum frequency threshold for the rules to be
     *                      considered.
     * @param minConfidence The minimum confidence threshold for the rules to be
     *                      considered.
     * @return A set of {@link AssociationRule} that satisfy the given frequency and
     *         confidence thresholds.
     */
    public Set<AssociationRule> extract(float minFrequency, float minConfidence);
}
