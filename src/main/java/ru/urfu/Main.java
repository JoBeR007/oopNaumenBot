package ru.urfu;

import lombok.extern.slf4j.Slf4j;
import ru.urfu.NyaParser.NyaParser;
import ru.urfu.RutrackerParser.RutrackerParser;
import ru.urfu.TorrentFileDownloader.TorrentFileDownloader;
import ru.urfu.model.Torrent;

import java.util.List;

@Slf4j
public class Main {
    public static void main(String[] args) {
        RutrackerParser rp = RutrackerParser.getInstance();
        List<Torrent> res = rp.search("dune");
        for(Torrent  t: res) {
            System.out.println(t);
        }
        TorrentFileDownloader tfd = new TorrentFileDownloader();

        NyaParser np = NyaParser.getInstance();
        List<Torrent> anime = np.search("naruto");
        for(Torrent t: anime) {
            System.out.println(t);
        }
        System.out.println(tfd.downloadTorrentFile(res.get(0).getTorrentDownloadLink(), true));
        System.out.println(tfd.downloadTorrentFile(anime.get(0).getTorrentDownloadLink(), false));

    }
}