package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Main extends TelegramLongPollingBot {

    private final Map<Long, Integer> levels = new HashMap<>();

    private final List<String> messages = Arrays.asList(
            "\n" +
                    "Ґа-ґа-ґа!\n" +
                    "Вітаємо у боті біолабораторії «Батько наш Бандера».\n" +
                    "\n" +
                    "Ти отримуєш гусака №71\n" +
                    "\n" +
                    "Цей бот ми створили для того, щоб твій гусак прокачався з рівня звичайної свійської худоби до рівня біологічної зброї, здатної нищити ворога. \n" +
                    "\n" +
                    "Щоб звичайний гусак перетворився на бандерогусака, тобі необхідно:\n" +
                    "✔️виконувати завдання\n" +
                    "✔️переходити на наступні рівні\n" +
                    "✔️заробити достатню кількість монет, щоб придбати Джавеліну і зробити свєрхтра-та-та.\n" +
                    "\n" +
                    "*Гусак звичайний.* Стартовий рівень.\n" +
                    "Бонус: 5 монет.\n" +
                    "Обери завдання, щоб перейти на наступний рівень\n",

                    "*Вітаємо на другому рівні! Твій гусак - біогусак.*\n" +
                            "Баланс: 20 монет. \n" +
                            "Обери завдання, щоб перейти на наступний рівень",

                    "*Вітаємо на третьому рівні! Твій гусак - бандеростажер.*\n" +
                            "Баланс: 35 монет. \n" +
                            "Обери завдання, щоб перейти на наступний рівень",

                    "*Вітаємо на останньому рівні! Твій гусак - готова біологічна зброя - бандерогусак.*\n" +
                            "Баланс: 50 монет. \n" +
                            "Тепер ти можеш придбати Джавелін і глушити чмонь",

                    "*Джавелін твій. Повний вперед!*"
    );

    public static void main(String[] args) throws TelegramApiException {

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);

        telegramBotsApi.registerBot(new Main());
    }

    @Override
    public String getBotUsername() {
        return "anonymousGusBanderyBot";
    }

    @Override
    public String getBotToken() {
        return "5861965822:AAF_imxqjDjzd9MO-A8xDpp6dKdn0GybEo8";
    }

    @Override
    public void onUpdateReceived(Update update) {
        long chatId = getChatId(update);

        // START
        if (update.hasMessage() && update.getMessage().getText().equals("/start")) {
            // send image
            sendImage("level-1", chatId);

            // send message
            SendMessage message = createMessage(messages.get(0));

            message.setChatId(chatId);

            List<String> options = Arrays.asList(
                    "Сплести маскувальну сітку (+15 монет)",
                    "Зібрати кошти патріотичними піснями (+15 монет)",
                    "Вступити в Міністерство Мемів України (+15 монет)",
                    "Запустити волонтерську акцію (+15 монет)",
                    "Вступити до лав тероборони (+15 монет)"
            );

            options = getThreeRandomOptions(options);

            attachButtons(message, Map.of(
                    options.get(0), "level_1_task",
                    options.get(1), "level_1_task",
                    options.get(2), "level_1_task"
            ));

            sendApiMethodAsync(message);

        }

        if (update.hasCallbackQuery()) {
            // LVL 2
            if (update.getCallbackQuery().getData().equals("level_1_task") && getLevel(chatId) == 1) {
                // set level
                setLevel(chatId, 2);

                // send image lvl2
                sendImage("level-2", chatId);

                // send msg
                SendMessage message = createMessage(messages.get(1));

                List<String> options = Arrays.asList(
                        "Зібрати комарів для нової біологічної зброї (+15 монет)",
                        "Пройти курс молодого бійця (+15 монет)",
                        "Задонатити на ЗСУ (+15 монет)",
                        "Збити дрона банкою огірків (+15 монет)",
                        "Зробити запаси коктейлів Молотова (+15 монет)"
                );

                options = getThreeRandomOptions(options);

                attachButtons(message, Map.of(
                        options.get(0), "level_2_task",
                        options.get(1), "level_2_task",
                        options.get(2), "level_2_task"
                ));

                message.setChatId(chatId);

                sendApiMethodAsync(message);
            }
            // LVL 3
            if (update.getCallbackQuery().getData().equals("level_2_task") && getLevel(chatId) == 2) {
                setLevel(chatId, 3);

                // send image lvl3
                sendImage("level-3", chatId);

                // send msg
                SendMessage message = createMessage(messages.get(2));

                message.setChatId(chatId);

                List<String> options = Arrays.asList(
                        "Злітати на тестовий рейд по чотирьох позиціях (+15 монет)",
                        "Відвезти гуманітарку на передок (+15 монет)",
                        "Знайти зрадника та здати в СБУ (+15 монет)",
                        "Навести арту на орків (+15 монет)",
                        "Притягнути танк трактором (+15 монет)"
                );

                options = getThreeRandomOptions(options);

                attachButtons(message, Map.of(
                        options.get(0), "level_3_task",
                        options.get(1), "level_3_task",
                        options.get(2), "level_3_task"
                ));

                sendApiMethodAsync(message);

            }
            // LVL 4
            if (update.getCallbackQuery().getData().equals("level_3_task") && getLevel(chatId) == 3) {
                setLevel(chatId, 4);

                // send image lvl3
                sendImage("level-4", chatId);

                // send msg
                SendMessage message = createMessage(messages.get(3));

                message.setChatId(chatId);

                List<String> options = Arrays.asList(
                        "Купити Джавелін (50 монет)"
                );

                attachButtons(message, Map.of(
                        options.get(0), "level_4_task"
                ));

                sendApiMethodAsync(message);

            }
            // FINAL
            if (update.getCallbackQuery().getData().equals("level_4_task") && getLevel(chatId) == 4) {
                setLevel(chatId, 4);

                // send image lvl3
                sendImage("final", chatId);

                // send msg
                SendMessage message = createMessage(messages.get(4));

                message.setChatId(chatId);

                sendApiMethodAsync(message);
            }

        }
    }

    private long getChatId(Update update) {
        return update.hasMessage() ? update.getMessage().getFrom().getId() :
                (update.hasCallbackQuery() ? update.getCallbackQuery().getFrom().getId() : 0);
    }

    private SendMessage createMessage(String text) {
        SendMessage message = new SendMessage();

        message.setText(new String(text.getBytes(), StandardCharsets.UTF_8));
        message.setParseMode("markdown");

        return message;
    }

    private void attachButtons(SendMessage message, Map<String, String> buttons) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        // заповнюємо об'єкт keyboard кнопками
        for (String buttonName : buttons.keySet()) {
            String buttonValue = buttons.get(buttonName);

            InlineKeyboardButton button = new InlineKeyboardButton();

            button.setText(new String(buttonName.getBytes(), StandardCharsets.UTF_8));
            button.setCallbackData(buttonValue);

            keyboard.add(Arrays.asList(button));
        }

        markup.setKeyboard(keyboard);
        message.setReplyMarkup(markup);
    }

    private void sendImage(String name, Long chatId) {
        SendAnimation animation = new SendAnimation();

        InputFile inputFile = new InputFile();

        inputFile.setMedia(new File("images/" + name + ".gif"));

        animation.setAnimation(inputFile);
        animation.setChatId(chatId);

        executeAsync(animation);
    }

    private int getLevel(Long chatId) {
        return levels.getOrDefault(chatId, 1);
    }

    private void setLevel(Long chatId, int level) {
        levels.put(chatId, level);
    }

    private List<String> getThreeRandomOptions(List<String> variantsOptions) {
        ArrayList<String> copy = new ArrayList<>(variantsOptions);

        Collections.shuffle(copy);

        return copy.subList(0, 3);
    }

}