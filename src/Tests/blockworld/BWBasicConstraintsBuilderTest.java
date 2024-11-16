package Tests.blockworld;

import org.junit.Before;
import org.junit.Test;
import org.junit.runners.model.TestClass;

import Tests.utils.Function;
import blocksworld.BWBasicConstraintsBuilder;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.*;

import modelling.Constraint;

public class BWBasicConstraintsBuilderTest extends TestClass{
    private BWBasicConstraintsBuilder bwBasicConstraintsBuilder1;
    private BWBasicConstraintsBuilder bwBasicConstraintsBuilder2;
    private BWBasicConstraintsBuilder bwBasicConstraintsBuilder3;

    private Set<Constraint> constraints1;
    private Set<Constraint> constraints2;
    private Set<Constraint> constraints3;

    public BWBasicConstraintsBuilderTest(){
        super(BWBasicConstraintsBuilderTest.class);
    }

    @Before
    public void setUp() {
        bwBasicConstraintsBuilder1 = new BWBasicConstraintsBuilder(2, 2);
        bwBasicConstraintsBuilder2 = new BWBasicConstraintsBuilder(4, 3);
        bwBasicConstraintsBuilder3 = new BWBasicConstraintsBuilder(0, 0);

        constraints1 = Function.createBasicConstraints(2, 2);
        constraints2 = Function.createBasicConstraints(4, 3);
        constraints3 = new HashSet<>();
    }

    @Test
    public void testConstructor() {
        assertThrows(
                "Une exception de type IllegalArgumentException devrait être lancée lorsque l'un des arguments est négatif",
                IllegalArgumentException.class,
                () -> new BWBasicConstraintsBuilder(-2, 5));
        assertThrows(
                "Une exception de type IllegalArgumentException devrait être lancée lorsque l'un des arguments est négatif",
                IllegalArgumentException.class,
                () -> new BWBasicConstraintsBuilder(2, -5));
        assertTrue("Le constructeur n'assigne pas correctement les arguments aux attributs",
                bwBasicConstraintsBuilder1.getNbBlocks() == 2);
        assertTrue("Le constructeur n'assigne pas correctement les arguments aux attributs",
                bwBasicConstraintsBuilder1.getNbStacks() == 2);
        assertTrue("Le constructeur n'assigne pas correctement les arguments aux attributs",
                bwBasicConstraintsBuilder2.getNbBlocks() == 4);
        assertTrue("Le constructeur n'assigne pas correctement les arguments aux attributs",
                bwBasicConstraintsBuilder2.getNbStacks() == 3);
        assertTrue("Le constructeur n'assigne pas correctement les arguments aux attributs",
                bwBasicConstraintsBuilder3.getNbBlocks() == 0);
        assertTrue("Le constructeur n'assigne pas correctement les arguments aux attributs",
                bwBasicConstraintsBuilder3.getNbStacks() == 0);

    }

    @Test
    public void testGetConstraints() {
        assertTrue("La taille de l'ensemble renvoyé n'est pas égale à celle attendu",
                constraints1.size() == bwBasicConstraintsBuilder1.getConstraints().size());
        assertTrue("La taille de l'ensemble renvoyé n'est pas égale à celle attendu",
                constraints2.size() == bwBasicConstraintsBuilder2.getConstraints().size());
        assertTrue("La taille de l'ensemble renvoyé n'est pas égale à celle attendu",
                constraints3.size() == bwBasicConstraintsBuilder3.getConstraints().size());

    }

    @Test
    public void testGetNbBlocks() {
        assertTrue("La méthode ne retourne pas la bonne valeur", bwBasicConstraintsBuilder1.getNbBlocks() == 2);
        assertTrue("La méthode ne retourne pas la bonne valeur", bwBasicConstraintsBuilder2.getNbBlocks() == 4);
        assertTrue("La méthode ne retourne pas la bonne valeur", bwBasicConstraintsBuilder3.getNbBlocks() == 0);

    }

    @Test
    public void testGetNbStacks() {
        assertTrue("La méthode ne retourne pas la bonne valeur", bwBasicConstraintsBuilder1.getNbStacks() == 2);
        assertTrue("La méthode ne retourne pas la bonne valeur", bwBasicConstraintsBuilder2.getNbStacks() == 3);
        assertTrue("La méthode ne retourne pas la bonne valeur", bwBasicConstraintsBuilder3.getNbStacks() == 0);

    }

}
