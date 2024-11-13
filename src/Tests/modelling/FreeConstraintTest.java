package Tests.modelling;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import Tests.utils.Function;
import modelling.BooleanVariable;
import modelling.FreeConstraint;
import modelling.Variable;

public class FreeConstraintTest {
    private BooleanVariable free1;
    private BooleanVariable free2;
    private Variable on1;
    private Variable on2;
    Set<Object> domain1;
    Set<Object> domain2;
    private FreeConstraint constraint1;
    private FreeConstraint constraint2;
    private Set<Variable> scope1;
    private Set<Variable> scope2;

    @Before
    public void setUp() {
        Set<Object> domain1 = Function.calculDomain(1, 4, 2);
        Set<Object> domain2 = Function.calculDomain(2, 4, 2);
        on1 = new Variable(1, domain1);
        free1 = new BooleanVariable(-1);

        on2 = new Variable(2, domain2);
        free2 = new BooleanVariable(-2);
        
        constraint1 = new FreeConstraint(on1, free1);
        constraint2 = new FreeConstraint(on2, free2);

        scope1 = new HashSet<>();
        scope1.add(on1);
        scope1.add(free1);

        scope2 = new HashSet<>();
        scope2.add(on2);
        scope2.add(free2);

    }
    @Test
    public void testConstructor() {
        FreeConstraint constraint = new FreeConstraint(on1, free1);

        assertEquals("Le constructeur n'affecte pas bien les variables données en argument", scope1,
                constraint.getScope());

        assertThrows(
                "Une exception de type IllegalArgumentException devrait être lancée lorsque le deuxième argument n'est pas la variable 'Free' d'une pile.",
                IllegalArgumentException.class,
                () -> new FreeConstraint(free1, on1));

        assertThrows(
                "Une exception de type IllegalArgumentException devrait être lancée lorsque le premier argument n'est pas la variable 'On' d'un bloc.",
                IllegalArgumentException.class,
                () -> new FreeConstraint(free2, on2));

        assertThrows(
                "Une exception de type IllegalArgumentException devrait être lancée lorsque l'une des variables est null.",
                IllegalArgumentException.class,
                () -> new FreeConstraint(on1, null));

        assertThrows(
                "Une exception de type IllegalArgumentException devrait être lancée lorsque l'une des variables est null.",
                IllegalArgumentException.class,
                () -> new FreeConstraint(null, free1));

        assertThrows(
                "Une exception de type IllegalArgumentException devrait être lancée lorsque les deux variables sont les memes",
                IllegalArgumentException.class,
                () -> new FreeConstraint(on1, on1));
    }

    @Test
        public void testGetScope() {
                Set<Variable> scope = constraint1.getScope();

                assertTrue(
                                "La méthode getScope ne retourne pas le bon ensemble. Elle renvoie " + scope
                                                + " pour une contrainte portant sur : " + on1 + " et "
                                                + free1,
                                scope.equals(scope1));
                scope = constraint2.getScope();
                assertTrue(
                                "La méthode getScope ne retourne pas le bon ensemble. Elle renvoie " + scope
                                                + " pour une contrainte portant sur : " + on2 + " et "
                                                + free2,
                                scope.equals(scope2));
        }

        @Test
        public void testIsSatisfiedBy() {
                Map<Variable, Object> instanciation = new HashMap<>();
                instanciation.put(on1, 2);
                instanciation.put(free1, true);
                assertTrue("La méthode renvoie false pour la contrainte " + constraint1 + " avec l'instanciation "
                                + instanciation, constraint1.isSatisfiedBy(instanciation));

                instanciation.clear();
                instanciation.put(on1, 1);
                instanciation.put(free1, false);
                
                assertTrue("La méthode renvoie false pour la contrainte " + constraint1 + " avec l'instanciation "
                                + instanciation, constraint1.isSatisfiedBy(instanciation));

                instanciation.clear();
                instanciation.put(on2, 3);
                instanciation.put(free2, false);

                assertTrue("La méthode renvoie false pour la contrainte " + constraint2 + " avec l'instanciation "
                                + instanciation, constraint2.isSatisfiedBy(instanciation));

        }
}
