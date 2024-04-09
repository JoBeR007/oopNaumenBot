package ru.urfu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageCaption;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.urfu.botObjects.BotResponse;
import ru.urfu.handlers.*;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Bot extends TelegramLongPollingBot {
    List<UpdateHandler> handlers = new ArrayList<>();

    private static String USERNAME;
    private static String TOKEN;

    @Override
    public String getBotUsername() {
        return USERNAME;
    }

    @Override
    public String getBotToken(){
        return TOKEN;
    }

    public Bot(String username, String token) {
        USERNAME = username;
        TOKEN = token;

        handlers.add(new TextUpdate());
        handlers.add(new PhotoUpdate());
        handlers.add(new DocumentUpdate());
        handlers.add(new CallbackQueryUpdate());
    }

    private static final Logger LOG = LoggerFactory.getLogger(Bot.class);

    @Override
    public void onUpdateReceived(Update update) {
        BotResponse message = new BotResponse(null, null, null, null, null);
        //LOG.info(getInformationAboutUser(update));

        for (UpdateHandler handler : handlers) {
            if (handler.validate(update)) {
                try {
                    message = handler.handle(update);
                    break;
                }
                catch (IOException | TelegramApiException e) {
                    LOG.error(e.toString());
                }
            }
        }

        SendMessage messageText = message.getOutputText();
        SendPhoto messagePhoto = message.getOutputPhoto();
        SendDocument messageDocument = message.getOutputDocument();
        EditMessageText editText = message.getOutputEditText();
        EditMessageCaption editCaption = message.getOutputEditCaption();

        if (messageText == null && messagePhoto == null && messageDocument == null && editText == null && editCaption == null) {
            return;
        }

        try {
            if(messageText.getText() != null)
                execute(messageText);
            if (messagePhoto.getPhoto() != null)
                execute(messagePhoto);
            if (messageDocument.getDocument() != null) {
                execute(messageDocument);
                deleteTorrentFile();
            }
            if (editText.getText()!= null)
                execute(editText);
            if (editCaption.getCaption()!= null)
                execute(editCaption);
        }
        catch (TelegramApiException e) {
            LOG.error(e.toString());
        }
    }

    private String getInformationAboutUser (Update update) {
        String userName = update.getMessage().getChat().getUserName();
        long userID = update.getMessage().getChat().getId();

        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        Date date = new Date();

        return "Received message from @" +  userName + ", id = " + userID + " at " + dateFormat.format(date);
    }

    private static void deleteTorrentFile() {
        File mainFolder = new File(".");
        File[] files = mainFolder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".torrent")) {
                    if (file.delete()) {
                        LOG.info("Удален файл: " + file.getName());
                    } else {
                        LOG.info("Не удалось удалить файл: " + file.getName());
                    }
                }
            }
        }

        else {
            LOG.info("В директории нет файлов.");
        }
    }
}
