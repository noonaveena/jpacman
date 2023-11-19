package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.PacmanConfigurationException;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.npc.ghost.Blinky;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * This is a test class for MapParser.
 */
@ExtendWith(MockitoExtension.class)
public class MapParserTest {
    @Mock
    private BoardFactory boardFactory;
    @Mock
    private LevelFactory levelFactory;
    @Mock
    private Blinky blinky;
    /**
     * Test for the parseMap method (good map).
     */
    @Test
    public void testParseMapGood() {
        MockitoAnnotations.initMocks(this);
        Assertions.assertNotNull(boardFactory);
        Assertions.assertNotNull(levelFactory);
        Mockito.when(levelFactory.createGhost()).thenReturn(blinky);
        MapParser mapParser = new MapParser(levelFactory, boardFactory);
        ArrayList<String> map = new ArrayList<>();
        map.add("############");
        map.add("#P        G#");
        map.add("############");
        mapParser.parseMap(map);
        Mockito.verify(levelFactory, Mockito.times(1)).createGhost();

        /* Add other verifications that can be performed */
        int number_of_walls = 26;
        final int number_of_grounds= 10;
        Mockito.verify(boardFactory, Mockito.times(number_of_walls)).createWall();
        Mockito.verify(boardFactory, Mockito.times(number_of_grounds)).createGround();
    }
    /**
     * Test for the parseMap method (bad map).
     * Map contains unexpected characters.
     */
    @Test
    public void testParseMapWrong1() {
        PacmanConfigurationException thrown =
            Assertions.assertThrows(PacmanConfigurationException.class, () -> {
                MockitoAnnotations.initMocks(this);
                Assertions.assertNotNull(boardFactory);
                Assertions.assertNotNull(levelFactory);
                MapParser mapParser = new MapParser(levelFactory, boardFactory);
                ArrayList<String> map = new ArrayList<>();
                map.add("############");
                map.add("#X        G#");
                map.add("############");
                mapParser.parseMap(map);
                mapParser.parseMap(map);
            });
        String message = "Invalid character at 1,1: X";
        Assertions.assertEquals(message, thrown.getMessage());
    }
}


