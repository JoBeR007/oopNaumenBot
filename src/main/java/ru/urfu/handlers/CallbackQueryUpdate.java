package ru.urfu.handlers;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.urfu.botObjects.BotRequest;
import ru.urfu.botObjects.BotResponse;

import java.io.IOException;

public class CallbackQueryUpdate implements UpdateHandler {
    @Override
    public boolean validate(Update update) {
        return update.hasCallbackQuery();
    }

    @Override
    public BotResponse handle(Update update) throws IOException {
        String chatId = Long.toString(update.getCallbackQuery().getMessage().getChatId());
        CallbackQuery callbackQuery = update.getCallbackQuery();

        BotRequest userAction = new BotRequest(null, null, null, callbackQuery);
        callbackQuery = userAction.getInputCallbackQuery();
        BotRequest request = new BotRequest(null, null, null, callbackQuery);

        return buildResponse(chatId, request);
    }
}
