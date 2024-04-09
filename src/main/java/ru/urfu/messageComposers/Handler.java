package ru.urfu.messageComposers;

import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageCaption;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.*;
import static ru.urfu.Bot.*;

import ru.urfu.NyaParser.NyaParser;
import ru.urfu.RutrackerParser.RutrackerParser;
import ru.urfu.botObjects.BotRequest;
import ru.urfu.botObjects.BotResponse;
import ru.urfu.model.Torrent;

import static ru.urfu.messageComposers.ResponseConstants.*;


import java.lang.*;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


public class Handler {

    private final MessageComposer messageComposer = new MessageComposer();

    public BotResponse distribute(BotRequest request) throws IOException {
        SendMessage textResponse = new SendMessage();
        SendPhoto photoResponse = new SendPhoto();
        SendDocument documentResponse = new SendDocument();

        textResponse.enableMarkdown(true);

        EditMessageText editTextResponse = new EditMessageText();
        EditMessageCaption editCaptionResponse = new EditMessageCaption();

        String instruction = request.getInputText();
        List<PhotoSize> photo = request.getInputPhoto();
        Document document = request.getInputDocument();
        CallbackQuery callbackQuery = request.getInputCallbackQuery();


        if (instruction != null) {
            handleText(textResponse, request);
        }

        else if(photo != null) {
            textResponse.setText(NO_MEDIA.getContent());
        }

        else if (document != null) {
            textResponse.setText(NO_DOCUMENTS.getContent());
        }

        else if (callbackQuery != null) {
            //setNewContentToMessage(callbackQuery, editTextResponse);
            messageComposer.getTorrentInformation(documentResponse, callbackQuery, textResponse);
        }

        return new BotResponse(textResponse, photoResponse, documentResponse, editTextResponse, editCaptionResponse);
    }

    private void setNewContentToMessage (CallbackQuery callbackQuery, EditMessageText editTextResponse){
        String callData = callbackQuery.getData();
        String messageId = Integer.toString(callbackQuery.getMessage().getMessageId());

    }


    private void handleText (SendMessage textResponse, BotRequest request) throws IOException {
        //default -> textResponse.setText("Не понимаю, чего ты хочешь. Чтобы посмотреть список доступных команд, отправь /help.");
        switch (request.getInputText()) {
            case "/start" -> textResponse.setText(HELLO.getContent());

            case "/help" -> textResponse.setText(HELP.getContent());

            case "/rt" -> {
                textResponse.setText(RUTRACKER.getContent());
            }

            case "/nya" -> {
                textResponse.setText(NYAA.getContent());
            }

            default -> messageComposer.getTorrentListMessage(textResponse, request);
        }
    }
}