package datamining;

import java.util.Set;
import modelling.BooleanVariable;

/**
 * Represents an association rule in the context of association rule mining.
 * <p>
 * An association rule is a relationship of the form:
 * {Premise} => {Conclusion}, where:
 * <ul>
 * <li>Premise is the itemset that is the "if" part of the rule.</li>
 * <li>Conclusion is the itemset that is the "then" part of the rule.</li>
 * </ul>
 * The rule also includes the frequency and confidence of the rule:
 * <ul>
 * <li>Frequency is the occurrence of the rule in the dataset.</li>
 * <li>Confidence is the likelihood of the conclusion given the premise.</li>
 * </ul>
 * </p>
 */
public class AssociationRule {
    private Set<BooleanVariable> premise;
    private Set<BooleanVariable> conclusion;
    private float frequency;
    private float confidence;

    /**
     * Constructs an association rule with the specified premise, conclusion,
     * frequency, and confidence.
     *
     * @param premise    The premise of the rule (left-hand side).
     * @param conclusion The conclusion of the rule (right-hand side).
     * @param frequency  The frequency of the rule in the database.
     * @param confidence The confidence of the rule (likelihood of conclusion given
     *                   premise).
     */
    public AssociationRule(Set<BooleanVariable> premise, Set<BooleanVariable> conclusion, float frequency,
            float confidence) {
        this.premise = premise;
        this.conclusion = conclusion;
        this.frequency = frequency;
        this.confidence = confidence;
    }

    /**
     * Returns the premise (left-hand side) of the rule.
     *
     * @return The premise of the rule.
     */
    public Set<BooleanVariable> getPremise() {
        return premise;
    }

    /**
     * Returns the conclusion (right-hand side) of the rule.
     *
     * @return The conclusion of the rule.
     */
    public Set<BooleanVariable> getConclusion() {
        return this.conclusion;
    }

    /**
     * Returns the frequency of the rule in the dataset.
     *
     * @return The frequency of the rule.
     */
    public float getFrequency() {
        return this.frequency;
    }

    /**
     * Returns the confidence of the rule (probability of conclusion given premise).
     *
     * @return The confidence of the rule.
     */
    public float getConfidence() {
        return this.confidence;
    }

    /**
     * Returns a string representation of the association rule.
     * <p>
     * The string includes the premise, conclusion, frequency, and confidence of the
     * rule.
     * </p>
     *
     * @return A string representation of the association rule.
     */
    @Override
    public String toString() {
        return "Premise = " + premise + " Conclusion = " + conclusion + " Frequency = " + frequency + " Confidence = "
                + confidence + "\n";
    }
}
