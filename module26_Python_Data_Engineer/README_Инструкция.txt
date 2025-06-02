Проект: Тестовое задание для вакансии: Junior Data Engineer  
https://saransk.hh.ru/vacancy/120435219?hhtmFrom=vacancy

Автор: Созин Владислав  
Контакты:  
+7 909 328-59-88 (WhatsApp)  
Telegram: https://t.me/tess_SV  
Email: sozin.vladislav@mail.ru  

---

# README_Инструкция

## Описание

В проекте реализованы оба задания из тестового задания:

1. **Задание 1:** Библиотека на Python для вычисления площади круга по радиусу и треугольника по трём сторонам (с возможностью расширения и юнит-тестами).  
2. **Задание 2:** PySpark-скрипт, который строит датафрейм с парами "Имя продукта – Имя категории" и выводит продукты без категорий.

---

## Как запустить решение Задания 2 (PySpark)

### 1. Требования

- **ОС:** Windows 10/11  
- **Python:** 3.7–3.10 (рекомендуется 3.10)  
- **Java:** 11 (Adoptium/OpenJDK, пример пути: `C:\Program Files\Eclipse Adoptium\jdk-11.0.27.6-hotspot`)  
- **PySpark:** 3.3.2  
- **winutils.exe:** для Hadoop 3.3.1 (лежит в `C:\hadoop\bin`)  

---

### 2. Установка зависимостей

1. Установите Python 3.10 с официального сайта ([python.org](https://www.python.org/)).  
2. Установите Java 11 (Adoptium/OpenJDK).  
3. Установите PySpark 3.3.2:
    ```
    pip install pyspark==3.3.2
    ```
4. Скачайте winutils.exe для Hadoop 3.3.1 и положите в `C:\hadoop\bin`.  
   Скачать можно отсюда: https://github.com/cdarlint/winutils/blob/master/hadoop-3.3.1/bin/winutils.exe?raw=true

---

### 3. Настройка переменных среды (в PowerShell)

Перед запуском скрипта укажите **свои** пути к установленным Java, Hadoop и Python:

$env:JAVA_HOME = "<ПУТЬ_К_ВАШЕЙ_JAVA_11>"
$env:HADOOP_HOME = "<ПУТЬ_К_ВАШЕМУ_HADOOP>"
$env:Path += ";<ПУТЬ_К_ВАШЕМУ_HADOOP_BIN>"
$env:PYSPARK_PYTHON = "<ПУТЬ_К_ВАШЕМУ_PYTHON.EXE>"
$env:PYSPARK_DRIVER_PYTHON = "<ПУТЬ_К_ВАШЕМУ_PYTHON.EXE>"

**Пример для типовой установки:**

$env:JAVA_HOME = "C:\Program Files\Eclipse Adoptium\jdk-11.0.27.6-hotspot"
$env:HADOOP_HOME = "C:\hadoop"
$env:Path += ";C:\hadoop\bin"
$env:PYSPARK_PYTHON = "C:\python310.exe"
$env:PYSPARK_DRIVER_PYTHON = "C:\python310.exe"

**Проверьте правильность написания путей к переменным среды с помощью команд в терминале:**

echo $env:JAVA_HOME
echo $env:HADOOP_HOME
echo $env:PYSPARK_PYTHON

Перед запуском проекта, убедитесь, что переменные окружения для Python настроены правильно
В терминале  PowerShell выполните эти команды:

$env:PYSPARK_PYTHON = "C:\12DraftsIdeaProjects\Portfolio_Sozin_Vladislav\module26_Python_Data_Engineer\venv\Scripts\python.exe"
$env:PYSPARK_DRIVER_PYTHON = "C:\12DraftsIdeaProjects\Portfolio_Sozin_Vladislav\module26_Python_Data_Engineer\venv\Scripts\python.exe"
$env:SPARK_LOCAL_IP = "127.0.0.1"
---

**Важно:**  
Пути к Java, Python и Hadoop могут отличаться на вашем компьютере.  
Везде, где в инструкции указан путь, используйте свой путь к установленному ПО.

Как узнать путь к Python:
- В PowerShell: `Get-Command python`
- Или найдите файл python.exe через Проводник.

Как узнать путь к Java:
- Найдите папку, где лежит папка bin с java.exe (например, `C:\Program Files\Eclipse Adoptium\jdk-11.0.27.6-hotspot`)

**Если ваш Python установлен в папке с пробелами (например, в `C:\Program Files\Python310\`), создайте символическую ссылку для удобства:**  
Откройте PowerShell от имени администратора и выполните:  
```
cmd /c mklink "C:\python310.exe" "<ПУТЬ_К_ВАШЕМУ_PYTHON.EXE>"
```
Например:  
```
cmd /c mklink "C:\python310.exe" "C:\Program Files\Python310\python.exe"
```
После этого используйте `C:\python310.exe` в переменных среды.

---

### 4. Запуск скрипта

1. Перейдите в папку проекта (например, `C:\SparkTest` или другую, где лежит проект).  
2. Запустите скрипт:
    ```
    python task2\pyspark_solution.py
    ```
---

### 5. Ожидаемый результат

На экране появится таблица со всеми парами "Имя продукта – Имя категории" и продуктами без категории. Пример вывода:

+------------+-------------+
|product_name|category_name|
+------------+-------------+
|Egg |null |
|Apple |Fruit |
|Banana |Fruit |
|Donut |Sweet |
|Apple |Vegetable |
|Carrot |Vegetable |
+------------+-------------+
---

## Как запустить решение Задания 1 (Python-библиотека)

1. Откройте файл `task1/geometry.py` (или другой, если используется другое имя).  
2. Для запуска юнит-тестов выполните:
    ```
    python -m unittest task1/test_geometry.py
    ```
3. Для примера использования функций — смотрите комментарии в коде.

---

## Примечания

- Для корректной работы PySpark на Windows важно, чтобы пути к проекту и Python не содержали кириллицы и пробелов.  
- Если что-то не запускается — внимательно проверьте переменные среды и версии ПО.

---

С уважением,  
Созин Владислав  
+7 909 328-59-88 (WhatsApp)  
https://t.me/tess_SV (Telegram)  
sozin.vladislav@mail.ru
