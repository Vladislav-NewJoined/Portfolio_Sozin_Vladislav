package task14_5_1.zadanye8.controllers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task14_5_1.zadanye8.models.Chat;
import task14_5_1.zadanye8.models.Quote;
import task14_5_1.zadanye8.repositories.ChatRepository;
import task14_5_1.zadanye8.services.BashParser;
import task14_5_1.zadanye8.services.QuoteService;

import java.util.Map;

@Service
public class BotController {

    @Autowired
    ChatRepository chatRepository;

    @Autowired
    QuoteService service;

    private final TelegramBot bot;

    public BotController(){
// Create your bot passing the token received from @BotFather
        String botToken = "6882256834:AAH5Fg-wUdKw7Rdqj8s9kXDgVt0R08tDnlY";
        bot = new TelegramBot(botToken);

// Register for updates
        bot.setUpdatesListener(updates -> {

            for (Update update : updates) {
                handleUpdate(update);
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    private void handleUpdate(Update update) {
        String text = update.message().text();
        long chatId = update.message().chat().id();
        var rawChat = chatRepository.findByChatIdEquals(chatId);
        Chat chat;

        if (rawChat.isPresent()) { // Проверка на присутствие значения
            chat = rawChat.get();
        } else {
            var _chat = new Chat();
            _chat.setChatId(chatId);
            _chat.setLastId(0);
            chat = chatRepository.save(_chat);
        }
        switch(text){
            case "/start":
            case "/next":
                sendNextQuote(chat);
                break;
            case "/prev":
                sendPrevQuote(chat);
                break;
            case "/rand":
                sendRandom(chat);
                break;
        }
    }

    private void sendNextQuote(Chat chat) {
        Quote quote = null;
        int newId = chat.getLastId();
        while (quote == null) {
            newId++;
            quote = service.getById(newId);

        }
        chat.setLastId(quote.getQuoteid());
        chatRepository.save(chat);
        sendText(chat.getChatId(), quote.getText());
    }

    private void sendPrevQuote(Chat chat) {
        Quote quote = null;
        int newId = chat.getLastId();
        while (quote == null) {
            newId--;
            if (newId < 2) newId = 2;
            quote = service.getById(newId);

        }
        chat.setLastId(quote.getQuoteid());
        chatRepository.save(chat);
        sendText(chat.getChatId(), quote.getText());
    }

    private void sendRandom(Chat chat) {
        Quote quote = service.getRandom();
        chatRepository.save(chat);
        sendText(chat.getChatId(), quote.getText());
    }

    private void sendText(long chatId, String text) {
        bot.execute(new SendMessage(chatId, text));
    }

}
