package ru.urfu.model;

import java.util.Date;
import java.util.Objects;

public class Torrent {
    private final String category;
    private final String name;
    private final String size;
    private final int seeders;
    private final int leechers;
    private final int completed;
    private String magnet = null;
    private final String url;
    private final Date date;
    private String torrentDownloadLink = null;

    public Torrent(String category, String name, String size,
                   int seeders, int leechers, int completed, String magnet,
                   String url, Date date, String torrentDownloadLink) {
        this.category = category;
        this.name = name;
        this.size = size;
        this.seeders = seeders;
        this.leechers = leechers;
        this.completed = completed;
        this.magnet = magnet;
        this.url = url;
        this.date = date;
        this.torrentDownloadLink = torrentDownloadLink;
    }

    public Torrent(String category, String name, String size, int seeders, int leechers, int completed, String url, Date date) {
        this.category = category;
        this.name = name;
        this.size = size;
        this.seeders = seeders;
        this.leechers = leechers;
        this.completed = completed;
        this.url = url;
        this.date = date;
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

    public String getUrl() {
        return url;
    }

    public String getTorrentDownloadLink(){
        return torrentDownloadLink;
    }

    public int getLeechers() {
        return leechers;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Torrent torrent = (Torrent) o;

        if (seeders != torrent.seeders) return false;
        if (leechers != torrent.leechers) return false;
        if (completed != torrent.completed) return false;
        if (!category.equals(torrent.category)) return false;
        if (!name.equals(torrent.name)) return false;
        if (!size.equals(torrent.size)) return false;
        if (!Objects.equals(magnet, torrent.magnet)) return false;
        if (!url.equals(torrent.url)) return false;
        if (!date.equals(torrent.date)) return false;
        return Objects.equals(torrentDownloadLink, torrent.torrentDownloadLink);
    }

    @Override
    public int hashCode() {
        int result = category.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + size.hashCode();
        result = 31 * result + seeders;
        result = 31 * result + leechers;
        result = 31 * result + completed;
        result = 31 * result + (magnet != null ? magnet.hashCode() : 0);
        result = 31 * result + url.hashCode();
        result = 31 * result + date.hashCode();
        result = 31 * result + (torrentDownloadLink != null ? torrentDownloadLink.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Torrent{" +
                "category='" + category + '\'' +
                ", name='" + name + '\'' +
                ", size='" + size + '\'' +
                ", seeders=" + seeders +
                ", leechers=" + leechers +
                ", completed=" + completed +
                ", magnet='" + magnet + '\'' +
                ", link='" + url + '\'' +
                ", date=" + date +
                ", torrentDownloadLink='" + torrentDownloadLink + '\'' +
                '}';
    }
}
