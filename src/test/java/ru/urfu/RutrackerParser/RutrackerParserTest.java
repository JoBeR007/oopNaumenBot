package ru.urfu.RutrackerParser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.urfu.model.Torrent;

import java.util.List;

public class RutrackerParserTest {
    private final RutrackerParser rutrackerParser = RutrackerParser.getInstance();

    @Test
    public void testGetMagnet1(){
        String expected = "magnet:?xt=urn:btih:2F1B54BFDB74648EBA0B752C6E48F5E87D4C9563";
        String actual = rutrackerParser.getMagnet("https://rutracker.org/forum/viewtopic.php?t=6189413");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testGetMagnet2(){
        String expected = "magnet:?xt=urn:btih:D4FEB0B237B8A3F109C3191E06E53B22C1E7FAD7";
        String actual = rutrackerParser.getMagnet("https://rutracker.org/forum/viewtopic.php?t=6391615");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testGetMagnetEmpty(){
        String expected = "";
        String actual = rutrackerParser.getMagnet(RutrackerSettings.TRACKER_URL);

        Assertions.assertEquals(expected, actual);
    }
}
