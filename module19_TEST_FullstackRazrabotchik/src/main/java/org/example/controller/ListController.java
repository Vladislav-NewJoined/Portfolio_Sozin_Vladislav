package org.example.controller;

import org.example.model.ListItem;
import org.example.service.ListItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/items")
@CrossOrigin
public class ListController {

    @Autowired
    private ListItemService service;

    @GetMapping
    public Map<String, Object> getItems(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String search) {

        List<ListItem> items = service.getItems(page, size, search);
        int totalCount = service.getTotalCount(search);

        Map<String, Object> response = new HashMap<>();
        response.put("items", items);
        response.put("totalCount", totalCount);
        response.put("totalPages", (int) Math.ceil((double) totalCount / size));
        response.put("currentPage", page);
        response.put("hasMore", (page + 1) * size < totalCount);

        return response;
    }

    // Остальные методы остаются без изменений
    @GetMapping("/selected")
    public List<ListItem> getSelectedItems() {
        return service.getSelectedItems();
    }

    @PostMapping("/{id}/toggle")
    public void toggleSelection(@PathVariable Long id) {
        service.toggleSelection(id);
    }

    @PostMapping("/reorder")
    public void reorderItems(@RequestBody List<Long> newOrder) {
        service.updateOrder(newOrder);
    }

    @GetMapping("/order")
    public List<Long> getCurrentOrder() {
        return service.getCurrentOrder();
    }

    @PostMapping("/reset-order")
    public void resetOrder() {
        service.resetOrder();
    }
}
