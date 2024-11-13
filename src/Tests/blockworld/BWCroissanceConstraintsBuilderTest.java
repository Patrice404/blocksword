package Tests.blockworld;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import Tests.utils.Function;
import blocksworld.BWCroissanceConstraintsBuilder;
import modelling.Constraint;

public class BWCroissanceConstraintsBuilderTest {
        private BWCroissanceConstraintsBuilder bwCroissanceConstraintsBuilder1;
        private BWCroissanceConstraintsBuilder bwCroissanceConstraintsBuilder2;
        private Set<Constraint> constraints1;
        private Set<Constraint> constraints2;

        @Before
        public void setUp() {
                bwCroissanceConstraintsBuilder1 = new BWCroissanceConstraintsBuilder(2, 2);
                bwCroissanceConstraintsBuilder2 = new BWCroissanceConstraintsBuilder(4, 3);
                constraints1 = Function.createCroissanceConstraints(2, 2);
                constraints2 = Function.createCroissanceConstraints(4, 3);

        }

        @Test
        public void testConstructor() {
                assertThrows(
                                "Une exception de type IllegalArgumentException devrait être lancée lorsque l'un des arguments est négatif",
                                IllegalArgumentException.class,
                                () -> new BWCroissanceConstraintsBuilder(-2, 5));
                assertThrows(
                                "Une exception de type IllegalArgumentException devrait être lancée lorsque l'un des arguments est négatif",
                                IllegalArgumentException.class,
                                () -> new BWCroissanceConstraintsBuilder(5, -5));

                assertTrue("Le constructeur n'assigne pas correctement les arguments aux attributs",
                                bwCroissanceConstraintsBuilder1.getNbBlocks() == 2);
                assertTrue("Le constructeur n'assigne pas correctement les arguments aux attributs",
                                bwCroissanceConstraintsBuilder1.getNbStacks() == 2);

                assertTrue("Le constructeur n'assigne pas correctement les arguments aux attributs",
                                bwCroissanceConstraintsBuilder2.getNbBlocks() == 4);
                assertTrue("Le constructeur n'assigne pas correctement les arguments aux attributs",
                                bwCroissanceConstraintsBuilder2.getNbStacks() == 3);
        }

        @Test
        public void testGetConstraints() {
                assertTrue("La taille de l'ensemble renvoyé n'est pas égale à celle attendu",
                                constraints1.size() == bwCroissanceConstraintsBuilder1.getConstraints().size());
                assertTrue("La taille de l'ensemble renvoyé n'est pas égale à celle attendu",
                                constraints2.size() == bwCroissanceConstraintsBuilder2.getConstraints().size());
        }

        @Test
        public void testGetNbBlocks() {
                assertTrue("La méthode ne retourne pas la bonne valeur",
                                bwCroissanceConstraintsBuilder1.getNbBlocks() == 2);
                assertTrue("La méthode ne retourne pas la bonne valeur",
                                bwCroissanceConstraintsBuilder2.getNbBlocks() == 4);

        }

        @Test
        public void testGetNbStacks() {
                assertTrue("La méthode ne retourne pas la bonne valeur",
                                bwCroissanceConstraintsBuilder1.getNbStacks() == 2);
                assertTrue("La méthode ne retourne pas la bonne valeur",
                                bwCroissanceConstraintsBuilder2.getNbStacks() == 3);
        }

}
