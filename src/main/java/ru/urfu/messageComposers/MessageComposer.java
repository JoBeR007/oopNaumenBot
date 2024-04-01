package ru.urfu.messageComposers;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.urfu.RutrackerParser.RutrackerParser;
import ru.urfu.botObjects.BotRequest;
import ru.urfu.model.Torrent;

import java.util.ArrayList;
import java.util.List;

import static ru.urfu.messageComposers.Constants.*;

public class MessageComposer {

    private final InlineKeyboardEditor inlineKeyboardEditor = new InlineKeyboardEditor();

    public void getInformation(SendMessage textResponse, BotRequest request) {
        List<Torrent> torrents = RutrackerParser.getInstance().search(request.getInputText());

        String torrentInf = parseTorrentList(torrents, request.getInputText());
        textResponse.setText(torrentInf);

        inlineKeyboardEditor.setMarkupInline(textResponse, torrents);
    }

    public String parseTorrentList (List<Torrent> torrents, String requestText){
        StringBuilder sb = new StringBuilder("По запросу _" + requestText + "_ найдено раздач в количестве _" + torrents.size() + "_:\n\n");
        for (int i = 0; i < torrents.size(); i += 1){
            sb.append(i + 1).append(". ").append(torrents.get(i).getName()).append(" (*Категория:* ").append(torrents.get(i).getCategory()).append(", *дургая инфа:* пук-пук)\n\n");
        }

        return sb.toString();
    }
}
