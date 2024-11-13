package Tests.modelling;

import org.junit.Before;
import org.junit.Test;
import modelling.BooleanVariable;
import modelling.Variable;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BooleanVariableTest {

        private Variable variable1;

        private BooleanVariable boolVariable1;
        private BooleanVariable boolVariable2;
        private BooleanVariable boolVariable3;
        private BooleanVariable boolVariable4;

        private Set<Object> domain1;
        private Set<Object> boolDomain;

        @Before
        public void setUp() {
                // Crée un domaine contenant deux valeurs
                domain1 = new HashSet<>();
                domain1.add(1);
                domain1.add(2);

                // Crée un domaine pour les variables booléennes avec true et false
                boolDomain = new HashSet<>();
                boolDomain.add(true);
                boolDomain.add(false);

                variable1 = new Variable(1, domain1);

                boolVariable1 = new BooleanVariable(1);
                boolVariable2 = new BooleanVariable(1);
                boolVariable3 = new BooleanVariable(2);
                boolVariable4 = new BooleanVariable(-14);

        }

        @Test
        public void testGetName() {
                assertEquals("La méthode getName ne retourne pas le résultat attendu. Ça renvoie "
                                + boolVariable1.getName() + " à la place de 1",
                                Integer.valueOf(1),
                                boolVariable1.getName());

                assertEquals("La méthode getName ne retourne pas le résultat attendu. Ça renvoie "
                                + boolVariable2.getName() + " à la place de 1",
                                Integer.valueOf(1),
                                boolVariable2.getName());

                assertEquals("La méthode getName ne retourne pas le résultat attendu. Ça renvoie "
                                + boolVariable3.getName() + " à la place de 2",
                                Integer.valueOf(2),
                                boolVariable3.getName());
                assertEquals("La méthode getName ne retourne pas le résultat attendu. Ça renvoie "
                                + boolVariable4.getName() + " à la place de -14",
                                Integer.valueOf(-14),
                                boolVariable4.getName());
        }

        @Test
        public void testToString() {
                String expectedBool1 = "BooleanVariable : 1";
                String expectedBool4 = "BooleanVariable : -14";

                assertEquals("La méthode toString ne renvoie pas le format attendu. Ça renvoie "
                                + boolVariable1.toString() + " à la place de " + expectedBool1,
                                expectedBool1,
                                boolVariable1.toString());
                assertEquals("La méthode toString ne renvoie pas le format attendu. Ça renvoie "
                                + boolVariable4.toString() + " à la place de " + expectedBool4,
                                expectedBool4,
                                boolVariable4.toString());
        }

        @Test
        public void testGetDomain() {
                assertEquals("La méthode getDomain ne renvoie pas le domaine attendu. Ça renvoie "
                                + boolVariable1.getDomain() + " à la place de " + boolDomain,
                                boolDomain,
                                boolVariable1.getDomain());
                assertEquals("La méthode getDomain ne renvoie pas le domaine attendu. Ça renvoie "
                                + boolVariable4.getDomain() + " à la place de " + boolDomain,
                                boolDomain,
                                boolVariable4.getDomain());
        }

        @Test
        public void testEquals() {

                assertTrue("La méthode equals ne fonctionne pas comme prévu. Elle retourne false pour les variables "
                                + boolVariable1 + " et " + boolVariable2, boolVariable1.equals(boolVariable2));

                assertFalse("La méthode equals ne fonctionne pas comme prévu. Elle retourne true pour les variables "
                                + boolVariable1 + " et " + boolVariable3, boolVariable1.equals(boolVariable3));

                assertFalse("La méthode equals ne fonctionne pas comme prévu. Elle retourne true pour les variables "
                                + boolVariable1 + " et " + variable1, boolVariable1.equals(variable1));

        }

        @Test
        public void testHashCode() {

                assertTrue("La méthode hashCode retourne false pour les variables " + boolVariable1 + " et "
                                + boolVariable2, boolVariable1.hashCode() == boolVariable2.hashCode());
                assertFalse("La méthode hashCode retourne true pour les variables " + boolVariable1 + " et "
                                + boolVariable3, boolVariable1.hashCode() == boolVariable3.hashCode());
                assertFalse("La méthode hashCode retourne false pour les variables " + boolVariable3 + " et "
                                + boolVariable4, boolVariable3.hashCode() == boolVariable4.hashCode());
                assertFalse("La méthode hashCode retourne false pour les variables " + boolVariable4 + " et "
                                + variable1, boolVariable4.hashCode() == variable1.hashCode());
        }

        @Test
        public void testIsBlockFixedVariable() {
                assertTrue("La méthode isBlockFixedVariable renvoie false pour la variable " + boolVariable1,
                                BooleanVariable.isBlockFixedVariable(boolVariable1));
                assertTrue("La méthode isBlockFixedVariable renvoie false pour la variable " + boolVariable3,
                                BooleanVariable.isBlockFixedVariable(boolVariable3));
                assertFalse("La méthode isBlockFixedVariable renvoie true pour la variable " + boolVariable4,
                                BooleanVariable.isBlockFixedVariable(boolVariable4));
        }

        @Test
        public void testIsStackFreeVariable() {
                assertFalse("La méthode isStackFreeVariable renvoie true pour la variable " + boolVariable1,
                                BooleanVariable.isStackFreeVariable(boolVariable1));
                assertTrue("La méthode isStackFreeVariable renvoie false pour la variable " + boolVariable4,
                                BooleanVariable.isStackFreeVariable(boolVariable4));
                assertFalse("La méthode isStackFreeVariable renvoie true pour la variable " + boolVariable3,
                                BooleanVariable.isStackFreeVariable(boolVariable3));

        }
}