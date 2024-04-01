package ru.urfu.messageComposers;

public enum ResponseConstants {
    HELLO("Привет!\n" +
            "Чтобы ознакомиться с функционалом бота, отправь /help!"),
    HELP("Гайд по работе с ботом будет чуть позже"),

    RUTRACKER("Введите запрос для поиска раздач на rutracker.org:"),

    NYAA ("Введите запрос для поиска раздач на nyaa.si:"),

    NO_MEDIA ("К сожалению, на данный момент наш бот не обрабатывает фото ;("),

    NO_DOCUMENTS ("К сожалению, на данный момент наш бот не обрабатывает документы ;(");


    private final String content;

    ResponseConstants(String content){
        this.content = content;
    }

    public String getContent(){
        return content;
    }
}
