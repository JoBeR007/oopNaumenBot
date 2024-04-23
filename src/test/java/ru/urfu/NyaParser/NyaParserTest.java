package ru.urfu.NyaParser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.urfu.model.Torrent;

import java.util.List;

public class NyaParserTest {

    private final NyaParser nyaParser = NyaParser.getInstance();

    @Test
    public void testSearchNotNull(){
        String request = "Naruto";
        List<Torrent> answer = nyaParser.search(request);
        Assertions.assertNotNull(answer);
    }
}
