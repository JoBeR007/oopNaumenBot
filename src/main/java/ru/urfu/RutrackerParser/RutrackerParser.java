package ru.urfu.RutrackerParser;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.urfu.model.Torrent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Slf4j
public class RutrackerParser {

    private static RutrackerParser instance = null;

    private final RutrackerLogin auth;

    private RutrackerParser() {
        auth = new RutrackerLogin();
    }

    public static RutrackerParser getInstance() {
        if (instance == null) {
            log.info("Instantiating RutrackerParser");
            instance = new RutrackerParser();
        }
        return instance;
    }

    public List<Torrent> search(String searchPhrase) {
        log.info("Searching rutracker for: " + searchPhrase);
        Document doc = fetchDocument(RutrackerSettings.SEARCH_URL + searchPhrase);

        if (doc != null) {
            Elements results = doc.getElementsByClass("tCenter hl-tr");
            List<Torrent> searchResults = new ArrayList<>();
            for (Element element : results) {
                searchResults.add(extractTorrentFromElement(element));
            }
            return searchResults;
        }
        log.info("No results for: " + searchPhrase);
        return Collections.emptyList();
    }

    public String getMagnet(String torrentUrl) {
        log.info("Getting magnet for url: " + torrentUrl);
        Document doc = fetchDocument(torrentUrl);

        if (doc != null) {
            Elements results = doc.getElementsByClass("row1");
            Element firstElement = results.select("a.med.magnet-link").first();
            if (firstElement != null) {
                String magnet = firstElement.attr("href");
                int endIdx = magnet.indexOf("&tr");
                if (endIdx != -1) {
                    return magnet.substring(0, endIdx);
                }
            }
        }
        log.info("No magnet on url: " + torrentUrl);
        return "";
    }

    public String getTorrentFileDownloadLink(String torrentUrl) {
        log.info("Getting .torrent link for url: " + torrentUrl);
        Document doc = fetchDocument(torrentUrl);

        if (doc != null) {
            Elements results = doc.getElementsByClass("row1");
            Element firstElement = results.select("a.dl-stub.dl-link.dl-topic").first();
            if (firstElement != null) {
                String relative = firstElement.attr("href");
                return RutrackerSettings.TRACKER_URL + relative;
            }
        }

        log.info("No .torrent link on url: " + torrentUrl);
        return "";
    }

    private Document fetchDocument(String url) {
        log.info("Fetching document from: " + url);
        if (!auth.isAuthenticated()) {
            auth.login();
        }

        if (auth.isAuthenticated()) {
            try {
                return Jsoup.connect(url)
                        .cookies(auth.getCookies())
                        .get();
            } catch (Exception e) {
                log.warn("Unable to connect to rutracker.org: " + e.getLocalizedMessage());
            }
        }

        log.info("Unable to fetch document for url: " + url);
        return null;
    }

    private Torrent extractTorrentFromElement(Element element) {
        log.info("Extracting Torrent info from Element: " + element.text());
        String category = element.getElementsByClass("gen f ts-text").get(0).text();
        String name = element.getElementsByClass("t-title").get(0).text();
        String sizeText = element.getElementsByClass("small tr-dl dl-stub").get(0)
                .text().replace(" ", " ")
                .replace("↓", "").trim();
        int seeders;
        int leechers;
        int completed;
        try {
            seeders = Integer.parseInt(element.getElementsByClass("seedmed").get(0).text());
            leechers = Integer.parseInt(element.getElementsByClass("row4 leechmed bold").get(0).text());
            completed = Integer.parseInt(element.getElementsByClass("row4 small number-format").get(0).text());
        } catch (Exception e) {
            log.warn("Error while trying to parse values");
            seeders = 0;
            leechers = 0;
            completed = 0;
        }
        String url = RutrackerSettings.TRACKER_URL + element.select("a").get(1).attr("href");
        Date date = new Date(Long.parseLong(element.getElementsByClass("row4 small nowrap")
                .get(0).attr("data-ts_text")) * 1000);
        return new Torrent(category, name, sizeText, seeders, leechers, completed, url, date);
    }
}
