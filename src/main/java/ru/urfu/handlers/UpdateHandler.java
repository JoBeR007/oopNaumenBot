package ru.urfu.handlers;

import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageCaption;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.urfu.botObjects.*;
import ru.urfu.messageComposers.Handler;

import java.io.IOException;

public interface UpdateHandler {
    boolean validate(Update update);

    BotResponse handle(Update update) throws IOException, TelegramApiException;

    default BotResponse buildResponse (String chatId, BotRequest request) throws IOException {
        Handler handler = new Handler();

        BotResponse distributedData = handler.distribute(request);

        SendPhoto photoResponse = distributedData.getOutputPhoto();
        photoResponse.setChatId(chatId);

        SendDocument documentResponse = distributedData.getOutputDocument();
        documentResponse.setChatId(chatId);

        SendMessage textResponse = distributedData.getOutputText();
        textResponse.setChatId(chatId);

        EditMessageText editTextResponse = distributedData.getOutputEditText();
        editTextResponse.setChatId(chatId);

        EditMessageCaption editCaptionResponse = distributedData.getOutputEditCaption();
        editCaptionResponse.setChatId(chatId);

        return new BotResponse(textResponse, photoResponse, documentResponse, editTextResponse, editCaptionResponse);
    }
}