package com.example.food.entity;

import com.example.food.annotation.Column;
import com.example.food.annotation.Table;

import java.util.HashMap;
import java.util.Objects;

@Table(name= "products")
public class Product {
    @Column(name = "id", type="INT PRIMARY KEY AUTO_INCREMENT")
    private int id;
    @Column(name = "name",type = "VARCHAR(200)")
    private String name;
    @Column(name = "thumbnail",type = "TEXT")
    private String thumbnail;
    @Column(name = "price",type = "DOUBLE")
    private double price;
    @Column(name = "status",type = "INT")
    private int status;

    public Product() {
    }

    public Product(String name, String thumbnail, double price) {
        this.name = name;
        this.thumbnail = thumbnail;
        this.price = price;
    }

    public Product(int id, String name, String thumbnail, double price, int status) {
        this.id = id;
        this.name = name;
        this.thumbnail = thumbnail;
        this.price = price;
        this.status = status;
    }
    public void checkValid(){
        this.error = new HashMap<>();
        if (this.name==null||this.name.length()==0){
            this.error.put("name","name requied");
        }
        if (this.thumbnail==null||this.thumbnail.length()==0){
            this.error.put("thumbnail","thumbnailrequied");
        }
        if (this.price==0){
            this.error.put("price","price requied");
        }
    }
    public HashMap<String,String>error;
    public HashMap<String,String>getErrors(){
        checkValid();
        return error;
    }

    public boolean isvalid(){
        checkValid();
        return error ==null||error.size()==0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", price=" + price +
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
