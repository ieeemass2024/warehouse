package com.example.warehouse.bean;

import java.io.Serializable;
import java.util.Date;

public class Item implements Serializable {
    private static final long serialVersionUID = 1L;
    //仓库管理系统的物品类
    private Integer id;
    private String name;
    private String category;
    private String description;
    private Integer stock;//库存
    private double price;
    private Date create_time;
    private Date update_time;

    // 无参数的默认构造器
    public Item() {
    }

    public Item(int i, String item1, String type1, int i1, double v) {
        this.id = i;
        this.name = item1;
        this.category = type1;
        this.stock = i1;
        this.price = v;
    }

    // Getters and setters
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Integer getStock() {
        return stock;
    }
    public void setStock(Integer stock) {
        this.stock = stock;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }
}
