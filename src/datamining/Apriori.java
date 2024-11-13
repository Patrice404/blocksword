package datamining;

import java.util.*;
import modelling.BooleanVariable;

/**
 * Implementation of the Apriori algorithm for mining frequent itemsets from a
 * Boolean database.
 * <p>
 * The Apriori algorithm is a classical method for frequent itemset mining. It
 * iteratively
 * identifies frequent itemsets in a Boolean database by generating candidate
 * itemsets,
 * checking their frequencies, and combining them to find higher-order frequent
 * itemsets.
 * The process stops when no more frequent itemsets can be found.
 * </p>
 */
public class Apriori extends AbstractItemsetMiner {

    /**
     * Constructs an Apriori miner with the specified Boolean database.
     *
     * @param database The Boolean database to be used for itemset mining.
     */
    public Apriori(BooleanDatabase database) {
        super(database);
    }

    /**
     * Extracts frequent itemsets from the Boolean database using the Apriori
     * algorithm.
     * <p>
     * The algorithm first finds frequent singleton itemsets (items that appear in
     * transactions).
     * It then iteratively generates candidate itemsets of increasing size, checks
     * their frequencies,
     * and retains those that meet the minimum frequency threshold. The process
     * continues until no more
     * frequent itemsets can be found.
     * </p>
     *
     * @param minimalFrequency The minimum frequency threshold for an itemset to be
     *                         considered frequent.
     * @return A set of frequent itemsets found in the database.
     */
    @Override
    public Set<Itemset> extract(float minimalFrequency) {
        List<SortedSet<BooleanVariable>> listLenght_k_Frequent = new ArrayList<>();
        Set<Itemset> itemsFrequent = new HashSet<>();
        Set<Itemset> frequentSingletons = this.frequentSingletons(minimalFrequency);
        itemsFrequent.addAll(frequentSingletons);
        for (Itemset itemset : frequentSingletons) {
            SortedSet<BooleanVariable> i = new TreeSet<>(AbstractItemsetMiner.COMPARATOR);
            i.addAll(itemset.getItems());
            listLenght_k_Frequent.add(i);
        }
        while (listLenght_k_Frequent.size() > 1) {
            List<SortedSet<BooleanVariable>> candidats = new ArrayList<>();

            for (SortedSet<BooleanVariable> item1 : listLenght_k_Frequent) {
                for (SortedSet<BooleanVariable> item2 : listLenght_k_Frequent) {
                    if (!item1.equals(item2)) {
                        SortedSet<BooleanVariable> combinaison = Apriori.combine(item1, item2);
                        if (combinaison != null) {
                            if (Apriori.allSubsetsFrequent(combinaison, listLenght_k_Frequent)) {
                                if (!candidats.contains(combinaison)) {
                                    candidats.add(combinaison);
                                }
                            }
                        }
                    }

                }
            }
            listLenght_k_Frequent.clear();

            // Calculer la fréquence des candidats et garder que les bons
            for (SortedSet<BooleanVariable> candidat : candidats) {
                float candidatFrequent = this.frequency(candidat);
                if (candidatFrequent >= minimalFrequency) {
                    itemsFrequent.add(new Itemset(candidat, candidatFrequent));
                    listLenght_k_Frequent.add(candidat);
                }
            }
        }
        return itemsFrequent;
    }

    /**
     * Creates a set of frequent singleton itemsets (single items that appear
     * frequently in the database).
     * 
     * @param minFrequency The minimum frequency threshold for an itemset to be
     *                     considered frequent.
     * @return A set of frequent singleton itemsets.
     */
    public Set<Itemset> frequentSingletons(float minFrequency) {
        if (!(minFrequency >= 0 && minFrequency <= 1)) {
            throw new IllegalArgumentException("The value of the frequency must be between 0 and 1");
        }
        Set<Itemset> itemsets = new HashSet<>();

        for (BooleanVariable item : this.database.getItems()) {
            Set<BooleanVariable> transaction = Collections.singleton(item);
            float transactionFrequency = this.frequency(transaction);
            if (transactionFrequency >= minFrequency) {
                itemsets.add(new Itemset(transaction, transactionFrequency));
            }
        }
        return itemsets;
    }

    /**
     * Combines two itemsets of the same size into a candidate itemset by joining
     * their elements.
     * <p>
     * This method combines two itemsets (if they have the same k-1 elements) to
     * form a new itemset of size k.
     * The last elements must be different to generate a valid combination.
     * </p>
     *
     * @param item1 The first itemset to combine.
     * @param item2 The second itemset to combine.
     * @return A combined itemset of size k, or null if the itemsets cannot be
     *         combined.
     */
    public static SortedSet<BooleanVariable> combine(SortedSet<BooleanVariable> item1,
            SortedSet<BooleanVariable> item2) {
        if (item1.size() != 0 && !item1.equals(item2) && item1.size() == item2.size()) {
            int length = item1.size();
            Iterator<BooleanVariable> iteratorItem1 = item1.iterator();
            Iterator<BooleanVariable> iteratorItem2 = item2.iterator();
            boolean sameElements = true;
            // On vérifie si les deux items ont les mêmes k-1 premiers élément
            for (int i = 1; i < length; i++) {
                BooleanVariable v1 = iteratorItem1.next();
                BooleanVariable v2 = iteratorItem2.next();
                if (!v1.getName().equals(v2.getName())) {
                    sameElements = false;
                    return null;
                }
            }
            // Si oui sont-ils différents sur le dernier élément ?
            if (sameElements && !item1.last().equals(item2.last())) {
                SortedSet<BooleanVariable> combinaison = new TreeSet<>(AbstractItemsetMiner.COMPARATOR);
                combinaison.addAll(item1);
                combinaison.add(item2.last());
                return combinaison;
            }
            return null;
        }
        return null;
    }

    /**
     * Checks if all subsets of an itemset are frequent.
     * <p>
     * For an itemset to be frequent, all of its subsets must also be frequent.
     * </p>
     *
     * @param item            The itemset to check.
     * @param itemsCollection The collection of frequent itemsets.
     * @return True if all subsets of the itemset are frequent, false otherwise.
     */
    public static boolean allSubsetsFrequent(Set<BooleanVariable> item,
            Collection<SortedSet<BooleanVariable>> itemsCollection) {
        for (BooleanVariable i : item) {
            SortedSet<BooleanVariable> copy = new TreeSet<>(AbstractItemsetMiner.COMPARATOR);
            copy.addAll(item);
            copy.remove(i);
            if (!itemsCollection.contains(copy)) {
                return false;
            }
        }
        return true;
    }
}
