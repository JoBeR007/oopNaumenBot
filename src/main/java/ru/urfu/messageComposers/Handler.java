package ru.urfu.messageComposers;

import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageCaption;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.*;
import ru.urfu.botObjects.BotRequest;
import ru.urfu.botObjects.BotResponse;
import static ru.urfu.messageComposers.ResponseConstants.*;
import static ru.urfu.messageComposers.Constants.*;

import java.lang.*;
import java.io.IOException;
import java.util.List;

public class Handler {

    private final MessageComposer mc = new MessageComposer();

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
            setNewContentToMessage(callbackQuery, editTextResponse);
        }

        return new BotResponse(textResponse, photoResponse, documentResponse, editTextResponse, editCaptionResponse);
    }

    private void setNewContentToMessage (CallbackQuery callbackQuery, EditMessageText editTextResponse){
        String callData = callbackQuery.getData();
        String messageId = Integer.toString(callbackQuery.getMessage().getMessageId());

        if (callData.equals("next")) {
            editTextResponse.setMessageId(Integer.parseInt(messageId));
            editTextResponse.setText("Следующая страница");
        }

        else if (callData.equals("previous")) {
            editTextResponse.setMessageId(Integer.parseInt(messageId));
            editTextResponse.setText("Предыдущая страница");
        }
    }

    private void handleText (SendMessage textResponse, BotRequest request) throws IOException {
        switch (request.getInputText()) {
            case "/start" -> textResponse.setText(HELLO.getContent());

            case "/help" -> textResponse.setText(HELP.getContent());

            case "/rutracker" -> textResponse.setText(RUTRACKER.getContent());

            case "/nyaa" -> textResponse.setText(NYAA.getContent());

            default -> mc.getInformation(textResponse, request);

            //default -> textResponse.setText("Не понимаю, чего ты хочешь. Чтобы посмотреть список доступных команд, отправь /help.");
        }
    }
}