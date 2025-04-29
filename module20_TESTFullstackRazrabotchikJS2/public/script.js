let currentPage = 0;
const pageSize = 20;
let isLoading = false;
let hasMoreItems = true;
let searchTimeout;
let searchQuery = '';
let itemsOrder = [];
let initialLoadComplete = false;
let totalItems = 0;

const container = document.getElementById('list-container');
const searchInput = document.getElementById('search-input');
const resetButton = document.getElementById('reset-button');
const loadingIndicator = document.getElementById('loading');
const statusMessage = document.getElementById('status-message');
const searchInfo = document.getElementById('search-info');

// Функция для отображения статусного сообщения
function showStatus(message, isError = false) {
    statusMessage.textContent = message;
    statusMessage.style.backgroundColor = isError ? '#f44336' : '#333';
    statusMessage.classList.add('show');
    
    setTimeout(() => {
        statusMessage.classList.remove('show');
    }, 3000);
}

// Функция для обновления информации о поиске
function updateSearchInfo() {
    if (searchQuery) {
        searchInfo.textContent = `Showing results for "${searchQuery}" (${totalItems} items found)`;
        searchInfo.style.display = 'block';
    } else {
        searchInfo.style.display = 'none';
    }
}

// Функция для загрузки выбранных элементов при первой загрузке
async function loadSelectedItems() {
    try {
        const response = await fetch('/api/items/selected');
        if (!response.ok) {
            throw new Error('Failed to load selected items');
        }
        
        const selectedItems = await response.json();
        
        if (selectedItems.length > 0) {
            container.innerHTML = '';
            itemsOrder = [];
            
            // Отображаем только первые 20 выбранных элементов
            const itemsToShow = selectedItems.slice(0, pageSize);
            
            itemsToShow.forEach(item => {
                const itemId = item.id;
                itemsOrder.push(itemId);
                
                const div = document.createElement('div');
                div.className = `item ${item.selected ? 'selected' : ''}`;
                div.setAttribute('data-id', itemId);
                div.draggable = true;
                
                div.innerHTML = `
                    <div class="drag-handle">☰</div>
                    <div class="item-content">
                        <input type="checkbox" class="checkbox" ${item.selected ? 'checked' : ''}>
                        <span>${item.displayText || 'Item ' + itemId}</span>
                    </div>
                `;
                
                // Добавляем обработчик для чекбокса
                const checkbox = div.querySelector('.checkbox');
                checkbox.addEventListener('click', function() {
                    toggleItem(itemId, this);
                });
                
                // Добавляем обработчики для Drag&Drop
                div.addEventListener('dragstart', handleDragStart);
                div.addEventListener('dragover', handleDragOver);
                div.addEventListener('drop', handleDrop);
                div.addEventListener('dragend', handleDragEnd);
                
                container.appendChild(div);
            });
            
            // Если выбранных элементов больше 20, устанавливаем флаг для подгрузки остальных
            hasMoreItems = selectedItems.length > pageSize;
            currentPage = 1; // Начинаем с 1, так как уже загрузили первую страницу
            totalItems = selectedItems.length;
            
            return true; // Возвращаем true, если загрузили выбранные элементы
        }
        
        return false; // Возвращаем false, если нет выбранных элементов
    } catch (error) {
        console.error('Error loading selected items:', error);
        showStatus('Error loading selected items', true);
        return false;
    }
}

// Функция для загрузки элементов
async function loadItems(page, append = true) {
    if (isLoading || (!hasMoreItems && append)) return;
    
    isLoading = true;
    loadingIndicator.style.display = 'block';
    
    try {
        const url = `/api/items?page=${page}&size=${pageSize}${searchQuery ? `&search=${encodeURIComponent(searchQuery)}` : ''}`;
        const response = await fetch(url);
        
        if (!response.ok) {
            throw new Error('Failed to load items');
        }
        
        const data = await response.json();
        
        // Обновляем информацию о пагинации
        totalItems = data.totalCount || 0;
        hasMoreItems = data.hasMore || false;
        
        // Обновляем информацию о поиске
        updateSearchInfo();
        
        // Проверяем, есть ли в ответе массив items
        const items = data.items || [];
        
        if (!append) {
            container.innerHTML = '';
            itemsOrder = [];
        }
        
        if (items.length === 0 && page === 0) {
            container.innerHTML = '<div class="loading">No items found</div>';
            return;
        }
        
        items.forEach(item => {
            const itemId = item.id;
            itemsOrder.push(itemId);
            
            const div = document.createElement('div');
            div.className = `item ${item.selected ? 'selected' : ''}`;
            div.setAttribute('data-id', itemId);
            div.draggable = true;
            
            div.innerHTML = `
                <div class="drag-handle">☰</div>
                <div class="item-content">
                    <input type="checkbox" class="checkbox" ${item.selected ? 'checked' : ''}>
                    <span>${item.displayText || 'Item ' + itemId}</span>
                </div>
            `;
            
            // Добавляем обработчик для чекбокса
            const checkbox = div.querySelector('.checkbox');
            checkbox.addEventListener('click', function() {
                toggleItem(itemId, this);
            });
            
            // Добавляем обработчики для Drag&Drop
            div.addEventListener('dragstart', handleDragStart);
            div.addEventListener('dragover', handleDragOver);
            div.addEventListener('drop', handleDrop);
            div.addEventListener('dragend', handleDragEnd);
            
            container.appendChild(div);
        });
    } catch (error) {
        console.error('Error loading items:', error);
        showStatus('Error loading items', true);
    } finally {
        isLoading = false;
        loadingIndicator.style.display = 'none';
    }
}

// Функция для переключения выбора элемента
async function toggleItem(id, checkbox) {
    try {
        const response = await fetch(`/api/items/${id}/toggle`, { method: 'POST' });
        
        if (!response.ok) {
            throw new Error('Failed to toggle item');
        }
        
        checkbox.closest('.item').classList.toggle('selected');
    } catch (error) {
        console.error('Error toggling item:', error);
        showStatus('Error toggling item selection', true);
        checkbox.checked = !checkbox.checked; // Возвращаем предыдущее состояние
    }
}

// Функция для сброса порядка элементов
async function resetOrder() {
    try {
        const response = await fetch('/api/items/reset-order', { method: 'POST' });
        
        if (!response.ok) {
            throw new Error('Failed to reset order');
        }
        
        showStatus('Order has been reset');
        
        // Перезагружаем элементы
        currentPage = 0;
        loadItems(currentPage, false);
    } catch (error) {
        console.error('Error resetting order:', error);
        showStatus('Error resetting order', true);
    }
}

// Функция для получения всех выбранных элементов
function getSelectedItems() {
    return Array.from(container.querySelectorAll('.item.selected'));
}

// Обработчики для Drag&Drop
let draggedItem = null;
let draggedItems = [];
let draggedItemsPositions = [];

function handleDragStart(e) {
    draggedItem = this;
    
    // Если перетаскиваемый элемент выбран, то перетаскиваем все выбранные элементы
    if (draggedItem.classList.contains('selected')) {
        draggedItems = getSelectedItems();
        
        // Запоминаем позиции всех выбранных элементов
        draggedItemsPositions = draggedItems.map(item => {
            const allItems = Array.from(container.querySelectorAll('.item'));
            return allItems.indexOf(item);
        });
    } else {
        // Иначе перетаскиваем только текущий элемент
        draggedItems = [draggedItem];
        draggedItemsPositions = [Array.from(container.querySelectorAll('.item')).indexOf(draggedItem)];
    }
    
    // Добавляем класс dragging ко всем перетаскиваемым элементам
    draggedItems.forEach(item => item.classList.add('dragging'));
    
    e.dataTransfer.effectAllowed = 'move';
    e.dataTransfer.setData('text/html', this.innerHTML);
}

function handleDragOver(e) {
    if (e.preventDefault) {
        e.preventDefault();
    }
    e.dataTransfer.dropEffect = 'move';
    
    // Удаляем все плейсхолдеры
    const placeholders = container.querySelectorAll('.placeholder');
    placeholders.forEach(p => p.remove());
    
    // Если элемент не перетаскивается или это один из перетаскиваемых элементов, выходим
    if (!draggedItem || draggedItems.includes(this)) {
        return false;
    }
    
    // Определяем, куда вставлять плейсхолдер (до или после текущего элемента)
    const rect = this.getBoundingClientRect();
    const y = e.clientY - rect.top;
    const isBelow = y > rect.height / 2;
    
    // Создаем плейсхолдер
    const placeholder = document.createElement('div');
    placeholder.className = 'placeholder';
    
    // Вставляем плейсхолдер до или после текущего элемента
    if (isBelow) {
        this.parentNode.insertBefore(placeholder, this.nextSibling);
    } else {
        this.parentNode.insertBefore(placeholder, this);
    }
    
    return false;
}

function handleDrop(e) {
    if (e.stopPropagation) {
        e.stopPropagation();
    }
    
    // Удаляем все плейсхолдеры
    const placeholders = container.querySelectorAll('.placeholder');
    placeholders.forEach(p => p.remove());
    
    // Если элемент не перетаскивается или это один из перетаскиваемых элементов, выходим
    if (!draggedItem || draggedItems.includes(this)) {
        return false;
    }
    
    // Определяем, куда вставлять элементы (до или после текущего элемента)
    const rect = this.getBoundingClientRect();
    const y = e.clientY - rect.top;
    const isBelow = y > rect.height / 2;
    
    // Получаем все элементы
    const allItems = Array.from(container.querySelectorAll('.item'));
    
    // Определяем индекс целевого элемента
    const targetIndex = allItems.indexOf(this);
    
    // Определяем, куда вставлять элементы
    const insertIndex = isBelow ? targetIndex + 1 : targetIndex;
    
    // Сортируем перетаскиваемые элементы по их исходным позициям (в обратном порядке)
    const sortedItems = draggedItems.map((item, index) => ({
        item,
        originalIndex: draggedItemsPositions[index]
    })).sort((a, b) => b.originalIndex - a.originalIndex);
    
    // Удаляем перетаскиваемые элементы из DOM
    sortedItems.forEach(({ item }) => {
        item.remove();
    });
    
    // Определяем элемент, перед которым будем вставлять
    const insertBeforeElement = allItems[insertIndex] || null;
    
    // Вставляем перетаскиваемые элементы в новую позицию (в правильном порядке)
    sortedItems.reverse().forEach(({ item }) => {
        if (insertBeforeElement) {
            container.insertBefore(item, insertBeforeElement);
        } else {
            container.appendChild(item);
        }
    });
    
    // Обновляем порядок элементов на сервере
    updateItemsOrder();
    
    return false;
}

function handleDragEnd() {
    // Удаляем класс dragging со всех перетаскиваемых элементов
    draggedItems.forEach(item => item.classList.remove('dragging'));
    
    // Удаляем все плейсхолдеры
    const placeholders = container.querySelectorAll('.placeholder');
    placeholders.forEach(p => p.remove());
    
    // Сбрасываем переменные
    draggedItem = null;
    draggedItems = [];
    draggedItemsPositions = [];
}

// Функция для обновления порядка элементов на сервере
async function updateItemsOrder() {
    const newOrder = Array.from(container.querySelectorAll('.item'))
        .map(item => parseInt(item.getAttribute('data-id')));
    
    try {
        const response = await fetch('/api/items/reorder', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(newOrder)
        });
        
        if (!response.ok) {
            throw new Error('Failed to update order');
        }
        
        itemsOrder = newOrder;
        showStatus('Order updated successfully');
    } catch (error) {
        console.error('Error updating order:', error);
        showStatus('Error updating order', true);
    }
}

// Обработчик поиска с debounce
searchInput.addEventListener('input', function() {
    clearTimeout(searchTimeout);
    searchTimeout = setTimeout(() => {
        const newSearchQuery = this.value.trim();
        
        // Если поисковый запрос изменился, сбрасываем пагинацию
        if (newSearchQuery !== searchQuery) {
            searchQuery = newSearchQuery;
            currentPage = 0;
            hasMoreItems = true;
            loadItems(currentPage, false);
        }
    }, 300);
});

// Обработчик для кнопки сброса порядка
resetButton.addEventListener('click', resetOrder);

// Обработчик прокрутки для бесконечной загрузки
window.addEventListener('scroll', () => {
    if (window.innerHeight + window.scrollY >= document.body.offsetHeight - 200 && !isLoading && hasMoreItems) {
        currentPage++;
        loadItems(currentPage);
    }
});

// Инициализация: сначала пытаемся загрузить выбранные элементы
async function initializeApp() {
    const hasSelectedItems = await loadSelectedItems();
    
    // Если нет выбранных элементов, загружаем обычные элементы
    if (!hasSelectedItems) {
        loadItems(currentPage);
    }
    
    initialLoadComplete = true;
}

// Запускаем инициализацию
initializeApp();
