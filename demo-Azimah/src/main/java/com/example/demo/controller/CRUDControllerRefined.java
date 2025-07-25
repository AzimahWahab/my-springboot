package com.example.demo.controller;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Data;
import com.example.demo.model.Item;
import com.example.demo.service.ItemService;
import com.example.demo.service.ItemServiceAnalysis;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/demo/v2")
public class CRUDControllerRefined {

    private final ItemService itemService;

//    private final Map<Long, String> dataStore = new ConcurrentHashMap<>();
//    private final AtomicLong idCounter = new AtomicLong();
    
    private final ItemServiceAnalysis itemServiceAnalysis;

//    public CRUDControllerRefined(ItemService itemService, ItemServiceAnalysis itemServiceAnalysis){
//    	this.itemService = itemService;
//    	this.itemServiceAnalysis = itemServiceAnalysis;
//    	
//    }
   
    // --- CREATE (HTTP POST) ---
    @PostMapping
    public ResponseEntity<String> createItem(@RequestBody String newItemName) {
        if (newItemName == null || newItemName.isBlank()) {
            return new ResponseEntity<>("Item name cannot be empty or blank.", HttpStatus.BAD_REQUEST);
        }
        long newId = Data.getIdCounter().incrementAndGet();
        Data.getDataStore().put(newId, newItemName);
        return new ResponseEntity<>("Item created successfully with ID: " + newId + " and data: " + newItemName,
                HttpStatus.CREATED);
    }

    // --- READ (HTTP GET) ---
    @GetMapping
    public ResponseEntity<Map<Long, String>> getAllItems() {
        return new ResponseEntity<>(Data.getDataStore(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getItemById(@PathVariable Long id) {
//    	ItemValidation.parseAndValidateLongId(id);
        String item = Data.getDataStore().get(id);
        if (item != null) {
            return new ResponseEntity<>("Found item with ID: " + id + " and data: " + item, HttpStatus.OK);
        }
        return new ResponseEntity<>("Item with ID: " + id + " not found.", HttpStatus.NOT_FOUND);
    }

    // --- UPDATE (HTTP PUT) ---
    @PutMapping("/{id}")
    public ResponseEntity<String> updateItem(@PathVariable Long id, @RequestBody String updatedName) {
        if (updatedName == null || updatedName.isBlank()) {
            return new ResponseEntity<>("Updated item name cannot be empty or blank.", HttpStatus.BAD_REQUEST);
        }

        String oldName = Data.getDataStore().computeIfPresent(id, (key, existingName) -> updatedName);

        if (oldName != null) {
            return new ResponseEntity<>("Item with ID: " + id + " updated successfully to: " + updatedName,
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Item with ID: " + id + " not found for update.", HttpStatus.NOT_FOUND);
        }
    }

    // --- DELETE (HTTP DELETE) ---
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable Long id) {
        String removedItem = Data.getDataStore().remove(id);
        if (removedItem != null) {
            return new ResponseEntity<>(
                    "Item with ID: " + id + " and data: '" + removedItem + "' deleted successfully.",
                    HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>("Item with ID: " + id + " not found for deletion.", HttpStatus.NOT_FOUND);
    }

    // ✅ --- SEARCH FUNCTION: GET ITEMS CONTAINING "demo" ---
    @GetMapping("/demoItems1")
    public ResponseEntity<List<Item>> getAllItemsWithDemo() {
        List<Item> demos = this.itemServiceAnalysis.getAllItemsWithDemo();
        return new ResponseEntity<>(demos, HttpStatus.OK);
    }
}




