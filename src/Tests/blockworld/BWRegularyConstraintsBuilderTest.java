package Tests.blockworld;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.*;

import org.junit.*;

import Tests.utils.Function;
import blocksworld.BWRegularyConstraintsBuilder;
import modelling.Constraint;
public class BWRegularyConstraintsBuilderTest {
        private BWRegularyConstraintsBuilder bwRegularyConstraintsBuilder1;
        private BWRegularyConstraintsBuilder bwRegularyConstraintsBuilder2;
        private Set<Constraint> constraints1;
        private Set<Constraint> constraints2;

        @Before
        public void setUp() {
                bwRegularyConstraintsBuilder1 = new BWRegularyConstraintsBuilder(2, 2);
                bwRegularyConstraintsBuilder2 = new BWRegularyConstraintsBuilder(4, 3);
                constraints1 = Function.createRegularyConstraints(2, 2);
                constraints2 = Function.createRegularyConstraints(4, 3);
        }

        @Test
        public void testConstructor() {

                assertThrows(
                                "Une exception de type IllegalArgumentException devrait être lancée lorsque l'un des arguments est négatif",
                                IllegalArgumentException.class,
                                () -> new BWRegularyConstraintsBuilder(0, -1));

                assertThrows(
                                "Une exception de type IllegalArgumentException devrait être lancée lorsque l'un des arguments est négatif",
                                IllegalArgumentException.class,
                                () -> new BWRegularyConstraintsBuilder(-2, 5));
                assertTrue("Le constructeur n'assigne pas correctement les arguments aux attributs",
                                bwRegularyConstraintsBuilder1.getNbBlocks() == 2);
                assertTrue("Le constructeur n'assigne pas correctement les arguments aux attributs",
                                bwRegularyConstraintsBuilder1.getNbStacks() == 2);
                assertTrue("Le constructeur n'assigne pas correctement les arguments aux attributs",
                                bwRegularyConstraintsBuilder2.getNbBlocks() == 4);
                assertTrue("Le constructeur n'assigne pas correctement les arguments aux attributs",
                                bwRegularyConstraintsBuilder2.getNbStacks() == 3);
                 }

        @Test
        public void testGetConstraints() {
                assertTrue("La taille de l'ensemble renvoyé n'est pas égale à celle attendu",
                                constraints1.size() == bwRegularyConstraintsBuilder1.getConstraints().size());
                assertTrue("La taille de l'ensemble renvoyé n'est pas égale à celle attendu",
                                constraints2.size() == bwRegularyConstraintsBuilder2.getConstraints().size());
        }

        @Test
        public void testGetNbBlocks() {
                assertTrue("La méthode ne retourne pas la bonne valeur",
                                bwRegularyConstraintsBuilder1.getNbBlocks() == 2);
                assertTrue("La méthode ne retourne pas la bonne valeur",
                                bwRegularyConstraintsBuilder2.getNbBlocks() == 4);

        }

        @Test
        public void testGetNbStacks() {
                assertTrue("La méthode ne retourne pas la bonne valeur",
                                bwRegularyConstraintsBuilder1.getNbStacks() == 2);
                assertTrue("La méthode ne retourne pas la bonne valeur",
                                bwRegularyConstraintsBuilder2.getNbStacks() == 3);
        }

}
