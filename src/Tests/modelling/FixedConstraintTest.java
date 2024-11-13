package Tests.modelling;

import modelling.Variable;
import modelling.BooleanVariable;
import modelling.FixedConstraint;

import org.junit.Before;
import org.junit.Test;

import Tests.utils.Function;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class FixedConstraintTest {

        private Variable on1;
        private Variable on2;
        private Variable fixed1;
        private Variable fixed2;
        Set<Object> domain1;
        Set<Object> domain2;
        private FixedConstraint constraint1;
        private FixedConstraint constraint2;
        private Set<Variable> scope1;
        private Set<Variable> scope2;

        @Before
        public void setUp() {
                Set<Object> domain1 = Function.calculDomain(1, 4, 2);
                Set<Object> domain2 = Function.calculDomain(2, 4, 2);

                on1 = new Variable(1, domain1);
                fixed1 = new BooleanVariable(1);

                on2 = new Variable(2, domain2);
                fixed2 = new BooleanVariable(2);

                constraint1 = new FixedConstraint(on1, fixed2);
                constraint2 = new FixedConstraint(on2, fixed1);

                scope1 = new HashSet<>();
                scope1.add(on1);
                scope1.add(fixed2);

                scope2 = new HashSet<>();
                scope2.add(on2);
                scope2.add(fixed1);

        }

        @Test
        public void testConstructor() {
                FixedConstraint constraint = new FixedConstraint(on1, fixed2);

                assertEquals("Le constructeur n'affecte pas bien les variables données en argument", scope1,
                                constraint.getScope());

                assertThrows(
                                "Une exception de type IllegalArgumentException devrait être lancée lorsque le deuxième argument n'est pas la variable 'fixed' d'un bloc.",
                                IllegalArgumentException.class,
                                () -> new FixedConstraint(on1, on1));

                assertThrows(
                                "Une exception de type IllegalArgumentException devrait être lancée lorsque lorsque le premier argument n'est pas la variable 'On' d'un bloc.",
                                IllegalArgumentException.class,
                                () -> new FixedConstraint(fixed1, fixed2));

                assertThrows(
                                "Une exception de type IllegalArgumentException devrait être lancée lorsque l'un des variables est null.",
                                IllegalArgumentException.class,
                                () -> new FixedConstraint(on1, null));

                assertThrows(
                                "Une exception de type IllegalArgumentException devrait être lancée lorsque l'un des variables est null.",
                                IllegalArgumentException.class,
                                () -> new FixedConstraint(null, fixed1));

                assertThrows(
                                "Une exception de type IllegalArgumentException devrait être lancée lorsque la première variable n'est pas une variable 'On' d'un bloc.",
                                IllegalArgumentException.class,
                                () -> new FixedConstraint(fixed1, fixed2));

                assertThrows(
                                "Une exception de type IllegalArgumentException devrait être lancée lorsque la deuxième variable n'est pas une variable fixed d'un bloc.",
                                IllegalArgumentException.class,
                                () -> new FixedConstraint(on1, on1));

                assertThrows(
                                "Une exception de type IllegalArgumentException devrait être lancée lorsque les deux variables ont le même nom",
                                IllegalArgumentException.class,
                                () -> new FixedConstraint(on1, fixed1));
        }

        @Test
        public void testGetScope() {
                Set<Variable> scope = constraint1.getScope();
                assertTrue(
                                "La méthode getScope ne retourne pas le bon ensemble. Elle renvoie " + scope
                                                + " pour une contrainte portant sur : " + on1 + " et "
                                                + fixed2,
                                scope.equals(scope1));
                scope = constraint2.getScope();
                assertTrue(
                                "La méthode getScope ne retourne pas le bon ensemble. Elle renvoie " + scope
                                                + " pour une contrainte portant sur : " + on2 + " et "
                                                + fixed1,
                                scope.equals(scope2));
        }

        @Test
        public void testIsSatisfiedBy() {
                Map<Variable, Object> instanciation = new HashMap<>();
                instanciation.put(on1, 2);
                instanciation.put(fixed2, true);
                assertTrue("La méthode renvoie false pour la contrainte " + constraint1 + " avec l'instanciation "
                                + instanciation, constraint1.isSatisfiedBy(instanciation));

                instanciation.clear();
                instanciation.put(on1, 2);
                instanciation.put(fixed2, false);

                assertFalse("La méthode renvoie true pour la contrainte " + constraint1 + " avec l'instanciation "
                                + instanciation, constraint1.isSatisfiedBy(instanciation));

                instanciation.clear();
                instanciation.put(on1, 3);
                instanciation.put(fixed2, false);

                assertTrue("La méthode renvoie false pour la contrainte " + constraint1 + " avec l'instanciation "
                                + instanciation, constraint1.isSatisfiedBy(instanciation));

                instanciation.clear();
                instanciation.put(on1, 2);
                assertThrows(
                                "Une exception de type IllegalArgumentException devrait être lancée lorsqu'au moins une des variables n'a pas de valeur dans l'instanciation fournie.",
                                IllegalArgumentException.class, () -> constraint1.isSatisfiedBy(instanciation));

                instanciation.clear();
                instanciation.put(fixed1, false);
                assertThrows(
                                "Une exception de type IllegalArgumentException devrait être lancée lorsque l'une des variables n'a pas de valeur dans l'instanciation fournie.",
                                IllegalArgumentException.class, () -> constraint1.isSatisfiedBy(instanciation));

        }
      
}
