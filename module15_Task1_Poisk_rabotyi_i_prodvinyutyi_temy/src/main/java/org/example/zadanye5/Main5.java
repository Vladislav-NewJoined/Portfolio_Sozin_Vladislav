package org.example.zadanye5;

public class Main5 {
    public static void main(String[] args) {
        System.out.println("""
            Модуль 15. Поиск работы и продвинутые темы. Задание №1. Проект:
            Шаг 5:
            Создаём новую ветку и добавляем в проект внесённые изменения
            Добавим к проекту пустой CSS-файл и подключим его к HTML. После этого в меню редактора
            появятся два цвета: HTML-файл подсветится оранжевым, а CSS-файл — зелёным.
            Оранжевый означает, что файл уже есть в удалённом репозитории и его нужно обновить.
            Зелёный — файла нет в репозитории. Переходим в GitHub Desktop и добавляем коммит для этого изменения.
            Подсветка файлов в меню меняется после добавления или редактирования файлов — это подсказка,
            чтобы мы не забывали обновлять репозиторий:
            Если мы откроем созданную страницу в браузере, то это будет несколько строчек текста на
            белом фоне. Представим такую ситуацию: нам нельзя изменять код проекта, но нужно
            посмотреть, как будет выглядеть страница на красном фоне. Чтобы сделать это — добавим
            в репозиторий новую ветку:
            Переходим в GitHub Desktop.
            Открываем раздел Current Branch, нажимаем кнопку New Branch, пишем название новой ветки
            и кликаем Create New Branch.
            Возвращаемся в редактор кода и тестируем идею.
            После создания новой ветки не забудьте нажать на Push origin, чтобы изменения попали в
            удалённый репозиторий на сайте GitHub
            Создаём новую ветку в GitHub Desktop:
            Предположим, наша идея с красным фоном оказалась удачной
            и код нужно залить в основную ветку. Чтобы это сделать, переходим сайт GitHub, нажимаем кнопку
            Сompare & pull request и подтверждаем изменения кнопкой Merge pull request. Последний шаг —
            переходим в GitHub Desktop, кликаем Fetch origin и синхронизируемся с удалённым репозиторием.
            Теперь код из дополнительной ветки попал в основную, а изменения есть на ПК и в облаке..
            Для слияния веток нужно выполнить несколько подтверждений на GitHub:

            Решение:
        """);

        System.out.println("""
            Добавил в 'Visual Studio Code' к проекту 'n240221_Module1_Task1_Zadaniya1-16' пустой CSS-файл и
            подключил его к HTML.
            Добавил в 'Visual Studio Code' в папке проекта новую ветку 'Red-fone'.
            С помощью кнопки 'Publish branch' в 'GitHub Desktop' перенёс изменения в репозиторий на GitHub.
            Для иллюстрации работы с ветками и их слиянием, в репозитории проекта на GitHub перешёл
            в ту часть проекта, которая находится в ветке 'Red-fone', клонировал проект в 'GitHub Desktop',
            после этого зашёл в проект из приложения 'Visual Studio Code' и создал в корневой папке
            текстовый файл 'addSomeCode.txt'.
            Написал в нём следующий текст:
            'Some change in code in new branch 'Red-fone'.'
            В 'GitHub Desktop' отправил изменения на сервер в репозиторий GitHub.
            Зашёл в репозиторий GitHub, удостоверился, что там в ветке "Red-fone' есть файл 'addSomeCode.txt',
            а в ветке 'master' нет такого файла.
            Для тестирования вернулся в приложении 'Visual Studio Code' в файл 'addSomeCode.txt' и немного изменил его код.
            Сохранил, закрыл 'Visual Studio Code'.
            В 'GitHub Desktop' "запушил" изменения в репозиторий GitHub.
            Далее решил задачу переноса (или добавления) изменений из ветки "Red-fone' в ветку 'master'
            следующим образом:
            - открыл проект в 'GitHub Desktop'. Вернулся на ветку master. В верхнем меню нажал на вкладку 'Branch'. и
            выбрал в выпадающем меню пункт 'Merge into current branch' -> 'Create a merge commit' -> Push origin.
            - Вернулся в репозиторий GitHub, обновил его и убедился, что файл 'addSomeCode.txt' добавился также в
            ветку master.
            При необходимости добавленную позже ветку 'Red-fone' можно удалить.
            Ссылка на репозиторий GitHub на этот проект: https://github.com/Vladislav-NewJoined/n240221_Module1_Task1_Zadaniya1-16.git""");
    }
}