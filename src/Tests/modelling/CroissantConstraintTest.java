package Tests.modelling;

import modelling.Variable;
import modelling.BooleanVariable;
import modelling.CroissantConstraint;

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

public class CroissantConstraintTest {
        private Variable on1;
        private Variable on2;
        private Variable on3;
        private Variable fixed1;
        Set<Object> domain1;
        Set<Object> domain2;
        private CroissantConstraint constraint1;
        private CroissantConstraint constraint2;
        private CroissantConstraint constraint3;
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

                constraint1 = new CroissantConstraint(on1, on2);
                constraint2 = new CroissantConstraint(on1, on3);
                constraint3 = new CroissantConstraint(on2, on1);

                scope1 = new HashSet<>();
                scope1.add(on1);
                scope1.add(on2);

                scope2 = new HashSet<>();
                scope2.add(on1);
                scope2.add(on3);

        }

        @Test
        public void testConstructor() {

                assertEquals("Le constructeur n'affecte pas bien les variables données en argument", scope1,
                                constraint1.getScope());

                assertThrows(
                                "Une exception de type IllegalArgumentException devrait être lancée lorsque le deuxième argument n'est pas la variable 'On' d'un bloc.",
                                IllegalArgumentException.class,
                                () -> new CroissantConstraint(on1, fixed1));

                assertThrows(
                                "Une exception de type IllegalArgumentException devrait être lancée lorsque le premier argument n'est pas la variable 'On' d'un bloc.",
                                IllegalArgumentException.class,
                                () -> new CroissantConstraint(fixed1, on2));

                assertThrows(
                                "Une exception de type IllegalArgumentException devrait être lancée lorsque l'un des variables est null.",
                                IllegalArgumentException.class,
                                () -> new CroissantConstraint(on1, null));

                assertThrows(
                                "Une exception de type IllegalArgumentException devrait être lancée lorsque l'un des variables est null.",
                                IllegalArgumentException.class,
                                () -> new CroissantConstraint(null, fixed1));

                assertThrows(
                                "Une exception de type IllegalArgumentException devrait être lancée lorsque les deux variables sont les memes",
                                IllegalArgumentException.class,
                                () -> new CroissantConstraint(on1, on1));
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
                                                + " pour une contrainte portant sur : " + on1 + " et "
                                                + on3,
                                scope.equals(scope2));
        }

        @Test
        public void testIsSatisfiedBy() {
                Map<Variable, Object> instanciation = new HashMap<>();
                instanciation.put(on1, 2);
                instanciation.put(on2, -1);
                assertFalse("La méthode renvoie true pour la contrainte " + constraint1 + " avec l'instanciation "
                                + instanciation, constraint1.isSatisfiedBy(instanciation));

                instanciation.clear();
                instanciation.put(on1, 2);
                instanciation.put(on1, 2);

                assertFalse("La méthode renvoie true pour la contrainte " + constraint1 + " avec l'instanciation "
                                + instanciation, constraint1.isSatisfiedBy(instanciation));

                instanciation.clear();
                instanciation.put(on2, 3);
                instanciation.put(on1, 0);

                assertTrue("La méthode renvoie false pour la contrainte " + constraint3 + " avec l'instanciation "
                                + instanciation, constraint3.isSatisfiedBy(instanciation));

                instanciation.clear();
                instanciation.put(on2, 1);
                instanciation.put(on1, 0);

                assertTrue("La méthode renvoie false pour la contrainte " + constraint3 + " avec l'instanciation "
                                + instanciation, constraint3.isSatisfiedBy(instanciation));
        }


}
