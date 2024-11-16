package Tests.blockworld;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import Tests.utils.Function;
import blocksworld.BWVariablesBuilder;
import modelling.Variable;

public class BWVariablesBuilderTest {
    private Set<Variable> config2x2Variables;
    private Set<Variable> config4x3Variables;
    private BWVariablesBuilder bwVariablesBuilder1;
    private BWVariablesBuilder bwVariablesBuilder2;

    @Before
    public void setUp() {
        bwVariablesBuilder1 = new BWVariablesBuilder(2, 2);
        bwVariablesBuilder2 = new BWVariablesBuilder(4, 3);

        config2x2Variables = Function.createVariables(2, 2);
        config4x3Variables = Function.createVariables(4, 3);
    }

    @Test
    public void testConstructor() {
        assertThrows(
                "Une exception de type IllegalArgumentException devrait être lancée lorsque l'un des arguments est négatif",
                IllegalArgumentException.class,
                () -> new BWVariablesBuilder(0, -1));
        assertThrows(
                "Une exception de type IllegalArgumentException devrait être lancée lorsque l'un des arguments est négatif",
                IllegalArgumentException.class,
                () -> new BWVariablesBuilder(0, -1));
        assertTrue("Le constructeur n'assigne pas correctement les arguments aux attributs",
                bwVariablesBuilder1.getNbBlocks() == 2);
        assertTrue("Le constructeur n'assigne pas correctement les arguments aux attributs",
                bwVariablesBuilder1.getNbStacks() == 2);
        assertTrue("Le constructeur n'assigne pas correctement les arguments aux attributs",
                bwVariablesBuilder2.getNbBlocks() == 4);
        assertTrue("Le constructeur n'assigne pas correctement les arguments aux attributs",
                bwVariablesBuilder2.getNbStacks() == 3);
    }

    @Test
    public void testGetVariables() {
        assertTrue("L'ensemble de variables construites ne correspond pas à ce qui est attendu",
                config2x2Variables.equals(bwVariablesBuilder1.getVariables()));
        assertTrue("L'ensemble de variables construites ne correspond pas à ce qui est attendu",
                config4x3Variables.equals(bwVariablesBuilder2.getVariables()));
    }

    @Test
    public void testGetNbBlocks() {
        assertTrue("La méthode ne retourne pas la bonne valeur", bwVariablesBuilder1.getNbBlocks() == 2);
        assertTrue("La méthode ne retourne pas la bonne valeur", bwVariablesBuilder2.getNbBlocks() == 4);

    }

    @Test
    public void testGetNbStacks() {
        assertTrue("La méthode ne retourne pas la bonne valeur", bwVariablesBuilder1.getNbStacks() == 2);
        assertTrue("La méthode ne retourne pas la bonne valeur", bwVariablesBuilder2.getNbStacks() == 3);
    }

}
