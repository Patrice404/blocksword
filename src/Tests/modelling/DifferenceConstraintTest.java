package Tests.modelling;

import modelling.Variable;
import modelling.BooleanVariable;
import modelling.DifferenceConstraint;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class DifferenceConstraintTest {

    private Variable block0Variable;
    private Variable block1Variable;
    private Variable block2Variable;
    private BooleanVariable booleanVariable;
    private Variable noBlockVariable;
    private DifferenceConstraint constraint1;
    private DifferenceConstraint constraint2;

    private Set<Variable> scope1;
    private Set<Variable> scope2;

    @Before
    public void setUp() {
        Set<Object> domain0 = new HashSet<>();
        domain0.add(-1);
        domain0.add(-2);
        domain0.add(1);
        domain0.add(2);

        Set<Object> domain1 = new HashSet<>();
        domain1.add(-1);
        domain1.add(-2);
        domain1.add(0);
        domain1.add(2);

        Set<Object> domain2 = new HashSet<>();
        domain1.add(-1);
        domain1.add(-2);
        domain2.add(0);
        domain2.add(1);

        block0Variable = new Variable(0, domain0);
        block1Variable = new Variable(1, domain1);
        block2Variable = new Variable(2, domain2);

        noBlockVariable = new Variable(-4, domain2);
        booleanVariable = new BooleanVariable(-1);

        constraint1 = new DifferenceConstraint(block1Variable, block2Variable);
        constraint2 = new DifferenceConstraint(block0Variable, block2Variable);

        scope1 = new HashSet<>();
        scope1.add(block1Variable);
        scope1.add(block2Variable);

        scope2 = new HashSet<>();
        scope2.add(block0Variable);
        scope2.add(block2Variable);
    }

    @Test
    public void testConstructor() {
        DifferenceConstraint constraint = new DifferenceConstraint(block1Variable, block2Variable);

        assertEquals("Le constructeur n'affecte pas bien les variables données en argument", scope1, constraint.getScope());
        assertThrows(
                "Une exception de type IllegalArgumentException devrait être lancée lorsque l'un des variables est null.",
                IllegalArgumentException.class,
                () -> new DifferenceConstraint(block1Variable, null));

        assertThrows(
                "Une exception de type IllegalArgumentException devrait être lancée lorsque l'une des variables n'est pas une variable on d'un bloc.",
                IllegalArgumentException.class,
                () -> new DifferenceConstraint(noBlockVariable, block1Variable));

        assertThrows(
                "Une exception de type IllegalArgumentException devrait être lancée lorsque l'une des variables n'est pas une variable on d'un bloc.",
                IllegalArgumentException.class,
                () -> new DifferenceConstraint(booleanVariable, block1Variable));

        assertThrows(
                "Une exception de type IllegalArgumentException devrait être lancée lorsque l'une des variables n'est pas une variable on d'un bloc.",
                IllegalArgumentException.class,
                () -> new DifferenceConstraint(block1Variable, noBlockVariable));

        assertThrows(
                "Une exception de type IllegalArgumentException devrait être lancée lorsque les variables sont identiques.",
                IllegalArgumentException.class,
                () -> new DifferenceConstraint(block1Variable, block1Variable));
    }

    @Test
    public void testGetScope() {
        Set<Variable> scope = constraint1.getScope();
        assertTrue(
                "La méthode getScope ne retourne pas le bon ensemble. Elle renvoie " + scope
                        + " pour une contrainte portant sur : " + block1Variable + " et " + block2Variable,
                scope.equals(scope1));
        scope = constraint2.getScope();
        assertTrue(
                "La méthode getScope ne retourne pas le bon ensemble. Elle renvoie " + scope
                        + " pour une contrainte portant sur : " + block0Variable + " et " + block2Variable,
                scope.equals(scope2));
    }

    @Test
    public void testIsSatisfiedBy() {
        Map<Variable, Object> instanciation = new HashMap<>();
        instanciation.put(block1Variable, -1);
        instanciation.put(block2Variable, 0);
        assertTrue(
                "La méthode renvoie false pour la contrainte " + constraint1 + " avec l'instanciation " + instanciation,
                constraint1.isSatisfiedBy(instanciation));

        instanciation.clear();
        instanciation.put(block1Variable, 2);
        instanciation.put(block2Variable, 2);

        assertFalse(
                "La méthode renvoie true pour la contrainte " + constraint1 + " avec l'instanciation " + instanciation,
                constraint1.isSatisfiedBy(instanciation));

        instanciation.clear();
        instanciation.put(block2Variable, 2);
        assertThrows(
                "Une exception de type IllegalArgumentException devrait être lancée lorsque l'une des variables n'a pas de valeur dans l'instanciation forunie.",
                IllegalArgumentException.class, () -> constraint1.isSatisfiedBy(instanciation));

        instanciation.clear();
        instanciation.put(block1Variable, 1);
        assertThrows(
                "Une exception de type IllegalArgumentException devrait être lancée lorsque l'une des variables n'a pas de valeur dans l'instanciation forunie.",
                IllegalArgumentException.class, () -> constraint1.isSatisfiedBy(instanciation));
    }

    @Test
    public void testToString() {
        String expected1 = "Contrainte de type OnDiff entre 1 et 2";
        String expected2 = "Contrainte de type OnDiff entre 0 et 2";

        assertEquals(
                "La méthode toString() ne renvoie pas la chaîne attendue. Résultat obtenu: " + constraint1.toString()
                        + " Résulatat attendu " + expected1,
                expected1, constraint1.toString());

        assertEquals(
                "La méthode toString() ne renvoie pas la chaîne attendue. Résultat obtenu: " + constraint2.toString()
                        + " Résulatat attendu " + expected2,
                expected2, constraint2.toString());
    }

    @Test
    public void testEquals() {
        assertFalse("La méthode renvoie true pour les deux contraintes suivantes " + constraint1 + " " + constraint2,
                constraint1.equals(constraint2));
        assertTrue("La méthode renvoie false pour les deux contraintes suivantes " + constraint1 + " " + constraint2,
                constraint1.equals(constraint1));
    }

}
