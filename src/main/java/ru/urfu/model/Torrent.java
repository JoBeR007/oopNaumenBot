package ru.urfu.model;

import java.net.URL;

public class Torrent {
    private final String category;
    private final String name;
    private final String size;
    private final int seeders;
    private final int completed;
    private final String magnet;
    private final URL link;
    private URL torrentDownloadLink;

    public Torrent(String category, String name, String size,
                   int seeders, int completed, String magnet,
                   URL link) {
        this.category = category;
        this.name = name;
        this.size = size;
        this.seeders = seeders;
        this.completed = completed;
        this.magnet = magnet;
        this.link = link;
    }

    public Torrent(String category, String name, String size,
                   int seeders, int completed, String magnet,
                   URL link, URL torrentDownloadLink) {
        this.category = category;
        this.name = name;
        this.size = size;
        this.seeders = seeders;
        this.completed = completed;
        this.magnet = magnet;
        this.link = link;
        this.torrentDownloadLink = torrentDownloadLink;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    public int getSeeders() {
        return seeders;
    }

    public int getCompleted() {
        return completed;
    }

    public String getMagnet() {
        return magnet;
    }

    public URL getLink() {
        return link;
    }

    public URL getTorrentDownloadLink(){
        return torrentDownloadLink;
    }
}
