def process_products_and_categories_simple(products_data, categories_data, product_category_data):
    """
    Простая версия без PySpark для тестирования логики.
    Обрабатывает данные о продуктах и категориях и возвращает список кортежей
    с парами "Имя продукта – Имя категории" и именами продуктов без категорий.
    """
    # Создаем словари для быстрого поиска
    products_dict = {pid: name for pid, name in products_data}
    categories_dict = {cid: name for cid, name in categories_data}

    # Создаем словарь связей продукт -> список категорий
    product_categories = {}
    for prod_id, cat_id in product_category_data:
        if prod_id not in product_categories:
            product_categories[prod_id] = []
        product_categories[prod_id].append(cat_id)

    results = []

    # Обрабатываем все продукты
    for prod_id, prod_name in products_data:
        if prod_id in product_categories:
            # Продукт имеет категории
            for cat_id in product_categories[prod_id]:
                cat_name = categories_dict.get(cat_id)
                results.append((prod_name, cat_name))
        else:
            # Продукт без категории
            results.append((prod_name, None))

    return results


if __name__ == "__main__":
    # Тестовые данные
    products_data = [
        (1, "Apple"),
        (2, "Banana"),
        (3, "Carrot"),
        (4, "Donut"),
        (5, "Egg")  # Продукт без категории
    ]

    categories_data = [
        (1, "Fruit"),
        (2, "Vegetable"),
        (3, "Sweet")
    ]

    product_category_data = [
        (1, 1),  # Apple - Fruit
        (2, 1),  # Banana - Fruit
        (3, 2),  # Carrot - Vegetable
        (4, 3),  # Donut - Sweet
        (1, 2)   # Apple - Vegetable (Apple имеет две категории)
    ]

    # Вызов функции
    results = process_products_and_categories_simple(products_data, categories_data, product_category_data)

    # Вывод результатов
    print("Product Name | Category Name")
    print("-" * 30)
    for prod_name, cat_name in results:
        print(f"{prod_name:<12} | {cat_name or 'None'}")

    print(f"\nВсего записей: {len(results)}")
