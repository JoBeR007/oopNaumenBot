package ru.urfu.botObjects;

import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageCaption;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

public class BotResponse extends SendMessage {
    private final SendMessage outputText;

    private final SendPhoto outputPhoto;

    private final SendDocument outputDocument;

    private final EditMessageText outputEditText;

    private final EditMessageCaption outputEditCaption;

    public BotResponse(SendMessage outputText, SendPhoto outputPhoto, SendDocument outputDocument, EditMessageText outputEditText, EditMessageCaption outputEditCaption) {
        this.outputText = outputText;
        this.outputPhoto = outputPhoto;
        this.outputDocument = outputDocument;
        this.outputEditText = outputEditText;
        this.outputEditCaption = outputEditCaption;
    }

    public SendMessage getOutputText() {
        return outputText;
    }

    public SendPhoto getOutputPhoto()
    {
        return outputPhoto;
    }

    public SendDocument getOutputDocument()
    {
        return outputDocument;
    }

    public EditMessageText getOutputEditText() {return outputEditText;}

    public EditMessageCaption getOutputEditCaption () {return  outputEditCaption;}
}