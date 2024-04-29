package com.example.warehouse.mapper;

import com.example.warehouse.bean.Item;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemMapper {
    /**
     * 查询所有商品
     */
    // @Select("SELECT * FROM items")
    List<Item> findAllItems();

    /**
     * 添加商品
     */
    // @Insert("INSERT INTO items (name, category, description, stock, price, create_time, update_time) " +
    //         "VALUES (#{name}, #{category}, #{description}, #{stock}, #{price}, #{create_time}, #{update_time})")
    void addItem(Item item);

    /**
     * 根据id删除商品
     */
    // @Delete("DELETE FROM items WHERE id = #{id}")
    int deleteItemById(Integer id);

    /**
     * 根据id查询商品
     */
    // @Select("SELECT * FROM items WHERE id = #{id}")
    Item findItemById(int id);

    /**
     * 更新商品信息
     */
    // @Update("UPDATE items SET name=#{name}, category=#{category}, description=#{description}, " +
    //         "stock=#{stock}, price=#{price}, update_time=#{update_time} WHERE id=#{id}")
    int updateItem(Item item);
}
