package ru.urfu.NyaParser;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.urfu.RutrackerParser.RutrackerParser;
import ru.urfu.RutrackerParser.RutrackerSettings;
import ru.urfu.model.Torrent;

import java.util.*;

@Slf4j
public class NyaParser {

    private static NyaParser instance = null;


    private NyaParser() {
    }

    public static NyaParser getInstance() {
        if (instance == null) {
            log.info("Instantiating NyaParser");
            instance = new NyaParser();
        }
        return instance;
    }

    public List<Torrent> search(String searchPhrase) {
        log.info("Searching nya for: {}", searchPhrase);
        Document doc = fetchDocument(NyaSettings.SEARCH_URL + searchPhrase);

        if (doc != null) {
            Elements elements = doc.getElementsByClass("default");
            List<Torrent> res = new ArrayList<>();
            for (Element element : elements) {
                res.add(extractTorrentFromElement(element));
            }
            return res;
        }
        log.info("No results for: {}", searchPhrase);
        return Collections.emptyList();
    }

    private Document fetchDocument(String url) {
        log.info("Fetching document from: {}", url);
        try {
            return Jsoup.connect(url)
                    .get();
        } catch (Exception e) {
            log.warn("Unable to connect to nya: {}", e.getLocalizedMessage());
        }


        log.info("Unable to fetch document for url: {}", url);
        return null;
    }

    private Torrent extractTorrentFromElement(Element element) {
        log.info("Extracting Torrent info from Element: {}", element.text());
        String category = element.select("a").attr("href");
        String name = element.select("a").get(1).text();
        String sizeText = element.getElementsByClass("text-center").get(1).text();
        String downloadLink = NyaSettings.TRACKER_URL + element.getElementsByClass("text-center").select("a").get(0).attr("href").substring(1);
        String fullMagnet = element.getElementsByClass("text-center").select("a").get(1).attr("href");
        String magnet = fullMagnet.substring(0, fullMagnet.indexOf("&"));
        int seeders;
        int leechers;
        int completed;
        try {
            seeders = Integer.parseInt(element.getElementsByClass("text-center").get(3).text());
            leechers = Integer.parseInt(element.getElementsByClass("text-center").get(4).text());
            completed = Integer.parseInt(element.getElementsByClass("text-center").get(5).text());
        } catch (Exception e) {
            log.warn("Error while trying to parse values");
            seeders = 0;
            leechers = 0;
            completed = 0;
        }
        String url = NyaSettings.TRACKER_URL + element.select("a").get(1).attr("href").substring(1);
        Date date = new Date(Long.parseLong(element.getElementsByClass("text-center")
                .get(2).attr("data-timestamp")) * 1000);
        return new Torrent(category, name, sizeText, seeders, leechers, completed, magnet, url, date, downloadLink);
    }

}
