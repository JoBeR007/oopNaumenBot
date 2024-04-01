package ru.urfu;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class Main {
    // private static final Map<String, String> envParams = System.getenv();

    public static void main(String [] args) throws TelegramApiException {
        String botToken = "";
        String botName = "";
        try {
            Properties prop = new Properties();
            InputStream input = new FileInputStream("src/main/resources/application.properties");
            prop.load(input);
            botToken = prop.getProperty("bot.token");
            botName = prop.getProperty("bot.name");
        } catch (FileNotFoundException e) {
            log.error("No properties file with necessary data: {}", e.getLocalizedMessage());
        } catch (IOException e) {
            log.error("Properties file is poorly structured or does not contain required data: {}",
                    e.getLocalizedMessage());
        }

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(new Bot(botName, botToken));
    }
}
