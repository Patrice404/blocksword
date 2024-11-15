package blocksworld.demo;

import java.util.Set;

import blocksworld.BWCroissanceConstraintsBuilder;
import blocksworld.BWRegularyConstraintsBuilder;
import modelling.Constraint;

public class Test {
    public static void main(String[] args) {
        BWRegularyConstraintsBuilder b = new BWRegularyConstraintsBuilder(3, 2);
        Set<Constraint> c = b.getConstraints();
        System.out.println(c);
    }
}
