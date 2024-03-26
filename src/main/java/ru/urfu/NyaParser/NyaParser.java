package ru.urfu.NyaParser;

import ru.urfu.model.Torrent;

import java.util.ArrayList;
import java.util.List;

public class NyaParser {
    private String request;

    public NyaParser(String request) {
        this.request = request;
    }

    public List<Torrent> getTorrents(){
        return new ArrayList<>();
    }
}
