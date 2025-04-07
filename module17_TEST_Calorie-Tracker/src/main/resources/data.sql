CREATE TABLE IF NOT EXISTS ПОЛЬЗОВАТЕЛИ (
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    Имя VARCHAR(255) NOT NULL,
    Возраст INT NOT NULL,
    Email VARCHAR(255) NOT NULL,
    Цель VARCHAR(255) NOT NULL,
    `Рост (см)` DOUBLE NOT NULL,
    `Вес (кг)` DOUBLE NOT NULL,
    `Уровень активности` VARCHAR(255) NOT NULL,
    Пол VARCHAR(255) NOT NULL,
    `Калории на порцию` DOUBLE,
    Углеводы DOUBLE,
    Жиры DOUBLE,
    Название VARCHAR(255),
    Белки DOUBLE
);
