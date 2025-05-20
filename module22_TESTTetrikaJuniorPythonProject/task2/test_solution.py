import os
import csv
import unittest
from solution import parse_animals, OUTPUT

class TestParseAnimals(unittest.TestCase):
    def test_csv_created_and_not_empty(self):
        # Удаляем файл, если он есть
        if os.path.exists(OUTPUT):
            os.remove(OUTPUT)
        parse_animals()
        self.assertTrue(os.path.exists(OUTPUT))
        with open(OUTPUT, encoding='utf-8') as f:
            reader = csv.reader(f)
            rows = list(reader)
            self.assertTrue(len(rows) > 0)
            for row in rows:
                self.assertEqual(len(row), 2)
                self.assertTrue(row[0].isalpha())
                self.assertTrue(row[1].isdigit())

if __name__ == '__main__':
    unittest.main()
