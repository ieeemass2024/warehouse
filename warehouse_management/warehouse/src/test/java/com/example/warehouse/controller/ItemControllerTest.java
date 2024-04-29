package com.example.warehouse.controller;

import com.example.warehouse.BaseJunit5Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
//@AutoConfigureMockMvc加载MockMvc对象
@AutoConfigureMockMvc
class ItemControllerTest extends BaseJunit5Test {

    @Autowired
    private ItemController itemController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        System.out.println("测试方法执行之前");
        System.out.println("controller: " + itemController);
        System.out.println("mockMvc: " + mockMvc);
    }

    /**
     * 测试获取所有商品
     * @throws Exception
     */
    @Test
    public void testGetItems() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/items")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    /**
     * 测试创建新商品
     * @throws Exception
     */
    @Test
    public void testCreateItem() throws Exception {
        // 使用正确的 JSON 格式，不包含注释
        String itemJson = """
                      {
                          "name": "New Item",
                          "category": "Test Category",
                          "description": "A new test item",
                          "stock": 100,
                          "price": 100,
                          "create_time": "2024-04-28T12:00:00Z",
                          "update_time": "2024-04-28T12:00:00Z"
                      }
                      """;

        mockMvc.perform(post("/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(itemJson))
                .andExpect(status().isCreated()); // 确保返回状态为 201 (Created)
    }



    /**
     * 测试删除商品
     * @throws Exception
     */
    @Test
    public void testDeleteItem() throws Exception {
        int itemId = 1; // 例子中使用的ID，实际使用中需要确保这个ID有效或者通过其他方式动态获取
        MvcResult mvcResult = this.mockMvc.perform(delete("/items/" + itemId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    /**
     * 测试更新商品信息
     * @throws Exception
     */
    @Test
    public void testUpdateItem() throws Exception {
        int itemId = 1; // 例子中使用的ID，实际使用中需要确保这个ID有效或者通过其他方式动态获取
        Map<String, Object> itemMap = new HashMap<>();
        itemMap.put("name", "Updated Item");
        itemMap.put("price", 150);
        String itemJson = JSONObject.toJSONString(itemMap);

        MvcResult mvcResult = this.mockMvc.perform(put("/items/" + itemId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(itemJson))
                .andExpect(status().isOk())
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    /**
     * 测试根据ID获取商品
     * @throws Exception
     */
    @Test
    public void testGetItemById() throws Exception {
        int itemId = 15; // 例子中使用的ID，实际使用中需要确保这个ID有效或者通过其他方式动态获取
        MvcResult mvcResult = this.mockMvc.perform(get("/items/" + itemId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }
}
