package ru.urfu.messageComposers;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.urfu.botObjects.BotRequest;
import ru.urfu.model.Torrent;


import java.util.ArrayList;
import java.util.List;

public class InlineKeyboardEditor {


    public void setMarkupInline (SendMessage textResponse, List<Torrent> torrents) {
        InlineKeyboardMarkup markupInline = getMarkupInline(torrents);
            textResponse.setReplyMarkup(markupInline);
    }

    private InlineKeyboardMarkup getMarkupInline (List<Torrent> torrents) {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInlineNumbers = new ArrayList<>();
        // List<InlineKeyboardButton> rowInlineArrows = new ArrayList<>();
        // rowInlineArrows.add(getInlineKeyboardButton("previous", "<"));
        for (int i = 1; i <= torrents.size(); i += 1) {

            rowInlineNumbers.add(getInlineKeyboardButton(String.valueOf(i), String.valueOf(i)));
        }

        // rowInlineArrows.add(getInlineKeyboardButton("next", ">"));
        rowsInline.add(rowInlineNumbers);
        // rowsInline.add(rowInlineArrows);
        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }

    private InlineKeyboardButton getInlineKeyboardButton (String callbackData, String text) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setCallbackData(callbackData);
        button.setText(text);
        return button;
    }
}
