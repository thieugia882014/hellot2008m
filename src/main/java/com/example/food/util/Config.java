package com.example.food.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class Config {
    public static final String DATABASE_URL = "jdbc:mysql://localhost:3306/t2008m";
    public static final String DATABASE_USERNAME = "root";
    public static final String DATABASE_PASSWORD = "";
    public static final String DATABASE_DRIVER_CLASS = "com.mysql.jdbc.Driver";
}