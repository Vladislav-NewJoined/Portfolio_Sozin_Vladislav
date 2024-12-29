package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.example.functions.ImageOperation;
import org.example.commands.AppBotCommand;
import org.example.commands.BotCommonCommands;
import org.example.functions.FilterOperation;
import org.example.utils.ImageUtils;
import org.example.utils.PhotoMessageUtils;

import java.io.*;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Bot extends TelegramLongPollingBot {

    HashMap<String, Message> messages = new HashMap<>();

    Class[] commandClasses = new Class[]{BotCommonCommands.class};

    @Override
    public String getBotUsername() {
        return "kkkllll_005_bot"; // Название вашего бота
    }

    @Override
    public String getBotToken() {
        return "6882256834:AAH5Fg-wUdKw7Rdqj8s9kXDgVt0R08tDnlY"; // Токен вашего бота
    }

    private String runCommand(String text) {
        BotCommonCommands commands = new BotCommonCommands();
        Method[] classMethods = commands.getClass().getDeclaredMethods();

        for (Method method : classMethods) {
            if (method.isAnnotationPresent(AppBotCommand.class)) {
                AppBotCommand annotation = method.getAnnotation(AppBotCommand.class);
                if (annotation.name().equals(text)) {
                    try {
                        method.setAccessible(true);
                        return (String) method.invoke(commands);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return "Команда не из кнопки";
    }

    private SendMessage runCommonCommand(Message message) throws InvocationTargetException, IllegalAccessException {
        String text = message.getText();
        BotCommonCommands commands = new BotCommonCommands();
        Method[] classMethods = commands.getClass().getDeclaredMethods();
        for (Method method : classMethods) {
            if (method.isAnnotationPresent(AppBotCommand.class)) {
                AppBotCommand command = method.getAnnotation(AppBotCommand.class);
                if (command.name().equals(text)) {
                    method.setAccessible(true);
                    String responseText = (String) method.invoke(commands);
                    if (responseText != null) {
                        SendMessage sendMessage = new SendMessage();
                        sendMessage.setChatId(message.getChatId().toString());
                        sendMessage.setText(responseText);
                        return sendMessage;
                    }
                }
            }
        }
        return null;
    }

    private SendMessage runPhotoMessge(Message message) {
        List<org.telegram.telegrambots.meta.api.objects.File> files = getFilesByMessage(message);
        if (files.isEmpty()) {
            return null;
        }
        String chatId = message.getChatId().toString();
        messages.put(message.getChatId().toString(), message);
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        ArrayList<KeyboardRow> allKeyboardRows = new ArrayList<>(getKeyboardRows(FilterOperation.class));
        replyKeyboardMarkup.setKeyboard(allKeyboardRows);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        sendMessage.setChatId(chatId);
        sendMessage.setText("Выберите фильтр");
        return sendMessage;
    }

    private Object runPhotoFilter(Message newMessage) {
        final String text = newMessage.getText();
        ImageOperation operation = ImageUtils.getOperation(text);
        if (operation == null) return null;
        String chatId = newMessage.getChatId().toString();
        Message photoMessage = messages.get(chatId);
        if (photoMessage != null) {
            List<org.telegram.telegrambots.meta.api.objects.File> files = getFilesByMessage(photoMessage);
            try {
                List<String> paths = PhotoMessageUtils.savePhotos(files, getBotToken());
                return preparePhotoMessage(paths, operation, chatId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText("Отправьте фото, чтобы воспользоваться фильтром");
            return sendMessage;
        }
        return null;
    }

    private List<org.telegram.telegrambots.meta.api.objects.File> getFilesByMessage(Message message) {
        List<PhotoSize> photoSizes = message.getPhoto();
        if (photoSizes == null) return new ArrayList<>();
        ArrayList<org.telegram.telegrambots.meta.api.objects.File> files = new ArrayList<>();
        for (PhotoSize photoSize : photoSizes) {
            final String fileId = photoSize.getFileId();
            try {
                files.add(sendApiMethod(new GetFile(fileId)));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        return files;
    }

    private SendMediaGroup preparePhotoMessage(List<String> localPaths, ImageOperation operation, String chatId) throws Exception {
        SendMediaGroup mediaGroup = new SendMediaGroup();
        ArrayList<InputMedia> medias = new ArrayList<>();
        for (String path : localPaths) {
            InputMedia inputMedia = new InputMediaPhoto();
            PhotoMessageUtils.processingImage(path, operation);
            inputMedia.setMedia(new File(path), "path");
            medias.add(inputMedia);
        }
        mediaGroup.setMedias(medias);
        mediaGroup.setChatId(chatId);
        return mediaGroup;
    }

    private ReplyKeyboardMarkup getKeyboard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        ArrayList<KeyboardRow> allKeyboardRows = new ArrayList<>();
        allKeyboardRows.addAll(getKeyboardRows(BotCommonCommands.class));
        allKeyboardRows.addAll(getKeyboardRows(FilterOperation.class));

        replyKeyboardMarkup.setKeyboard(allKeyboardRows);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        return replyKeyboardMarkup;
    }

    private ArrayList<KeyboardRow> getKeyboardRows(Class someClass) {
        Method[] classMethods = someClass.getDeclaredMethods();
        ArrayList<AppBotCommand> commands = new ArrayList<>();
        for (Method method : classMethods) {
            if (method.isAnnotationPresent(AppBotCommand.class)) {
                commands.add(method.getAnnotation(AppBotCommand.class));
            }
        }

        // Добавляем новую кнопку "Админ. панель" на клавиатуру
        commands.add(new AppBotCommand() {
            /**
             * Returns the annotation interface of this annotation.
             *
             * @return the annotation interface of this annotation
             * @apiNote Implementation-dependent classes are used to provide
             * the implementations of annotations. Therefore, calling {@link
             * Object#getClass getClass} on an annotation will return an
             * implementation-dependent class. In contrast, this method will
             * reliably return the annotation interface of the annotation.
             * @see Enum#getDeclaringClass
             */
            @Override
            public Class<? extends Annotation> annotationType() {
                return null;
            }

            @Override
            public String name() {
                return "Админ. панель";
            }

            @Override
            public String description() {
                return "Админ. панель";
            }

            @Override
            public boolean showInHelp() {
                return false;
            }

            @Override
            public boolean showInKeyboard() {
                return true;
            }
        });

        // Добавляем код формирования клавиатуры, включая новую кнопку
        ArrayList<KeyboardRow> keyboardRows = new ArrayList<>();
        int columnCount = 3;
        int rowsCount = commands.size() / columnCount + ((commands.size() % columnCount == 0) ? 0 : 1);
        for (int rowIndex = 0; rowIndex < rowsCount; rowIndex++) {
            KeyboardRow row = new KeyboardRow();
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                int index = rowIndex * columnCount + columnIndex;
                if (index >= commands.size()) continue;
                AppBotCommand command = commands.get(index);
                KeyboardButton keyboardButton = new KeyboardButton(command.name());
                row.add(keyboardButton);
            }
            keyboardRows.add(row);
        }
        return keyboardRows;
    }

    private void saveImage(String url, String fileName) throws IOException {
        URL urlModel = new URL(url);
        InputStream inputStream = urlModel.openStream();
        OutputStream outputStream = new FileOutputStream(fileName);
        byte[] b = new byte[2048];
        int length;
        while ((length = inputStream.read(b)) != -1) {
            outputStream.write(b, 0, length);
        }
        inputStream.close();
        outputStream.close();
    }

    private void printUserInfo(User user) {
        System.out.println("User ID: " + user.getId() + ", Username: " + user.getUserName());
    }

    private boolean botEnabled = true; // Флаг состояния бота (по умолчанию включен)

    private boolean isBotEnabled() {
        return botEnabled;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String data = callbackQuery.getData();

            if(data.equals("users_list")) {
                SendMessage sendMessage = getUsersList();
                sendMessage.setChatId(((CallbackQuery) callbackQuery).getMessage().getChatId().toString());
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }

            AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
            answerCallbackQuery.setCallbackQueryId(callbackQuery.getId());
            try {
                execute(answerCallbackQuery);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String data = callbackQuery.getData();

            if (data.equals("on_off_bot")) {
                // Изменить состояние бота (включить или выключить)
                botEnabled = !botEnabled;

                SendMessage response = new SendMessage();
                response.setChatId(callbackQuery.getMessage().getChatId().toString());

                // Установить текст сообщения в зависимости от состояния бота
                if (botEnabled) {
                    response.setText("Бот включен");
                } else {
                    response.setText("Бот выключен");
                }

                try {
                    execute(response);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

                // Отправить ответ на нажатие кнопки
                AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
                answerCallbackQuery.setCallbackQueryId(callbackQuery.getId());
                try {
                    execute(answerCallbackQuery);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }

        Message message = update.getMessage();

        if (message != null) {
            User user = message.getFrom();
            if (user != null && user.getId().equals(5799431854L)) {
                printUserInfo(user);
            }
        }

        try {
            SendMessage responseTestMessage = runCommonCommand(message);
            if (responseTestMessage != null) {
                execute(responseTestMessage);
                return;
            }
            responseTestMessage = runPhotoMessge(message);
            if (responseTestMessage != null) {
                execute(responseTestMessage);
                return;
            }
            Object responseMediaMessage = runPhotoFilter(message);
            if (responseMediaMessage != null) {
                if (responseMediaMessage instanceof SendMediaGroup) {
                    execute((SendMediaGroup) responseMediaMessage);
                } else if (responseMediaMessage instanceof SendMessage) {
                    execute((SendMessage) responseMediaMessage);
                }
                return;
            }

            // Добавляем обновление inline клавиатуры с двумя командами
            if (message.hasText() && message.getText().equals("Админ. панель")) {
                String token = "6882256834:AAH5Fg-wUdKw7Rdqj8s9kXDgVt0R08tDnlY";
                String chatId = message.getChatId().toString();
                String endpoint = "https://api.telegram.org/bot" + token + "/sendMessage";

                String keyboard = "{\"inline_keyboard\":[[{\"text\":\"/users_list\", \"callback_data\":\"users_list\"},{\"text\":\"/on_off_bot\", \"callback_data\":\"on_off_bot\"}]]}";

                URL url = new URL(endpoint);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);

                String params = "chat_id=" + chatId + "&text=Choose a command:&reply_markup=" + keyboard;
                OutputStream out = connection.getOutputStream();
                out.write(params.getBytes());
                out.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuffer response = new StringBuffer();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();
                connection.disconnect();
            }

        } catch (InvocationTargetException | IllegalAccessException | TelegramApiException | IOException e) {
            e.printStackTrace();
        }
        if (message != null && message.hasText()) {
            String text = message.getText();
            if (text.equals("/users_list")) {
                // Обработка команды /users_list и отправка списка пользователей
                SendMessage response = getUsersList();
                try {
                    execute(response);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
            else if (text.equals("/on_off_bot")) {


                // Включить или выключить бота
                botEnabled = !botEnabled; // Переключаем состояние бота
                // Включить или выключить бота
                boolean botEnabled = !isBotEnabled(); // Переключаем состояние бота

                // Отправить сообщение в чат об изменении состояния бота
                SendMessage response = new SendMessage();
                response.setChatId(message.getChatId());

                if (botEnabled) {
                    response.setText("Бот включен");
                } else {
                    response.setText("Бот выключен");
                }

                try {
                    execute(response);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Метод для обработки команды /users_list
    private SendMessage getUsersList() {
        StringBuilder responseText = new StringBuilder();

        if(messages.isEmpty()) {
            responseText.append("Список пользователей пуст");
        } else {
            for (Message storedMessage : messages.values()) {
                User user = storedMessage.getFrom();
                responseText.append("User ID: ").append(user.getId()).append(", Username: ").append(user.getUserName()).append("\n");
            }
        }

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(5799431854L); // Укажите нужный chatId для отправки сообщения
        sendMessage.setText(responseText.toString());

        return sendMessage;
    }
}
