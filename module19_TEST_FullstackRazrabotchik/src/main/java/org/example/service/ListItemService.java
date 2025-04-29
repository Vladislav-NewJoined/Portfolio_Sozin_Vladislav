package org.example.service;

import org.example.model.ListItem;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListItemService {
    private final List<ListItem> items;
    private List<Long> customOrder = new ArrayList<>(); // Для хранения пользовательского порядка
    private boolean hasCustomOrder = false; // Флаг, указывающий, что есть пользовательская сортировка

    public ListItemService() {
        items = new ArrayList<>();
        for (long i = 1; i <= 1_000_000; i++) {
            items.add(new ListItem(i));
        }
    }

    public List<ListItem> getItems(int page, int size, String searchQuery) {
        // Сначала применяем поисковый фильтр, если он есть
        List<ListItem> filteredItems;
        if (searchQuery != null && !searchQuery.isEmpty()) {
            String query = searchQuery.toLowerCase();
            filteredItems = items.stream()
                    .filter(item -> item.getDisplayText().toLowerCase().contains(query))
                    .collect(Collectors.toList());
        } else {
            filteredItems = new ArrayList<>(items);
        }

        // Затем применяем пользовательскую сортировку, если она есть
        if (hasCustomOrder && !customOrder.isEmpty()) {
            filteredItems = filteredItems.stream()
                    .sorted(Comparator.comparing(item -> {
                        int index = customOrder.indexOf(item.getId());
                        return index == -1 ? Integer.MAX_VALUE : index;
                    }))
                    .collect(Collectors.toList());
        }

        // Наконец, применяем пагинацию
        int start = page * size;
        int end = Math.min(start + size, filteredItems.size());

        if (start >= filteredItems.size()) {
            return new ArrayList<>();
        }

        return filteredItems.subList(start, end);
    }

    // Метод для получения общего количества элементов после фильтрации
    public int getTotalCount(String searchQuery) {
        if (searchQuery != null && !searchQuery.isEmpty()) {
            String query = searchQuery.toLowerCase();
            return (int) items.stream()
                    .filter(item -> item.getDisplayText().toLowerCase().contains(query))
                    .count();
        }
        return items.size();
    }

    // Остальные методы остаются без изменений
    public void toggleSelection(Long id) {
        items.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .ifPresent(item -> item.setSelected(!item.isSelected()));
    }

    public void updateOrder(List<Long> newOrder) {
        if (newOrder != null && !newOrder.isEmpty()) {
            this.customOrder = new ArrayList<>(newOrder);
            this.hasCustomOrder = true;
        }
    }

    public List<ListItem> getSelectedItems() {
        List<ListItem> selectedItems = items.stream()
                .filter(ListItem::isSelected)
                .collect(Collectors.toList());

        if (hasCustomOrder && !customOrder.isEmpty()) {
            selectedItems = selectedItems.stream()
                    .sorted(Comparator.comparing(item -> {
                        int index = customOrder.indexOf(item.getId());
                        return index == -1 ? Integer.MAX_VALUE : index;
                    }))
                    .collect(Collectors.toList());
        }

        return selectedItems;
    }

    public boolean hasSelectedItems() {
        return items.stream().anyMatch(ListItem::isSelected);
    }

    public List<Long> getCurrentOrder() {
        return new ArrayList<>(customOrder);
    }

    public void resetOrder() {
        this.customOrder.clear();
        this.hasCustomOrder = false;
    }
}
