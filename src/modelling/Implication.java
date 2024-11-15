package modelling;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Implication implements Constraint {
    private Variable v1, v2;
    private Set<Object> s1, s2;

    public Implication(Variable v1, Set<Object> s1, Variable v2, Set<Object> s2) {
        if(!v1.getDomain().containsAll(s1) || !v2.getDomain().containsAll(s2)){
            throw new IllegalArgumentException("Sub domain must be includ in variables doamin");
        }else{
            this.v1 = v1;
            this.s1 = s1;
            this.v2 = v2;
            this.s2 = s2;
        }   
    }

    @Override
    public Set<Variable> getScope() {
        Set<Variable> variables = new HashSet<>();
        variables.add(this.v1);
        variables.add(this.v2);
        return variables;
    }

    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> instanciation) throws IllegalArgumentException {
        Object valueOfV1 = instanciation.get(this.v1);
        Object valueOfV2 = instanciation.get(this.v2);
        if(valueOfV1 == null || valueOfV2==null){
            throw new IllegalArgumentException("Bad instanciation");
        }
        if(this.s1.contains(valueOfV1)){
            if(this.s2.contains(valueOfV2)){
                return true;
            }
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Contrainte d'implication portant sur " + v1 + " " +" S1 = " +s1 +" et " + v2  +" S2 = "+ s2 +"\n";
    }
}
