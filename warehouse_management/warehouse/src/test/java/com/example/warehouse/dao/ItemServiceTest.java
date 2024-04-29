package com.example.warehouse.dao;

import com.example.warehouse.bean.Item;
import com.example.warehouse.mapper.ItemMapper;
import com.example.warehouse.server.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ItemServiceTest {

    @Mock
    private ItemMapper itemMapper;

    @InjectMocks
    private ItemService itemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllItemsTest() {
        List<Item> expectedItems = new ArrayList<>();
        expectedItems.add(new Item(1, "Item1", "Type1", 10, 100));
        expectedItems.add(new Item(2, "Item2", "Type2", 20, 200));

        when(itemMapper.findAllItems()).thenReturn(expectedItems);

        List<Item> actualItems = itemService.findAllItems();
        assertEquals(expectedItems, actualItems);
        verify(itemMapper).findAllItems();
    }

    @Test
    void addItemTest() {
        Item newItem = new Item(3, "Item3", "Type3", 30, 300);
        doNothing().when(itemMapper).addItem(any(Item.class));

        itemService.addItem(newItem);
        verify(itemMapper).addItem(newItem);
    }

    @Test
    void deleteItemByIdTest() {
        when(itemMapper.deleteItemById(1)).thenReturn(1);
        int result = itemService.deleteItemById(1);
        assertEquals(1, result);
        verify(itemMapper).deleteItemById(1);
    }

    @Test
    void findItemByIdTest() {
        Item expectedItem = new Item(1, "Item1", "Type1", 10, 100);
        when(itemMapper.findItemById(1)).thenReturn(expectedItem);

        Item actualItem = itemService.findItemById(1);
        assertEquals(expectedItem, actualItem);
        verify(itemMapper).findItemById(1);
    }

    @Test
    void updateItemTest() {
        Item updatedItem = new Item(1, "UpdatedItem1", "Type1", 10, 150.0);
        when(itemMapper.updateItem(updatedItem)).thenReturn(1);

        int result = itemService.updateItem(updatedItem);
        assertEquals(1, result);
        verify(itemMapper).updateItem(updatedItem);
    }
}

