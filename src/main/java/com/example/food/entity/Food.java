package com.example.food.entity;

import com.example.food.annotation.Column;
import com.example.food.annotation.Table;
import com.example.food.modelAnnotation.ForeignKey;
import com.example.food.modelAnnotation.Id;

import java.security.Timestamp;
import java.util.HashMap;

@Table(name= "food")
public class Food {
    @Id(AutoIncrement = true)
    @Column(name = "id", type = "INT PRIMARY KEY AUTO_INCREMENT")
    private int id;
    @Column(name = "name", type = "VARCHAR(250)")
    private String name;
    @ForeignKey(referenceColumn = "id", referenceTable = "categories")
    @Column(name = "categoryId", type = "INT" )
    private int categoryId;
    @Column(name = "description", type = "TEXT(250)")
    private String description;
    @Column(name = "thumbnail", type = "TEXT")
    private String thumbnail;
    @Column(name = "price", type = "DOUBLE")
    private double price;
    @Column(name = "startDay", type = "TIMESTAMP NOT NULL DEFAULT CURRENT_DATE()")
    private Timestamp startDay;
    @Column(name = "DayUpdate", type = "TIMESTAMP NOT NULL DEFAULT CURRENT_DATE()")
    private Timestamp DayUpdate;
    @Column(name = "status", type = "INT DEFAULT 1")
    private int status;
    private HashMap<String, String> errors;

    public Food() {
    }

    public Food(int id, String name, int categoryId, String description, String thumbnail, double price, Timestamp startDay, Timestamp dayUpdate, int status) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
        this.description = description;
        this.thumbnail = thumbnail;
        this.price = price;
        this.startDay = startDay;
        this.DayUpdate = dayUpdate;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Food{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", categoryId=" + categoryId +
                ", description='" + description + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", price=" + price +
                ", startDay=" + startDay +
                ", DayUpdate=" + DayUpdate +
                ", status=" + status +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Timestamp getStartDay() {
        return startDay;
    }

    public void setStartDay(Timestamp startDay) {
        this.startDay = startDay;
    }

    public Timestamp getDayUpdate() {
        return DayUpdate;
    }

    public void setDayUpdate(Timestamp dayUpdate) {
        DayUpdate = dayUpdate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
