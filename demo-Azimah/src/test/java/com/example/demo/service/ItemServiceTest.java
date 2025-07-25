package com.example.demo.service;

import com.example.demo.model.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ItemServiceTest {

    private ItemService itemService;

    @BeforeEach
    void setUp() {
        itemService = new ItemService();
    }

    @Test
    void testCreateItem() {
        Item item = itemService.createItem("Test Value");
        assertNotNull(item);
        assertEquals("Test Value", item.value());
        assertTrue(item.id() > 0);
    }

    @Test
    void testCreateItemWithProvidedId_Success() {
        Optional<Item> optionalItem = itemService.createItemWithProvidedId(100L, "Manual ID Item");
        assertTrue(optionalItem.isPresent());
        assertEquals(100L, optionalItem.get().id()); // ✅ TODO completed
        assertEquals("Manual ID Item", optionalItem.get().value());
    }

    @Test
    void testGetItemById() {
        Item created = itemService.createItem("Lookup");
        Optional<Item> fetched = itemService.getItemById(created.id());
        assertTrue(fetched.isPresent()); // ✅ More accurate than just assertNotNull
        assertEquals("Lookup", fetched.get().value());
    }

    @Test
    void testGetAllItems() {
        itemService.createItem("One");
        itemService.createItem("Two");
        List<Item> items = itemService.getAllItems();
        assertEquals(2, items.size());
    }

    @Test
    void testUpdateItem_Success() {
        Item created = itemService.createItem("Before");
        Optional<Item> updated = itemService.updateItem(created.id(), "After");
        assertTrue(updated.isPresent());
        assertEquals("After", updated.get().value());
    }

    @Test
    void testDeleteItem_Success() {
        Item created = itemService.createItem("Delete Me");
        boolean result = itemService.deleteItem(created.id());

        assertTrue(result); // ✅ TODO completed: confirm deletion success
        assertFalse(itemService.getItemById(created.id()).isPresent()); // ✅ Check it's truly deleted
    }

    @Test
    void testDeleteItem_NotFound() {
        boolean result = itemService.deleteItem(12345L);
        assertFalse(result); // ✅ More accurate than just assertNotNull
    }
}
