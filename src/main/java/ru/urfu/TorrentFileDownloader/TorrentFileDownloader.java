package ru.urfu.TorrentFileDownloader;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import ru.urfu.RutrackerParser.RutrackerLogin;
import ru.urfu.RutrackerParser.RutrackerSettings;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class TorrentFileDownloader {
    private final RutrackerLogin auth;

    public TorrentFileDownloader() {
        auth = RutrackerLogin.getInstance();
    }

    public String downloadTorrentFile(String url, boolean cookiesRequired) {
        log.info("Fetching document from: {}", url);
        try {
            if (cookiesRequired) {
                if (!auth.isAuthenticated()) {
                    auth.login();
                }

                if (auth.isAuthenticated()) {
                    return downloadUsingHttpClient(url, true);
                } else {
                    log.warn("Authentication failed. Unable to download torrent file for url: {}", url);
                    return "";
                }
            } else {
                return downloadUsingHttpClient(url, false);
            }
        } catch (IOException e) {
            log.error("Error downloading torrent file from url: {}", url, e);
            return "";
        }
    }

    private String downloadUsingHttpClient(String url, boolean cookiesRequired) throws IOException {
        char delim = cookiesRequired ? '=' : '/';
        String ending = cookiesRequired ? ".torrent" : "";
        HttpGet request = new HttpGet(url);
        String name = url.substring(url.lastIndexOf(delim) + 1) + ending;
        if(cookiesRequired)
            request.addHeader("Cookie", cookiesToString(auth.getCookies()));


        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(request)) {

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                byte[] fileBytes = EntityUtils.toByteArray(response.getEntity());
                Path outputFilePath = Paths.get(RutrackerSettings.TORRENTS_PATH + name);
                Files.write(outputFilePath, fileBytes);
                log.info("Torrent file downloaded successfully for url: {}", url);
            } else {
                log.warn("Failed to download torrent file. HTTP Status Code: {}, url: {}",
                        response.getStatusLine().getStatusCode(), url);
            }
        }
        return name;
    }

    private String cookiesToString(Map<String, String> cookies) {
        return cookies.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("; "));
    }
}
