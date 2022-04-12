package com.example.food.entity;


import com.example.food.annotation.Column;
import com.example.food.annotation.Table;

@Table(name= "Category")
public class Category {
    @Column(name = "id", type = "INT")
    private int id;
    @Column(name = "categoryName", type = "VARCHAR(250)")
    private String categoryName;
    @Column(name = "status", type = "INT DEFAULT 1")
    private int status;

    public Category() {
    }

    public Category(int id, String categoryName, int status) {
        this.id = id;
        this.categoryName = categoryName;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", categoryName='" + categoryName + '\'' +
                ", status=" + status +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
