package Tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import Tests.blockworld.*;
import Tests.modelling.*;


@RunWith(Suite.class)
@Suite.SuiteClasses({VariableTest.class, BooleanVariableTest.class,DifferenceConstraintTest.class, ImplicationTest.class, 
                    BWBasicConstraintsBuilderTest.class,BWVariablesBuilderTest.class,MisplacedBlockTest.class, 
                    DataminingVariableBuilderTest.class, BWCroissanceConstraintsBuilderTest.class, 
                    BWRegularyConstraintsBuilderTest.class})


public class RunTests {
    
}
