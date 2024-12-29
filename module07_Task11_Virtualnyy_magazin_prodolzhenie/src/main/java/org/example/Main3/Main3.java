package org.example.Main3;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Main3 {
    public static void main(String[] args) throws Exception {
        System.out.println("""
                Задание:\s
                Модуль 7. Взаимодействие с API. Задание №11. Проект:\s
                    3. Реализовать навигационное меню состоящее из:\s
                         Каталог
                         Корзина
                         Оформление заказа
                         Назад\s

                Решение:\s""");

        Products2 nokia = new Products2("Nokia 3310", 12345, 4, 0);
        Products2 samsungGalaxy = new Products2("Samsung Galaxy S100", 30000, 7, 0);
        Products2 iphone = new Products2("IPhone 20", 392049, 1, 0);
        Products2 googlePixel = new Products2("Google Pixel 10a", 30000, 0, 0);
        List<Products2> catalog = Arrays.asList(nokia, samsungGalaxy, iphone, googlePixel);

        // Объявляем список: Название
        System.out.println("\nСписок: НАИМЕНОВАНИЕ: ");
        catalog.stream().map((product -> product.getName())).forEach(System.out::println);

        // Объявляем список: Цена
        System.out.println("\nСписок: ЦЕНА: ");
        catalog.stream().map((product -> product.getPrice())).forEach(System.out::println);

        // Объявляем список: Кол-во в наличии на складе
        System.out.println("\nСписок: КОЛ-ВО В НАЛИЧИИ НА СКЛАДЕ: ");
        catalog.stream().map((product -> product.getQtyInStock())).forEach(System.out::println);

        // Объявляем список: Кол-во заказано
        System.out.println("\nСписок: КОЛ-ВО ЗАКАЗАНО: ");
        catalog.stream().map((product -> product.getQtyOrdered())).forEach(System.out::println);

        // Сортируем по цене
        System.out.println("\nСОРТИРУЕМ КАТАЛОГ ПО ЦЕНЕ (по возрастанию):");
        catalog.stream().sorted(Comparator.comparing(Products2::getPrice)).toList()
                .forEach(System.out::println);

        // Фильтруем относительно доступности товара в текущий момент для заказа
        System.out.println("\nФИЛЬТРУЕМ ОТНОСИТЕЛЬНО ДОСТУПНОСТИ ТОВАРА ДЛЯ ЗАКАЗА: \n(исключаем позицию, которая на складе отсутствует)");
        catalog.stream()
                .filter(p -> p.getQtyInStock() != 0/*, qtyInStock*/)
                .forEach(p -> System.out.printf("Наименование: %s, Цена: %d, Кол-во на складе: %d.%n",
                        p.getName(), p.getPrice(), p.getQtyInStock())
                );

        System.out.println("\nРЕАЛИЗУЕМ НАВИГАЦИОННОЕ МЕНЮ, СОСТОЯЩЕЕ ИЗ РАЗДЕЛОВ:");
        System.out.println("ПОЛНЫЙ КАТАЛОГ:");
        catalog.forEach(p -> System.out.printf("Наименование: %s, Цена: %d, Кол-во на складе: %d.%n",
                p.getName(), p.getPrice(), p.getQtyInStock())
        );
        System.out.println();

        String name = "Samsung Galaxy S100";
        int count = 2;

        System.out.printf("КОРЗИНА: \n(Выбрано:  %s- %d шт.)%n", name, count);

// нашли продукт
        Products2 selected = catalog.stream()
                .filter(product -> Objects.equals(product.getName(), name))
                .filter(product -> product.getQtyInStock() >= count)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Товар не найден"));
// изменили состояние при заказе
        selected.setQtyOrdered(count);
// распечатали содержимое корзины
        catalog
                .stream()
                .filter(product -> product.getQtyOrdered() > 0)
                .forEach(p -> System.out.printf("Наименование: %s, Цена: %d, Кол-во заказа: %d.%n",
                        p.getName(), p.getPrice(), p.getQtyOrdered()
                ));
        System.out.println();

        System.out.println("ОФОРМЛЕНИЕ ЗАКАЗА:");
        catalog.stream()
                .filter(product -> Objects.equals(product.getName(), name)) // product.getQtyOrdered() > 0
                .forEach(p -> System.out.printf("Наименование: %s, Цена: %d, Кол-во заказа: %d, Сумма к оплате: %d руб.%n",
                        p.getName(), p.getPrice(), p.getQtyOrdered(), p.getPrice() * p.getQtyOrdered()
                ));
        System.out.println();
// изменили состояние после оформления заказа, т.е. покупки
        selected.sell();

        System.out.println("НАЗАД: \n(Samsung Galaxy S100 на складе осталось 5, а до заказа было 7)");
        catalog.forEach(p -> System.out.printf("Наименование: %s, Цена: %d, Кол-во на складе: %d.%n",
                p.getName(), p.getPrice(), p.getQtyInStock())
        );
    }
}


class Products2 {
    private String name;
    private int price;
    private int qtyInStock;
    private int qtyOrdered;

public Products2(String name, int price, int qtyInStock, int qtyOrdered) {
    this.setName(name);
    this.setPrice(price);
    this.setQtyInStock(qtyInStock);
    this.setQtyOrdered(qtyOrdered);
}

public String getName() {
    return name;
}

public void setName(String name) {
    this.name = name;
}

public int getPrice() {
    return price;
}

public void setPrice(int price) {
    this.price = price;
}

public int getQtyInStock() {
    return qtyInStock;
}

public void setQtyInStock(int qtyInStock) {
    this.qtyInStock = qtyInStock;
}

public int getQtyOrdered() {
    return qtyOrdered;
}

public void setQtyOrdered(int qtyOrdered) {
    this.qtyOrdered = qtyOrdered;
}

@Override
public String toString() {
    return "Products2{" +
            "name='" + name + '\'' +
            ", price=" + price +
            ", qtyInStock=" + qtyInStock +
            ", qtyOrdered=" + qtyOrdered +
            '}';
}

public void sell() {
    this.qtyInStock -= this.qtyOrdered;
    this.qtyOrdered = 0;
    }
}