package com.example.warehouse.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.example.warehouse.bean.User;

@Repository


public interface UserMapper {
    /**
     * 查询所有用户
     */
//     @Select("SELECT * FROM users")
    List<User> findAllUsers();

    /**
     * 增加保存用户
     */
//     @Insert("INSERT INTO users (username, password, role, email, register_time) " +
//             "VALUES (#{username}, #{password}, #{role}, #{email}, #{register_time})")
    void addUser(User user);

    /**
     * 根据id删除用户
     */
//     @Delete("DELETE FROM users WHERE id = #{id}")
    int deleteUserById(Integer id);

    /**
     * 根据id查找用户
     */
//     @Select("SELECT * FROM users WHERE id = #{id}")
    User findUserById(int id);

    /**
     * 更改用户信息
     */
//     @Update("UPDATE users SET username=#{username}, password=#{password}, " +
//             "role=#{role}, email=#{email}, register_time=#{register_time} WHERE id=#{id}")
    int updateUser(User user);

    /**
     * 根据用户名查询用户
     */
//     @Select("SELECT * FROM users WHERE username = #{username}")
    User findUserByUsername(String username);
}
