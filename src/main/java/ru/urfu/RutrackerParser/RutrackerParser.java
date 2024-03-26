package ru.urfu.RutrackerParser;

import ru.urfu.model.Torrent;

import java.util.ArrayList;
import java.util.List;

public class RutrackerParser {
    private String request;

    public RutrackerParser(String request) {
        this.request = request;
    }

    public List<Torrent> getTorrents(){
        return new ArrayList<>();
    }
}
