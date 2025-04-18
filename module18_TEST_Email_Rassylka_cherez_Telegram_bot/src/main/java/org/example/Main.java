package org.example;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static void main(String[] args) {
//        ИНСТРУКЦИИ НАХОДЯТСЯ В ФАЙЛЕ: README_ИНСТРУКЦИЯ.txt
//        Chat ID своего бота как узнать?:
//        Напишите боту @userinfobot или @myidbot
//        Отправьте команду /start или /getid
//        Chat ID моего бота:
//        @tess_SV
//        Id: 5799431854    - это Chat ID

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new MailingBot());
            System.out.println("Бот запущен!");
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
