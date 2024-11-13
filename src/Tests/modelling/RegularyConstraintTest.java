package Tests.modelling;

import static org.junit.Assert.*;
import java.util.*;
import org.junit.Before;
import org.junit.Test;
import Tests.utils.Function;
import modelling.*;

public class RegularyConstraintTest {
    private Variable on1;
    private Variable on2;
    private Variable on3;
    private Variable fixed1;
    private int differentExpected;

    Set<Object> domain1;
    Set<Object> domain2;
    private RegularyConstraint constraint1;
    private RegularyConstraint constraint2;
    private Set<Variable> scope1;
    private Set<Variable> scope2;

    @Before
    public void setUp() {
        Set<Object> domain1 = Function.calculDomain(1, 4, 2);// domain de la variable on qui a comme nom 1
        Set<Object> domain2 = Function.calculDomain(2, 4, 2);
        Set<Object> domain3 = Function.calculDomain(3, 4, 2);

        on1 = new Variable(1, domain1);
        on2 = new Variable(2, domain2);
        on3 = new Variable(3, domain3);

        fixed1 = new BooleanVariable(1);

        constraint1 = new RegularyConstraint(on1, on2,1);
        constraint2 = new RegularyConstraint(on2, on3,0);

        scope1 = new HashSet<>();
        scope1.add(on1);
        scope1.add(on2);

        scope2 = new HashSet<>();
        scope2.add(on2);
        scope2.add(on3);

    }

    @Test
    public void testConstructor() {
        RegularyConstraint constraint = new RegularyConstraint(on1, on2,1);

        assertEquals("Le constructeur n'affecte pas bien les variables données en argument", scope1,
                constraint.getScope());

        assertThrows(
                "Une exception de type IllegalArgumentException devrait être lancée lorsque le deuxième argument n'est pas la variable 'On' d'un bloc.",
                IllegalArgumentException.class,
                () -> new RegularyConstraint(on1, fixed1,differentExpected));

        assertThrows(
                "Une exception de type IllegalArgumentException devrait être lancée lorsque le premier argument n'est pas la variable 'On' d'un bloc.",
                IllegalArgumentException.class,
                () -> new RegularyConstraint(fixed1, on2,differentExpected));

        assertThrows(
                "Une exception de type IllegalArgumentException devrait être lancée lorsque l'une des variables est null.",
                IllegalArgumentException.class,
                () -> new RegularyConstraint(on1, null,differentExpected));

        assertThrows(
                "Une exception de type IllegalArgumentException devrait être lancée lorsque l'une des variables est null.",
                IllegalArgumentException.class,
                () -> new RegularyConstraint(null, on2,differentExpected));

        assertThrows(
                "Une exception de type IllegalArgumentException devrait être lancée lorsque les deux variables sont les memes",
                IllegalArgumentException.class,
                () -> new RegularyConstraint(on1, on1,differentExpected));
    }

    @Test
        public void testGetScope() {
                Set<Variable> scope = constraint1.getScope();

                assertTrue(
                                "La méthode getScope ne retourne pas le bon ensemble. Elle renvoie " + scope
                                                + " pour une contrainte portant sur : " + on1 + " et "
                                                + on2,
                                scope.equals(scope1));
                scope = constraint2.getScope();
                assertTrue(
                                "La méthode getScope ne retourne pas le bon ensemble. Elle renvoie " + scope
                                                + " pour une contrainte portant sur : " + on2 + " et "
                                                + on3,
                                scope.equals(scope2));
        }

        @Test
        public void testIsSatisfiedBy() {
                Map<Variable, Object> instanciation = new HashMap<>();
                instanciation.put(on1, 2);
                instanciation.put(on2, 1);
                assertTrue("La méthode renvoie false pour la contrainte " + constraint1 + " avec l'instanciation "
                                + instanciation, constraint1.isSatisfiedBy(instanciation));

                instanciation.clear();
                instanciation.put(on1, 2);
                instanciation.put(on1, 2);
                
                assertTrue("La méthode renvoie false pour la contrainte " + constraint1 + " avec l'instanciation "
                                + instanciation, constraint1.isSatisfiedBy(instanciation));

                instanciation.clear();
                instanciation.put(on2, 3);
                instanciation.put(on3, 0);

                assertFalse("La méthode renvoie true pour la contrainte " + constraint2 + " avec l'instanciation "
                                + instanciation, constraint2.isSatisfiedBy(instanciation));

        }
        @Test
        public void testToString() {
                String expectedString = "Contrainte de type Regulary entre 1 et 2 avec l'ecart : 1";
                String actualString = constraint1.toString();
                assertEquals("La méthode toString() ne renvoie pas la chaîne attendue. Résultat obtenu: "
                                + actualString,
                                expectedString, actualString);

                expectedString = "Contrainte de type Regulary entre 2 et 3 avec l'ecart : 0";
                actualString = constraint2.toString();
                assertEquals("La méthode toString() ne renvoie pas la chaîne attendue. Résultat obtenu: "
                                + actualString,
                                expectedString, actualString);
        }




}
