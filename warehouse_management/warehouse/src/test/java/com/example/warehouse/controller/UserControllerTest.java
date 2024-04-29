package com.example.warehouse.controller;

import com.example.warehouse.BaseJunit5Test;
import com.example.warehouse.bean.LoginRequest;
import com.example.warehouse.bean.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest  extends BaseJunit5Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserController userController;

    @BeforeEach
    public void setUp() {
        System.out.println("测试方法执行之前");
        System.out.println("controller: " + userController);
        System.out.println("mockMvc: " + mockMvc);
    }

    @Test
    public void testGetUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateUser() throws Exception {
        // 使用正确的 JSON 格式，不包含注释
        String userJson = """
                      {
                          "id": 1,
                          "username": "Username",
                          "password": "Password"
                      }
                      """;
        User newUser = new User();
        newUser.setId(1);
        newUser.setUsername("John Doe");
        newUser.setPassword("John Doe");
        newUser.setEmail("john@example.com");

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/users/{id}", 1))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateUser() throws Exception {
        // 使用正确的 JSON 格式，不包含注释
        String userJson = """
                      {
                          "username": "Username",
                          "password": "Password"
                      }
                      """;
        User updatedUser = new User();
        updatedUser.setUsername("Jane Doe Updated");
        updatedUser.setEmail("jane.updated@example.com");



                mockMvc.perform(put("/users/{id}", 1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(userJson))
                        .andExpect(status().isOk());
    }

    @Test
    public void testLogin() throws Exception {
        LoginRequest loginRequest = new LoginRequest("user2", "pass5678");

        // 使用正确的 JSON 格式，不包含注释
        String loginJson = """
                      {
                          "username": "user2",
                          "password": "pass5678"
                      }
                      """;

                mockMvc.perform(post("/users/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(loginJson))
                        .andExpect(status().isOk());
    }
}
