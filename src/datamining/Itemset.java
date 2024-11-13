package datamining;

import java.util.Set;
import modelling.BooleanVariable;

/**
 * Represents an itemset in the context of association rule mining.
 * <p>
 * An itemset is a collection of items (Boolean variables) that appear together
 * in transactions.
 * Each itemset also has a frequency, which represents how often the itemset
 * appears in the database of transactions.
 * </p>
 */
public class Itemset {

    private Set<BooleanVariable> items; 
    private float frequency;

    /**
     * Constructs an Itemset with the given items and their frequency.
     * <p>
     * The frequency value must be between 0 and 1 (inclusive), representing the
     * proportion of transactions
     * that contain this itemset in the database.
     * </p>
     *
     * @param items     The set of items (Boolean variables) in this itemset.
     * @param frequency The frequency of this itemset in the database (must be
     *                  between 0 and 1).
     * @throws IllegalArgumentException If the frequency is not between 0 and 1.
     */
    public Itemset(Set<BooleanVariable> items, float frequency) {
        if (!(frequency <= 1 && frequency >= 0)) {
            throw new IllegalArgumentException("Item frequency must be between 0 and 1");
        }
        this.items = items;
        this.frequency = frequency;
    }

    /**
     * Gets the items in this itemset.
     * 
     * @return The set of items in this itemset.
     */
    public Set<BooleanVariable> getItems() {
        return items;
    }

    /**
     * Gets the frequency of this itemset in the database.
     * 
     * @return The frequency of this itemset.
     */
    public float getFrequency() {
        return frequency;
    }

    /**
     * Returns a string representation of this itemset.
     * <p>
     * The string representation includes the items in the itemset and its
     * frequency.
     * </p>
     *
     * @return A string describing the itemset and its frequency.
     */
    @Override
    public String toString() {
        return this.items + " de fréquence " + this.frequency + "\n";
    }
}
