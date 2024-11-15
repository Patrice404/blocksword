package Tests.modelling;

import modelling.Variable;
import modelling.BooleanVariable;
import modelling.Implication;

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

public class ImplicationTest {

        private Variable on1;
        private Variable on2;
        private Variable fixed1;
        private Variable fixed2;
        private Set<Object> domain1;
        private Set<Object> domain2;
        private Implication constraint1;
        private Implication constraint2;
        private Implication constraint3;

        private Set<Variable> scope1;
        private Set<Variable> scope2;
        private Set<Object> s1;
        private Set<Object> s2;
        private Set<Object> s3;


        @Before
        public void setUp() {
                domain1 = Function.calculDomain(1, 4, 2);
                domain2 = Function.calculDomain(2, 4, 2);

                on1 = new Variable(1, domain1);
                fixed1 = new BooleanVariable(1);

                on2 = new Variable(2, domain2);
                fixed2 = new BooleanVariable(2);
                s1 = new HashSet<>();
                s1.add(2);
                s2 = new HashSet<>();
                s2.add(true);

                s3 = new HashSet<>();
                s3.add(0);
                s3.add(3);

                constraint1 = new Implication(on1, s1, fixed2, s2);
                constraint2 = new Implication(on2, s3, fixed1, s2);
                constraint3 = new Implication(on2, domain2, on1, s3);
                scope1 = new HashSet<>();
                scope1.add(on1);
                scope1.add(fixed2);

                scope2 = new HashSet<>();
                scope2.add(on2);
                scope2.add(fixed1);

        }

        @Test
        public void testConstructor() {
                Set<Object> s4 = new HashSet<>();
                s4.add(1);
                s4.add(2);
                s4.add(4);

                assertEquals("Le constructeur n'affecte pas bien les variables données en argument", scope1,
                                constraint1.getScope());
                assertEquals("Le constructeur n'affecte pas bien les variables données en argument", scope2,
                                constraint2.getScope());

                assertThrows(
                                "Une exception de type IllegalArgumentException devrait être lancée lorsque la domaine de la variable 1 ne contient l'ensemble des éléments de s1. Pareil pour la variable 2 et s2",
                                IllegalArgumentException.class,
                                () -> new Implication(on1, s2, on2, s1));
                assertThrows(
                                "Une exception de type IllegalArgumentException devrait être lancée lorsque la domaine de la variable 1 ne contient l'ensemble des éléments de s1",
                                IllegalArgumentException.class,
                                () -> new Implication(on1, s4, fixed2, s2));

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
                instanciation.put(on2, 1);
                instanciation.put(on1, 2);
                assertFalse("La méthode renvoie true pour la contrainte " + constraint3 + " avec l'instanciation "
                                + instanciation, constraint3.isSatisfiedBy(instanciation));

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
