Инструкция, как пользоваться кодом в файле README_ИНСТРУКЦИЯ в корневой папке проекта
Название бота для этого проекта (переименованное, просто для меня в списке ботов):
Mailing_bot
Название этого бота правильное:
@BES_Mailing_bot
Ссылка на этот бот:
https://t.me/BES_Mailing_bot
Токен этого бота:
7658983765:AAFaNCkR8D5O74u-YkMVZyjxuePRXY1NZ64
Java проект по контексту поиска этот: Email_Rassylka_cherez_Telegram_bot

Команды в терминале bash для проверки работы проекта:
1. Для Telegram-бота:
bash
mvn compile exec:java -Dexec.mainClass="org.example.Main"
2. Для теста email без бота:
bash
mvn compile exec:java -Dexec.mainClass="org.example.SendEmail"
3. Ещё проверка:
mvn clean compile exec:java

Здесь инфо, как создать рассылку: https://dev.to/suprsend/how-to-send-email-notifications-using-java-3-methods-with-code-examples-2ckd
Как создать пароль для внешнего сервиса , для внешнего приложения:
• Перейдите на страницу безопасности вашей учетной записи Mail.ru.
• Найдите раздел, связанный с "Паролями приложений" или "Доступом приложений".
• Создайте новый пароль приложения. Вам будет предоставлен уникальный код.
Создаётся здесь: https://id.mail.ru/security
В итоге создался здесь: https://account.mail.ru/user/2-step-auth/passwords?back_url=https%3A%2F%2Fid.mail.ru%2Fsecurity
