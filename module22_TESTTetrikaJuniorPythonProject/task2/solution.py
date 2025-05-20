import requests
from bs4 import BeautifulSoup
import csv
import time
import os

URL = "https://ru.wikipedia.org/wiki/Категория:Животные_по_алфавиту"
OUTPUT = os.path.join(os.path.dirname(__file__), "beasts.csv")

def get_page(url):
    response = requests.get(url)
    response.raise_for_status()
    return response.text

def parse_animals():
    letter_counts = {}
    url = URL
    while url:
        html = get_page(url)
        soup = BeautifulSoup(html, "html.parser")
        for group in soup.select('.mw-category-group'):
            letter = group.find('h3').text.strip()
            count = len(group.find_all('li'))
            letter_counts[letter] = letter_counts.get(letter, 0) + count
        next_link = soup.find('a', string="Следующая страница")
        if next_link:
            url = "https://ru.wikipedia.org" + next_link['href']
            time.sleep(0.5)  # чтобы не перегружать сервер
        else:
            url = None
    with open(OUTPUT, "w", encoding="utf-8", newline='') as f:
        writer = csv.writer(f)
        for letter, count in sorted(letter_counts.items()):
            writer.writerow([letter, count])

if __name__ == "__main__":
    parse_animals()
