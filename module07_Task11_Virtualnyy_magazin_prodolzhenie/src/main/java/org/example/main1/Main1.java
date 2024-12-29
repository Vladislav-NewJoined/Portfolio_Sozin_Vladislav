package org.example.main1;

import java.util.Arrays;
import java.util.List;

public class Main1 {
    public static void main(String[] args) {
        System.out.println("""
                Задание:\s
                Модуль 7. Взаимодействие с API. Задание №11. Проект:\s
                    1. Реализовать в  проекте пагинацию для того, чтобы каждая категория товара была в соответствующем
                       каталоге (реализуйте не менее 3-х категорий).\s

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
}