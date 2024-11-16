package Tests.blockworld;

import org.junit.Before;
import org.junit.Test;

import Tests.utils.Function;
import blocksworld.DataminingVariableBuilder;
import modelling.BooleanVariable;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.*;

public class DataminingVariableBuilderTest {
    private DataminingVariableBuilder dataminingVariableBuilder1;
    private DataminingVariableBuilder dataminingVariableBuilder2;
    private DataminingVariableBuilder dataminingVariableBuilder3;
    private Set<BooleanVariable> variables1;
    private Set<BooleanVariable> variables2;
    private Set<BooleanVariable> variables3;

    @Before
    public void setUp() {
        dataminingVariableBuilder1 = new DataminingVariableBuilder(2, 2);
        dataminingVariableBuilder2 = new DataminingVariableBuilder(4, 3);
        dataminingVariableBuilder3 = new DataminingVariableBuilder(0, 0);
        variables1 = Function.createDataminingVariable(2, 2);
        variables2 = Function.createDataminingVariable(4, 3);
        variables3 = new HashSet<>();
    }

    @Test
    public void testConstructor() {
        assertThrows(
                "Une exception de type IllegalArgumentException devrait être lancée lorsque l'un des arguments est négatif",
                IllegalArgumentException.class,
                () -> new DataminingVariableBuilder(0, -1));
        assertThrows(
                "Une exception de type IllegalArgumentException devrait être lancée lorsque l'un des arguments est négatif",
                IllegalArgumentException.class,
                () -> new DataminingVariableBuilder(-1, 0));
    }

    @Test
    public void testGetVariables() {
        assertTrue("La taille de l'ensemble renvoyé n'est pas égale à celle attendu",
                variables1.size() == dataminingVariableBuilder1.getVariables().size());
        assertTrue("La taille de l'ensemble renvoyé n'est pas égale à celle attendu",
                variables2.size() == dataminingVariableBuilder2.getVariables().size());
        assertTrue("La taille de l'ensemble renvoyé n'est pas égale à celle attendu",
                variables3.size() == dataminingVariableBuilder3.getVariables().size());

        assertTrue("La méthode ne renvoie pas le bon ensemble",
                variables1.equals(dataminingVariableBuilder1.getVariables()));
        assertTrue("La méthode ne renvoie pas le bon ensemble",
                variables2.equals(dataminingVariableBuilder2.getVariables()));
        assertTrue("La méthode ne renvoie pas le bon ensemble",
                variables3.equals(dataminingVariableBuilder3.getVariables()));

    }
}
