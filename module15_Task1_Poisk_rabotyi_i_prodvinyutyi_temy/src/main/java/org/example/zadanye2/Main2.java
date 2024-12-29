package org.example.zadanye2;

public class Main2 {
    public static void main(String[] args) {
        System.out.println("""
            Модуль 15. Поиск работы и продвинутые темы. Задание №1. Проект:\s
            Шаг 2:
            Добавляем удалённый репозиторий
            Репозиторий — это файловое хранилище проектов. На бесплатном тарифе можно загружать до 500 МБ
            данных и создавать неограниченное количество репозиториев.
            Чтобы создать репозиторий, нажмите на кнопку New repository, назовите проект и кликните Create
            repository. Можно добавить описание проекта, сделать его публичным или приватным и прикрепить
            технические файлы:
            README file содержит подробное описание проекта — так другие разработчики узнают, какой репозиторий
            они смотрят и зачем он нужен.
            Gitignore позволяет сэкономить место и не заливать на GitHub лишние файлы. Например, можно
            исключить скрытые файлы Mac OS.
            License добавляет к коду ссылку на первоисточник и защищает права разработчика. Лицензия
            позволяет понять, как правильно использовать чужой код и можно ли его свободно внедрять в коммерческие
            проекты.
            Для создания  тестового репозитория,  лицензирование можно не использовать, поэтому нужно выбрать
            только два дополнительных файла: README file и gitignore. Если вы пока не знаете, что писать в README
            file и что добавлять в gitignore, — оставьте эти файлы пустыми или посмотрите инструкцию в разделе Read
            the guide.
            В README file содержится краткое описание проекта на данный момент этот файл не важен, поэтому менять его
            описание не нужно. Нужно изменить файл gitignore чтобы он не учитывал служебные папки операционной
            системы:
              1. Необходимо перейти  на сайт gitignore.io.
              2. Добавить свою операционной систему
              3. Далее нажать  Create и получаем нужный служебный файл.
            Необходимо скопировать  данные и перенести  их в файл gitignore на GitHub.
            После редактирования gitignore сделайте коммит — записывая  в историю проекта факт того,
            что было  установлено ограничение для файлов вашей OC.

            Решение:
        """);

        System.out.println("""
            Сначала дублируются пункты, идентичные Шагу 1.
            На веб-сайте: https://github.com/
            Создан мой аккаунт (или profile), и в нём созданы мои репозитории.
            Имя моего аккаунта (или логин):  Vladislav-NewJoined
            Почта, указанная при регистрации: sozin.vladislav@mail.ru
            Ссылка на мой аккаунт:\s
            https://github.com/Vladislav-NewJoined
            Пример моего репозитория для этого задания:\s
            Имя репозитория:
            n240221_Module1_Task1_Zadaniya1-16
            Тип репозитория: Public
            Ссылка на репозиторий:\s
            https://github.com/Vladislav-NewJoined/n240221_Module1_Task1_Zadaniya1-16.git
            Далее показаны действия в Шаге 2:
            Я сгенерировал шаблон для файла .gitignore на сайте: gitignore.io для моей
            операционной системы (Windows 10). Вот так он выглядит:\s
            ```
            # Created by https://www.toptal.com/developers/gitignore/api/windows
            # Edit at https://www.toptal.com/developers/gitignore?templates=windows

            ### Windows ###
            # Windows thumbnail cache files
            Thumbs.db
            Thumbs.db:encryptable
            ehthumbs.db
            ehthumbs_vista.db

            # Dump file
            *.stackdump

            # Folder config file
            [Dd]esktop.ini

            # Recycle Bin used on file shares
            $RECYCLE.BIN/

            # Windows Installer files
            *.cab
            *.msi
            *.msix
            *.msm
            *.msp

            # Windows shortcuts
            *.lnk

            # End of https://www.toptal.com/developers/gitignore/api/windows
            ```
            , скопировал его и вставил в файл '.gitignore' в репозитории github.
            Сохранил изменения, нажав на кнопку 'Commit changes'.
            Используя ссылку на репозиторий github, https://github.com/Vladislav-NewJoined/n240221_Module1_Task1_Zadaniya1-16.git
            сделал обмен данными между репозиторием github и\s
            моим локальным компьютером, чтобы проекты в репозитории и на компьютере были идентичными.
        """);
    }
}