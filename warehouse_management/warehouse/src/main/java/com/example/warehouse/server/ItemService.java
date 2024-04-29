package com.example.warehouse.server;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;
import com.example.warehouse.bean.Item;
import com.example.warehouse.mapper.ItemMapper;

@Service
public class ItemService {
    @Autowired
    private ItemMapper itemMapper; // 注入ItemMapper

    // 查询所有商品
    public List<Item> findAllItems() {
        return itemMapper.findAllItems();
    }

    // 添加商品
    public void addItem(Item item) {
        itemMapper.addItem(item);
    }

    // 根据id删除商品
    public int deleteItemById(Integer id) {
        return itemMapper.deleteItemById(id);
    }

    // 根据id查询商品
    public Item findItemById(int id) {
        return itemMapper.findItemById(id);
    }

    // 更新商品信息
    public int updateItem(Item item) {
        return itemMapper.updateItem(item);
    }
}