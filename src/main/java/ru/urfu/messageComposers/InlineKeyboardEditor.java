package ru.urfu.messageComposers;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.urfu.model.Torrent;

import static ru.urfu.messageComposers.Constants.*;

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
        List<InlineKeyboardButton> rowInlineArrows = new ArrayList<>();

        for (int i = 1; i <= torrents.size(); i += 1) {
            InlineKeyboardButton buttonNumber = new InlineKeyboardButton();
            buttonNumber.setCallbackData(String.valueOf(i));
            buttonNumber.setText(String.valueOf(i));
            rowInlineNumbers.add(buttonNumber);
        }

        InlineKeyboardButton rowPrevious = new InlineKeyboardButton();
        rowPrevious.setCallbackData("previous");
        rowPrevious.setText("<");
        rowInlineArrows.add(rowPrevious);

        InlineKeyboardButton rowNext = new InlineKeyboardButton();
        rowNext.setCallbackData("next");
        rowNext.setText(">");
        rowInlineArrows.add(rowNext);

        rowsInline.add(rowInlineNumbers);
        rowsInline.add(rowInlineArrows);
        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }
}
