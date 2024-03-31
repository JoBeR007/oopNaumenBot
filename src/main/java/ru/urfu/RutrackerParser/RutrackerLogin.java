package ru.urfu.RutrackerParser;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Slf4j
public class RutrackerLogin {

    private static RutrackerLogin instance = null;
    private String trackerLogin;
    private String trackerPassword;
    private static boolean authenticated = false;
    private static Date cookiesDate;
    private static Map<String, String> cookies = new HashMap<>();

    private RutrackerLogin() {
        try {
            Properties prop = new Properties();
            InputStream input = new FileInputStream("src/main/resources/application.properties");
            prop.load(input);
            trackerLogin = prop.getProperty("tracker.login");
            trackerPassword = prop.getProperty("tracker.password");
        } catch (FileNotFoundException e) {
            log.error("No properties file with necessary data: {}", e.getLocalizedMessage());
        } catch (IOException e) {
            log.error("Properties file is poorly structured or does not contain required data: {}",
                    e.getLocalizedMessage());
        }
    }

    public static RutrackerLogin getInstance() {
        if (instance == null) {
            log.info("Instantiating RutrackerLogin");
            instance = new RutrackerLogin();
        }
        return instance;
    }

    public void login() {
        Connection.Response res = null;
        try {
            res = Jsoup.connect(RutrackerSettings.LOGIN_URL)
                    .data("login_username", trackerLogin,
                            "login_password", trackerPassword, "login", "Вход")
                    .method(Connection.Method.POST)
                    .execute();
        } catch (Exception e) {
            log.warn("Unable to login to Rutracker.org: {}", e.getLocalizedMessage());
        }

        if (res != null && res.statusCode() == 200) {
            if (res.cookies().size() > 0) {
                cookies = res.cookies();
                authenticated = true;
                cookiesDate = new Date();
                log.info("Authenticated, cookies loaded");
            }
        }

    }

    public boolean isAuthenticated() {
        if (cookiesDate != null && authenticated) {
            if (Duration.between(new Date().toInstant(), cookiesDate.toInstant()).toDays() > 2) {
                login();
            }
        }

        return authenticated;
    }

    public Map<String, String> getCookies() {
        log.info("Getting cookies");
        return cookies;
    }
}
