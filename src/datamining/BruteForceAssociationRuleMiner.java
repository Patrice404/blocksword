package datamining;

import java.util.*;

import modelling.BooleanVariable;

/**
 * A brute-force implementation of an association rule miner that generates
 * rules based on frequent itemsets.
 * <p>
 * This class performs association rule mining using the Apriori algorithm to
 * find frequent itemsets.
 * It then generates all possible association rules from those itemsets using a
 * brute-force approach.
 * For each frequent itemset, the class calculates confidence for all possible
 * premises and conclusions and
 * returns the valid rules that meet the minimum confidence threshold.
 * </p>
 */
public class BruteForceAssociationRuleMiner extends AbstractAssociationRuleMiner {

    /**
     * Constructs a BruteForceAssociationRuleMiner with the given database.
     * <p>
     * This constructor initializes the brute-force miner with the provided
     * {@link BooleanDatabase}.
     * </p>
     *
     * @param database The BooleanDatabase containing the items and transactions for
     *                 mining association rules.
     */
    public BruteForceAssociationRuleMiner(BooleanDatabase database) {
        super(database);
    }

    /**
     * Extracts association rules from the frequent itemsets using brute-force
     * enumeration.
     * <p>
     * For each frequent itemset, the method generates all possible candidate
     * premises and computes the confidence
     * of the corresponding association rules. Only rules with confidence greater
     * than or equal to the given threshold
     * are returned.
     * </p>
     *
     * @param minFrequency The minimum frequency threshold for itemsets to be
     *                     considered frequent.
     * @param minConfiance The minimum confidence threshold for rules to be
     *                     considered valid.
     * @return A set of valid association rules that meet the minimum frequency and
     *         confidence criteria.
     */
    @Override
    public Set<AssociationRule> extract(float minFrequency, float minConfiance) {
        Set<AssociationRule> rules = new HashSet<>();
        Apriori apriori = new Apriori(this.database);
        Set<Itemset> frequentItems = apriori.extract(minFrequency);
        for (Itemset itemset : frequentItems) {
            Set<BooleanVariable> domain = new HashSet<>();
            domain.addAll(itemset.getItems());
            Set<Set<BooleanVariable>> subDomains = BruteForceAssociationRuleMiner.allCandidatePremises(domain);
            for (Set<BooleanVariable> y : subDomains) {
                // On calcul x = domain privé de y
                SortedSet<BooleanVariable> x = new TreeSet<>(AbstractItemsetMiner.COMPARATOR);
                x.addAll(domain);
                x.removeAll(y);
                // on veut calculer la confiance de la règle x --> y
                // noter que x union y donne domain
                float xFrequency = AbstractAssociationRuleMiner.frequency(x, frequentItems);
                float itemFrequency = itemset.getFrequency();
                float ruleConfiance = itemFrequency / xFrequency;
                if (ruleConfiance >= minConfiance) {
                    AssociationRule rule = new AssociationRule(x, y, itemFrequency, ruleConfiance);
                    rules.add(rule);
                }
            }
        }
        return rules;
    }

    /**
     * Generates all possible candidate premises from the given domain.
     * <p>
     * This method iterates over all possible subsets of the domain and returns the
     * set of all candidate premises.
     * The candidate premises are all subsets of the domain with sizes from 1 to the
     * size of the domain minus 1.
     * </p>
     *
     * @param domain The set of items representing the domain.
     * @return A set of all possible candidate premises that are subsets of the
     *         domain.
     */
    public static Set<Set<BooleanVariable>> allCandidatePremises(Set<BooleanVariable> domain) {
        if (domain.size() == 1 || domain.size() == 0) {
            return new HashSet<>();
        }
        Set<Set<BooleanVariable>> subDomains = new HashSet<>();
        Set<SortedSet<BooleanVariable>> domainDeNiveauK = new HashSet<>();
        Set<SortedSet<BooleanVariable>> domainDeNiveauSuivant = new HashSet<>();

        // Calcule des subDomain de taille 1
        for (BooleanVariable item : domain) {
            SortedSet<BooleanVariable> i = new TreeSet<>(AbstractItemsetMiner.COMPARATOR);
            i.add(item);
            domainDeNiveauK.add(i);
        }
        subDomains.addAll(domainDeNiveauK);
        // Calcule des subDomain de niveau 2 et +
        while (domainDeNiveauK.size() > 1) {
            for (SortedSet<BooleanVariable> item1 : domainDeNiveauK) {
                for (SortedSet<BooleanVariable> item2 : domainDeNiveauK) {
                    if (item1 != null && item2 != null && !item1.equals(item2)) {
                        SortedSet<BooleanVariable> combinaison = Apriori.combine(item1, item2);
                        if (combinaison != null && !combinaison.equals(domain)
                                && !domainDeNiveauSuivant.contains(combinaison)) {
                            domainDeNiveauSuivant.add(combinaison);
                        }
                    }
                }
            }
            // Fin de calcul des subDomains de niveau suivant
            // On met tous les domains calculés dans subDomains
            // domainDeNiveauK devient les domains calculés et on reprend le calcul des
            // domaines de niveau suivant

            subDomains.addAll(domainDeNiveauSuivant);
            domainDeNiveauK.clear();
            domainDeNiveauK.addAll(domainDeNiveauSuivant);
            domainDeNiveauSuivant.clear();
        }
        return subDomains;
    }

}
