const ListItem = require('../models/listItem');

class ItemService {
  constructor() {
    this.items = [];
    this.customOrder = [];
    this.hasCustomOrder = false;
    
    // Инициализация миллиона элементов
    for (let i = 1; i <= 1000000; i++) {
      this.items.push(new ListItem(i));
    }
  }
  
  getItems(page, size, searchQuery) {
    // Применяем фильтрацию, если есть поисковый запрос
    let filteredItems = this.items;
    
    if (searchQuery) {
      const query = searchQuery.toLowerCase();
      filteredItems = this.items.filter(item => 
        item.displayText.toLowerCase().includes(query)
      );
    }
    
    // Применяем пользовательскую сортировку, если она есть
    if (this.hasCustomOrder && this.customOrder.length > 0) {
      filteredItems = [...filteredItems].sort((a, b) => {
        const indexA = this.customOrder.indexOf(a.id);
        const indexB = this.customOrder.indexOf(b.id);
        
        if (indexA === -1) return 1;
        if (indexB === -1) return -1;
        
        return indexA - indexB;
      });
    }
    
    // Применяем пагинацию
    const start = page * size;
    const end = Math.min(start + size, filteredItems.length);
    
    if (start >= filteredItems.length) {
      return [];
    }
    
    return filteredItems.slice(start, end);
  }
  
  getTotalCount(searchQuery) {
    if (searchQuery) {
      const query = searchQuery.toLowerCase();
      return this.items.filter(item => 
        item.displayText.toLowerCase().includes(query)
      ).length;
    }
    return this.items.length;
  }
  
  toggleSelection(id) {
    const item = this.items.find(item => item.id === id);
    if (item) {
      item.selected = !item.selected;
      return true;
    }
    return false;
  }
  
  updateOrder(newOrder) {
    if (newOrder && newOrder.length > 0) {
      this.customOrder = [...newOrder];
      this.hasCustomOrder = true;
      return true;
    }
    return false;
  }
  
  getSelectedItems() {
    let selectedItems = this.items.filter(item => item.selected);
    
    // Применяем пользовательскую сортировку, если она есть
    if (this.hasCustomOrder && this.customOrder.length > 0) {
      selectedItems = [...selectedItems].sort((a, b) => {
        const indexA = this.customOrder.indexOf(a.id);
        const indexB = this.customOrder.indexOf(b.id);
        
        if (indexA === -1) return 1;
        if (indexB === -1) return -1;
        
        return indexA - indexB;
      });
    }
    
    return selectedItems;
  }
  
  hasSelectedItems() {
    return this.items.some(item => item.selected);
  }
  
  getCurrentOrder() {
    return [...this.customOrder];
  }
  
  resetOrder() {
    this.customOrder = [];
    this.hasCustomOrder = false;
    return true;
  }
}

// Создаем и экспортируем единственный экземпляр сервиса
module.exports = new ItemService();
