package com.example.warehouse.dao;

import com.example.warehouse.bean.User;
import com.example.warehouse.mapper.UserMapper;
import com.example.warehouse.server.UserService;
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
public class UserServiceTest {
    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllUsersTest() {
        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(new User(1, "testUser1", "pass1", "role1", "email1", "time1"));
        expectedUsers.add(new User(2, "testUser2", "pass2", "role2", "email2", "time2"));

        when(userMapper.findAllUsers()).thenReturn(expectedUsers);

        List<User> actualUsers = userService.userList();
        assertEquals(expectedUsers, actualUsers);
        verify(userMapper).findAllUsers();
    }

    @Test
    void addUserTest() {
        User newUser = new User(3, "testUser3", "pass3", "role3", "email3", "time3");
        doNothing().when(userMapper).addUser(any(User.class));

        userService.save(newUser);
        verify(userMapper).addUser(newUser);
    }

    @Test
    void deleteUserByIdTest() {
        when(userMapper.deleteUserById(1)).thenReturn(1);
        int result = userService.delete(1);
        assertEquals(1, result);
        verify(userMapper).deleteUserById(1);
    }

    @Test
    void findUserByIdTest() {
        User expectedUser = new User(1, "testUser1", "pass1", "role1", "email1", "time1");
        when(userMapper.findUserById(1)).thenReturn(expectedUser);

        User actualUser = userService.findUserById(1);
        assertEquals(expectedUser, actualUser);
        verify(userMapper).findUserById(1);
    }

    @Test
    void updateUserTest() {
        User updatedUser = new User(1, "testUser1", "pass1", "role1", "email1", "time1");
        when(userMapper.updateUser(updatedUser)).thenReturn(1);

        int result = userService.update(updatedUser);
        assertEquals(1, result);
        verify(userMapper).updateUser(updatedUser);
    }
}
