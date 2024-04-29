package com.example.warehouse.controller;

import com.example.warehouse.bean.Item;
import com.example.warehouse.server.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final Logger LOG = LoggerFactory.getLogger(getClass());
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ItemService itemService;

    // 获取所有商品
    @GetMapping
    public ResponseEntity<List<Item>> getItems() {
        String key = "allItems";
        List<Item> items = (List<Item>) redisTemplate.opsForValue().get(key);
        if (items == null) {
            try {
                items = itemService.findAllItems();
                redisTemplate.opsForValue().set(key, items);
                LOG.info("All items retrieved from database and cached.");
                return ResponseEntity.ok(items);
            } catch (Exception e) {
                LOG.error("Error retrieving all items: {}", e.getMessage(), e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            LOG.info("All items retrieved from cache.");
            return ResponseEntity.ok(items);
        }
    }


    // 创建新商品
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')") // 只允许具有管理员权限的用户调用此API
    public ResponseEntity<String> createItem(@RequestBody Item item) {
        try {
            itemService.addItem(item);
            // 清除所有商品的缓存
            redisTemplate.delete("allItems");
            return ResponseEntity.status(HttpStatus.CREATED).body("Item created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating item: " + e.getMessage());
        }
    }

    // 删除商品
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')") // 只允许具有管理员权限的用户调用此API
    public ResponseEntity<String> deleteItem(@PathVariable Integer id) {
        try {
            itemService.deleteItemById(id);
            // 清除所有商品的缓存
            redisTemplate.delete("allItems");
            // 清除对应商品的缓存
            redisTemplate.delete("item:"+id);
            LOG.info("Deleting item for ID: {} from caching", id);
            return ResponseEntity.ok("Item deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting item: " + e.getMessage());
        }
    }

    // 更新商品信息
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')") // 只允许具有管理员权限的用户调用此API
    public ResponseEntity<String> updateItem(@PathVariable Integer id, @RequestBody Item item) {
        try {
            item.setId(id);
            itemService.updateItem(item);
            // 清除所有商品的缓存
            redisTemplate.delete("allItems");
            // 更新对应商品的缓存
            redisTemplate.delete("item:"+id); // 删除旧的商品缓存
            redisTemplate.opsForValue().set("item:"+id, item); // 重新缓存更新后的商品信息
            return ResponseEntity.ok("Item updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating item: " + e.getMessage());
        }
    }

    // 根据ID获取商品
    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Integer id) {
        Item item = getItemData(id);
        if (item != null) {
            return ResponseEntity.ok(item);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public Item getItemData(Integer id) {
        String key = "item:" + id;
        // 尝试从缓存中获取项目
        Item item = (Item) redisTemplate.opsForValue().get(key);
        if (item == null) {
            LOG.info("Fetching from database for ID: {}", id);
            item = itemService.findItemById(id);
            if (item != null) {
                LOG.info("Caching item for ID: {}", id);
                redisTemplate.opsForValue().set(key, item);
            }
        } else {
            LOG.info("Retrieved from cache for ID: {}", id);
        }
        return item;
    }
}
