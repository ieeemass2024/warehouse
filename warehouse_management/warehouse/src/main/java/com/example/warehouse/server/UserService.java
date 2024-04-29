package com.example.warehouse.server;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.warehouse.bean.User;
import com.example.warehouse.mapper.UserMapper;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper; // 注入UserMapper

    // 查询所有用户
    public List<User> userList() {
        return userMapper.findAllUsers(); // 确保这里的方法名与Mapper中定义的一致
    }

    // 增加保存用户
    public void save(User user) {
        userMapper.addUser(user);
    }

    // 更改用户信息
    public int update(User user) {
        return userMapper.updateUser(user); // 确保参数传递是正确的
    }

    // 根据id查找用户
    public User findUserById(int id) {
        return userMapper.findUserById(id);
    }

    // 根据id删除用户
    public int delete(Integer id) {
        return userMapper.deleteUserById(id);
    }

    // 用户认证
    public User authenticate(String username, String password) {
        User user = userMapper.findUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}