package task14_5_1.zadanye1;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {
	public static void main(String[] args) throws IOException {
		System.out.println("""
            Модуль 14. Spring. Задание №5. Проект:\s
                Задания:
                1. Концептуальный парсинг данных с сайта с помощью пакета jsoup.

            Решение:\s""");

		Document doc = Jsoup.connect("http://ibash.org.ru/").get();
		System.out.println(doc.title());
		Elements sourceQuotes = doc.select(".quote");
		Map<Integer, String> quotes = new HashMap<>();
		for (Element quoteElement : sourceQuotes) {
			int id = Integer.parseInt(quoteElement.select("b").first().text().substring(1));
			System.out.println(id);
			String text = quoteElement.select(".quotbody").first().text();
			System.out.println("text");
			quotes.put(id, text);
		}
	}
}
