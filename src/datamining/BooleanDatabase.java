package datamining;

import java.util.*;

import modelling.*;

/**
 * Represents a Boolean database for association rule mining.
 * <p>
 * A Boolean database is a collection of transactions, where each transaction is a set of items represented by {@link BooleanVariable}.
 * The database also contains a collection of all possible items that can appear in the transactions.
 * This class provides methods to access the items, transactions, and to add new transactions.
 * </p>
 */
public class BooleanDatabase {

    private Set<BooleanVariable> items;
    private List<Set<BooleanVariable>> transactions;

    /**
     * Constructs a BooleanDatabase with a specified set of items.
     * <p>
     * The database will store the set of items and maintain a list of transactions.
     * </p>
     *
     * @param items A set of Boolean variables representing the items in the database.
     */
    public BooleanDatabase(Set<BooleanVariable> items) {
        this.items = items;
        this.transactions = new ArrayList<>();
    }

    /**
     * Returns the set of items in the database.
     * <p>
     * These items represent the potential items that may appear in the transactions.
     * </p>
     *
     * @return The set of Boolean variables representing the items in the database.
     */
    public Set<BooleanVariable> getItems() {
        return items;
    }

    /**
     * Returns the list of transactions in the database.
     * <p>
     * Each transaction is a set of Boolean variables that appear in a particular transaction.
     * </p>
     *
     * @return The list of transactions, where each transaction is a set of Boolean variables.
     */
    public List<Set<BooleanVariable>> getTransactions() {
        return transactions;
    }

    /**
     * Adds a new transaction to the database.
     * <p>
     * This method allows you to add a set of Boolean variables representing a new transaction
     * to the database. Each transaction is stored as a set of items that occurred together.
     * </p>
     *
     * @param transaction A set of Boolean variables representing the items in the transaction.
     */
    public void add(Set<BooleanVariable> transaction){
        this.transactions.add(transaction);
    }
}
