package blocksworld.demo;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import blocksworld.*;
import blocksworld.utils.Function;
import bwgenerator.BWGenerator;
import datamining.*;
import modelling.BooleanVariable;

public class Datamining {
    public static void main(String[] args) {
        int n = 100000;
        BWGenerator bwGenerator = new BWGenerator(20,6);
        DataminingVariableBuilder dataminingVariableBuilder = new DataminingVariableBuilder(20, 6);
         Map<BooleanVariable, String>  mapping = dataminingVariableBuilder.getMapping();
        BooleanDatabase db = new BooleanDatabase(dataminingVariableBuilder.getVariables());
        Random random = new Random();
        List<List<Integer>> stateListe = null;
        for (int i = 0; i < n; i++) {
            stateListe= bwGenerator.generate(random);
            Set<BooleanVariable> state = dataminingVariableBuilder.getInstance(stateListe);
            db.add(state);
        }
        Apriori apriori = new Apriori(db);
        float minFrequency = 0.66f;
        float minConfiance = 0.95f;
        Set<Itemset> itemFrequent = apriori.extract(minFrequency);
        System.out.println("MOTIF DE FREQUENCE += 2/3");
        Function.showFrequentItem(itemFrequent, mapping);

        System.out.println("REGLE D'ASSOCIATION DE FREQUENCE += 2/3 ET DE CONFIANCE += 95/100");
        BruteForceAssociationRuleMiner bruteForceAssociationRuleMiner = new BruteForceAssociationRuleMiner(db);
        Set<AssociationRule> rules = bruteForceAssociationRuleMiner.extract(minFrequency, minConfiance);
        Function.showValideRules(rules, mapping);
       
    }
}
