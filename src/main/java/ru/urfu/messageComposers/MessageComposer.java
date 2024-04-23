package ru.urfu.messageComposers;

import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import ru.urfu.NyaParser.NyaParser;
import ru.urfu.RutrackerParser.RutrackerParser;
import ru.urfu.botObjects.BotRequest;
import ru.urfu.model.LastTorrentList;
import ru.urfu.model.Torrent;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ru.urfu.messageComposers.ResponseConstants.*;


public class MessageComposer {

    private final InlineKeyboardEditor inlineKeyboardEditor = new InlineKeyboardEditor();

    public void getTorrentListMessage(SendMessage textResponse, BotRequest request) {
        List<Torrent> torrents;
        String requestText = request.getInputText().replaceAll("^\\s+", "");

        if (requestText.startsWith(RUTRACKER_MODE.getContent())) {
            requestText = requestText.replaceFirst(RUTRACKER_MODE.getContent(), "");
            torrents = RutrackerParser.getInstance().search(requestText);
            if (torrents.size() == 0) {
                textResponse.setText(EMPTY_SEARCH_RESULT.getContent());
                return;
            }
            LastTorrentList.getInstance().setList((ArrayList<Torrent>) torrents);
        }

        else if (requestText.startsWith(NYAA_MODE.getContent())) {
            requestText = requestText.replaceFirst(NYAA_MODE.getContent(), "");
            torrents = NyaParser.getInstance().search(requestText);
            if (torrents.size() == 0) {
                textResponse.setText(EMPTY_SEARCH_RESULT.getContent());
                return;
            }
            LastTorrentList.getInstance().setList((ArrayList<Torrent>) torrents);
        }

        else {
            textResponse.setText(WRONG_REQUEST.getContent());
            return;
        }

        String torrentInf = parseTorrentList(torrents, requestText);
        textResponse.setText(torrentInf);

        inlineKeyboardEditor.setMarkupInline(textResponse, torrents);
    }

    public void getTorrentInformation (SendDocument documentResponse, CallbackQuery callbackQuery, SendMessage textResponse) throws IOException {
        String callBackData = callbackQuery.getData();

        Torrent torrent = LastTorrentList.getInstance().getList().get(Integer.parseInt(callBackData)-1);
        textResponse.setText("Magnet-ссылка для *" + torrent.getName() + "*:\n```" + torrent.getMagnet() + "```");

        Path path = new FileDownloader().downloadFile(torrent.getTorrentDownloadLink(), Paths.get(torrent.getName()+".torrent"));
        documentResponse.setDocument(new InputFile(path.toFile()));
    }

    private String parseTorrentList (List<Torrent> torrents, String requestText){
        StringBuilder sb = new StringBuilder("По запросу _" + requestText + "_ найдено раздач в количестве _" + torrents.size() + "_:\n\n");

        int maxArraySizeValue = Math.min(torrents.size(), 8);

        for (int i = 0; i < maxArraySizeValue; i += 1){
            sb
                    .append(i + 1)
                    .append(". ")
                    .append(torrents.get(i).getName())
                    .append(" (*Категория:* ")
                    .append(torrents.get(i).getCategory())
                    .append(", *Размер:* ")
                    .append(torrents.get(i).getSize())
                    .append(", *Число скачиваний:* ")
                    .append(torrents.get(i).getCompleted())
                    .append(", *Число личей:* ")
                    .append(torrents.get(i).getLeechers())
                    .append(", *Число сидов:* ")
                    .append(torrents.get(i).getSeeders())
                    .append(")\n\n");
        }

        return sb.toString();
    }

    public String parseTorrentCard (Torrent torrent) {
        return torrent.getName() + "\n" +
                " (*Категория:* " +
                torrent.getCategory() + "\n" +
                ", *Размер:* " +
                torrent.getSize() + "\n" +
                ", *Число скачиваний:* " +
                torrent.getCompleted() + "\n" +
                ", *Число личей:* " +
                torrent.getLeechers() + "\n" +
                ", *Число сидов:* " +
                torrent.getSeeders() + "\n";
    }
}
