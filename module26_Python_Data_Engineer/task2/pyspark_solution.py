import os
from pyspark.sql import SparkSession
from pyspark.sql.functions import col
from pyspark.conf import SparkConf

def process_products_and_categories(spark, products_data, categories_data, product_category_data):
    """
    Обрабатывает данные о продуктах и категориях и возвращает DataFrame с парами
    "Имя продукта – Имя категории" и именами продуктов без категорий.
    """
    # 1. Создание DataFrame'ов
    products = spark.createDataFrame(products_data, ["id", "product_name"])
    categories = spark.createDataFrame(categories_data, ["id", "category_name"])
    product_category = spark.createDataFrame(product_category_data, ["product_id", "category_id"])

    # 2. LEFT JOIN: продукты + связи (может быть пусто, если у продукта нет категории)
    prod_cat = products.join(product_category, products.id == product_category.product_id, "left")

    # 3. LEFT JOIN: добавляем имена категорий
    prod_cat_names = prod_cat.join(categories, prod_cat.category_id == categories.id, "left") \
        .select(col("product_name"), col("category_name"))

    # 4. Возвращаем результат напрямую (включает и продукты с категориями, и без)
    result = prod_cat_names.select(
        col("product_name"),
        col("category_name")
    )

    return result

if __name__ == "__main__":
    # Инициализируем переменную spark как None
    spark = None

    try:
        # Установка переменной окружения для разрешения Security Manager
        os.environ['PYSPARK_SUBMIT_ARGS'] = '--conf spark.driver.extraJavaOptions=-Djava.security.manager=allow --conf spark.executor.extraJavaOptions=-Djava.security.manager=allow pyspark-shell'

        conf = SparkConf()
        conf.set("spark.driver.host", "localhost")
        conf.set("spark.driver.bindAddress", "localhost")
        conf.set("spark.sql.adaptive.enabled", "false")
        conf.set("spark.sql.adaptive.coalescePartitions.enabled", "false")
        conf.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
        conf.set("spark.sql.execution.arrow.pyspark.enabled", "false")
        conf.set("spark.python.worker.reuse", "false")
        conf.set("spark.network.timeout", "800s")
        conf.set("spark.executor.heartbeatInterval", "60s")

        conf.set("spark.driver.extraJavaOptions", "-Djava.security.manager=allow")
        conf.set("spark.executor.extraJavaOptions", "-Djava.security.manager=allow")

        spark = SparkSession.builder \
            .appName("ProductCategory") \
            .master("local[1]") \
            .config(conf=conf) \
            .getOrCreate()

        spark.sparkContext.setLogLevel("ERROR")

        # Ваши данные и вызов функции
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
            (1, 2)   # Apple - Vegetable (Apple - две категории)
        ]

        result_df = process_products_and_categories(spark, products_data, categories_data, product_category_data)
        result_df.show(truncate=False)

    except Exception as e:
        print(f"Произошла ошибка: {e}")
        print("Попробуйте заменить 'allow' на 'default' в Java опциях")
    finally:
        # Безопасная остановка Spark
        if spark is not None:
            try:
                spark.stop()
            except Exception as e:
                print(f"Ошибка при остановке Spark: {e}")
