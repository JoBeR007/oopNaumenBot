package ru.urfu.model;

import java.util.ArrayList;

public class LastTorrentList {
    private static LastTorrentList instance;
    private ArrayList<Torrent> list;

    private LastTorrentList() {
        list = new ArrayList<Torrent>();
    }

    public static synchronized LastTorrentList getInstance() {
        if (instance == null) {
            instance = new LastTorrentList();
        }
        return instance;
    }

    public ArrayList<Torrent> getList() {
        return list;
    }

    public void setList(ArrayList<Torrent> newList) {
        if (newList != null) {
            this.list = newList;
        }
    }
}

