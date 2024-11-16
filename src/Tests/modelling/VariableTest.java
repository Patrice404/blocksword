package Tests.modelling;

import org.junit.Before;
import org.junit.Test;
import modelling.*;
import java.util.*;
import static org.junit.Assert.*;
public class VariableTest {

        private Variable variable1;
        private Variable variable2;
        private Variable variable3;
        private Variable variable4;

        private BooleanVariable boolVariable1;

        private Set<Object> domain1;
        private Set<Object> domain2;

        @Before
        public void setUp() {
                // Crée un domaine contenant deux valeurs
                domain1 = new HashSet<>();
                domain1.add(1);
                domain1.add(2);

                domain2 = new HashSet<>();
                domain2.add(89);
                domain2.add(15);

               
                variable1 = new Variable(1, domain1);
                variable2 = new Variable(1, domain1);
                variable3 = new Variable(-1, domain2);
                variable4 = new Variable(0, domain2);

                boolVariable1 = new BooleanVariable(1);
        }

        @Test
        public void testGetName() {
                assertEquals("La méthode getName ne retourne pas le résultat attendu. Ça renvoie "
                                + variable1.getName() + " à la place de 1",
                                Integer.valueOf(1),
                                variable1.getName());

                assertEquals("La méthode getName ne retourne pas le résultat attendu. Ça renvoie "
                                + variable3.getName() + " à la place de -1",
                                Integer.valueOf(-1),
                                variable3.getName());

                assertEquals("La méthode getName ne retourne pas le résultat attendu. Ça renvoie "
                                + variable4.getName() + " à la place de 0",
                                Integer.valueOf(0),
                                variable4.getName());
        }

        @Test
        public void testToString() {
                String expected1 = "Variable : 1 Domaine : [1, 2].";
                String expected2 = "Variable : -1 Domaine : [89, 15].";

                assertEquals("La méthode toString ne renvoie pas le format attendu. Ça renvoie "
                                + variable1.toString() + " à la place de " + expected1,
                                expected1,
                                variable1.toString());

                assertEquals("La méthode toString ne renvoie pas le format attendu. Ça renvoie "
                                + variable3.toString() + " à la place de " + expected2,
                                expected2,
                                variable3.toString());
        }

        @Test
        public void testGetDomain() {
                assertEquals("La méthode getDomain ne renvoie pas le domaine attendu. Ça renvoie "
                                + variable1.getDomain() + " à la place de " + domain1,
                                domain1,
                                variable1.getDomain());

                assertEquals("La méthode getDomain ne renvoie pas le domaine attendu. Ça renvoie "
                                + variable3.getDomain() + " à la place de " + domain2,
                                domain2,
                                variable3.getDomain());

                assertEquals("La méthode getDomain ne renvoie pas le domaine attendu. Ça renvoie "
                                + variable4.getDomain() + " à la place de " + domain2,
                                domain2,
                                variable4.getDomain());
        }

        @Test
        public void testEquals() {
                // Comparaison des objets Variable
                boolean areEqualVariable1 = variable1.equals(variable2);
                boolean areEqualVariable2 = variable1.equals(variable4);

                // Comparaison entre Variable et BooleanVariable
                boolean areEqualVariableAndBooleanVariable = variable1.equals(boolVariable1);

                // Tests pour la classe Variable
                assertEquals("Deux variables avec le même nom et la même classe doivent être égales. "
                                + "Première variable: " + variable1 + ", Deuxième variable: " + variable2
                                + ". Résultat de la méthode equals: " + areEqualVariable1 + ". Résultat attendu : true",
                                true, areEqualVariable1);

                assertEquals("Deux variables avec des noms ou des classes différentes ne doivent pas être égales. "
                                + "Première variable: " + variable1 + ", Deuxième variable: " + variable4
                                + ". Résultat de la méthode equals: " + areEqualVariable2
                                + ". Résultat attendu : false",
                                false, areEqualVariable2);

                // Test entre Variable et BooleanVariable
                assertEquals("Une Variable et une BooleanVariable ne doivent pas être égales. "
                                + "Première variable: " + variable1 + ", Deuxième variable: " + boolVariable1
                                + ". Résultat de la méthode equals: " + areEqualVariableAndBooleanVariable
                                + ". Résultat attendu : false",
                                false, areEqualVariableAndBooleanVariable);
        }

        @Test
        public void testHashCode() {
                int hashCode1 = variable1.hashCode();
                int hashCode2 = variable2.hashCode();
                int hashCode3 = variable3.hashCode();
                int hashCode4 = variable4.hashCode();

                int hashCodeBool1 = boolVariable1.hashCode();

                // Pour la classe Variable
                assertEquals("Deux variables égales doivent avoir le même hashCode. "
                                + "Première variable: " + variable1 + ", Deuxième variable: " + variable2
                                + ". HashCode de la première variable: " + hashCode1
                                + ", HashCode de la deuxième variable: "
                                + hashCode2
                                + ". Résultat attendu : true", true, hashCode1 == hashCode2);

                assertEquals("Deux variables qui ne sont pas égales ne doivent pas avoir le même hashCode. "
                                + "Première variable: " + variable1 + ", Deuxième variable: " + variable3
                                + ". HashCode de la première variable: " + hashCode1
                                + ", HashCode de la deuxième variable: "
                                + hashCode3
                                + ". Résultat attendu : false", false, hashCode1 == hashCode3);

                assertEquals("Deux variables qui ne sont pas égales ne doivent pas avoir le même hashCode. "
                                + "Première variable: " + variable1 + ", Deuxième variable: " + variable4
                                + ". HashCode de la première variable: " + hashCode1
                                + ", HashCode de la deuxième variable: "
                                + hashCode4
                                + ". Résultat attendu : false", false, hashCode1 == hashCode4);

                // Comparaison entre Variable et BooleanVariable
                assertEquals("Une Variable et une BooleanVariable ne doivent pas avoir le même hashCode. "
                                + "Première variable: " + variable1 + ", Deuxième variable: " + boolVariable1
                                + ". HashCode de la première variable: " + hashCode1
                                + ", HashCode de la deuxième variable: "
                                + hashCodeBool1
                                + ". Résultat attendu : false", false, hashCode1 == hashCodeBool1);

        }

        @Test
        public void testIsBlockOnVariable() {
                assertEquals("La méthode isBlockOnVariable ne fonctionne pas comme prévue. Elle renvoie "
                                + Variable.isBlockOnVariable(variable1) + "pour la variable " + variable1, true,
                                Variable.isBlockOnVariable(variable1));
                assertFalse("La méthode isBlockOnVariable ne fonctionne pas comme prévue. Elle donne true pour la variable "
                                + variable3, Variable.isBlockOnVariable(variable3));
                assertTrue("La méthode isBlockOnVariable ne fonctionne pas comme prévue. Elle donne false pour la variable" + variable4, Variable.isBlockOnVariable(variable4));
        }
}