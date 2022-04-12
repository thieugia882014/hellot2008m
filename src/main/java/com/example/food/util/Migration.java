package com.example.food.util;

//import com.example.food.annotation.Table;
//import com.example.food.annotation.Column;
//import org.reflections.Reflections;
//
//import java.lang.reflect.Field;
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.Set;
//
//public class Migration {
//
//    public static void main(String[] args){
//        Reflections reflections = new Reflections("com.example.food");
//        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Table.class);
//        for (Class<?> table : annotated){
//            createTableFromEntity(table);
//        }
//    }
//
//    private static void createTableFromEntity(Class clazz) {
//        if (!clazz.isAnnotationPresent(Table.class)){
//            return;
//        }
//        StringBuilder sqlStringbuiler = new StringBuilder();
//        sqlStringbuiler.append("CREATE TABLE");
//        sqlStringbuiler.append(" ");
//        String tablerName = clazz.getSimpleName().toLowerCase() + "s";
//        Table table = (Table) clazz.getDeclaredAnnotation(Table.class);
//        if (table.name() != null && table.name().length()>0){
//            tablerName = table.name();
//        }
//        sqlStringbuiler.append(tablerName);
//        sqlStringbuiler.append(" ");
//        sqlStringbuiler.append("(");
//        Field[] fields = clazz.getDeclaredFields();
//        for (Field field : fields) {
//            String coloumnName = field.getName();
//            String coloumnType = "";
//            if (field.getType().getSimpleName().contains("String")) {
//                coloumnType = "VARCHAR(250)";
//            } else {
//                coloumnType = field.getType().getSimpleName();
//            }
//            if (field.isAnnotationPresent(Column.class)) {
//                Column column = field.getDeclaredAnnotation(Column.class);
//                coloumnName = column.name();
//                coloumnType = column.type();
//            }else {
//                continue;
//            }
//            sqlStringbuiler.append(coloumnName);
//            sqlStringbuiler.append(" ");
//            sqlStringbuiler.append(coloumnType);
//            sqlStringbuiler.append(",");
//        }
//        sqlStringbuiler.setLength(sqlStringbuiler.length()-1);
//        sqlStringbuiler.append(")");
//        System.out.println(sqlStringbuiler.toString());
//
//        try {
//            Connection connection = ConnectionHelper.getConnection();
//            Statement dropStatement = connection.createStatement();
//            dropStatement.execute(String.format("DROP TABLE %s",tablerName));
//            System.out.printf("DROP TABLE %s SUCCESS!",tablerName);
//        }catch (SQLException | ClassNotFoundException e){
//            System.err.println(e.getMessage());
//        }
//        try {
//            Connection connection = ConnectionHelper.getConnection();
//            Statement stt = connection.createStatement();
//            stt.execute(sqlStringbuiler.toString());
//            System.out.printf("CREATE TABLE %s SUCCESS!",tablerName);
//        }catch (SQLException| ClassNotFoundException e){
//            System.err.println(e.getMessage());
//        }
//    }
//}

import com.example.food.annotation.Column;
import com.example.food.modelAnnotation.ForeignKey;
import com.example.food.modelAnnotation.Id;
import com.example.food.annotation.Table;
import com.example.food.util.Config;
import com.example.food.util.SQLConfig;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.Set;

public class Migration {
    private static Connection connection;
    public static final String scanFolder = "com.example.food";

    static {
        try {
            connection = ConnectionHelper.getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        Set<Class<?>> annotated = getAnnotation();
        for (Class<?> table : annotated
        ) {
            createTable(table);
        }
//        dropAllTable();
    }

    public static boolean isExistsTable(String tableName) {
        try {
            String checkSql = SQLConfig.SELECT +
                    SQLConfig.SPACE +
                    SQLConfig.COUNT +
                    SQLConfig.OPEN_BRACKET +
                    SQLConfig.STAR +
                    SQLConfig.CLOSE_BRACKET +
                    SQLConfig.SPACE +
                    SQLConfig.FROM +
                    SQLConfig.SPACE +
                    SQLConfig.INFORMATION_SCHEMA +
                    SQLConfig.SPACE +
                    SQLConfig.WHERE +
                    SQLConfig.SPACE +
                    SQLConfig.TABLE_SCHEMA +
                    SQLConfig.SPACE +
                    SQLConfig.EQUAL_SIGN +
                    SQLConfig.SPACE +
                    SQLConfig.QUESTION +
                    SQLConfig.SPACE +
                    SQLConfig.AND +
                    SQLConfig.SPACE +
                    SQLConfig.TABLE_NAME +
                    SQLConfig.EQUAL_SIGN +
                    SQLConfig.SPACE +
                    SQLConfig.QUESTION;
            PreparedStatement stt = connection.prepareStatement(checkSql);
            stt.setString(1, Config.DATABASE_URL);
            stt.setString(2, tableName);
            ResultSet resultSet = stt.executeQuery();

            if (resultSet.next()) {
                if (resultSet.getInt(1) > 0)
                    return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    public static void dropAllTable() {
        Set<Class<?>> annotated = getAnnotation();
        for (Class<?> table : annotated
        ) {
            dropForeignKey(table);

        }
        for (Class<?> table : annotated
        ) {
            dropTable(table);

        }
    }

    public static void dropForeignKey(Class clazz) {
        if (!clazz.isAnnotationPresent(Table.class)) {
            return;
        }
        String tableName = clazz.getSimpleName().toLowerCase() + "s";

        Table table = (Table) clazz.getDeclaredAnnotation(Table.class);
        if (table.name().length() > 0) {
            tableName = table.name();
        }
        Field[] listField = clazz.getDeclaredFields();
        for (Field field : listField) {
            if (!field.isAnnotationPresent(ForeignKey.class)) {
                continue;
            }
            ForeignKey foreignKey = field.getDeclaredAnnotation(ForeignKey.class);
            String dropColumnKey = SQLConfig.ALTER_TABLE + SQLConfig.SPACE + tableName + SQLConfig.SPACE + SQLConfig.DROP + SQLConfig.SPACE + SQLConfig.FOREIGN_KEY + SQLConfig.SPACE + getConstraintName(foreignKey, tableName);
            System.out.println(dropColumnKey);
            try {
                PreparedStatement stt = connection.prepareStatement(dropColumnKey);
                stt.execute();
                return;
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    public static void dropTable(Class clazz) {
        if (!clazz.isAnnotationPresent(Table.class)) {
            return;
        }
        StringBuilder sqlString = new StringBuilder();
        sqlString.append(SQLConfig.CREATE_TABLE);
        sqlString.append(SQLConfig.SPACE);
        String tableName = clazz.getSimpleName().toLowerCase() + "s";
        Table table = (Table) clazz.getDeclaredAnnotation(Table.class);
        if (table.name().length() > 0) {
            tableName = table.name();
        }
        if (!isExistsTable(tableName)) {
            return;
        }
        try {
            Statement DropStt = connection.createStatement();
            DropStt.execute(String.format(SQLConfig.DROP_TABLE + " %s", tableName));
            System.out.printf("Delete table %s success\n", tableName);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void createTable(Class clazz) {
        if (!clazz.isAnnotationPresent(Table.class)) {
            return;
        }
        StringBuilder sqlString = new StringBuilder();
        sqlString.append(SQLConfig.CREATE_TABLE);
        sqlString.append(SQLConfig.SPACE);
        String tableName = clazz.getSimpleName().toLowerCase() + "s";
        Table table = (Table) clazz.getDeclaredAnnotation(Table.class);
        if (table.name().length() > 0) {
            tableName = table.name();
        }
        if (isExistsTable(tableName)) {
            System.out.println("OUT");
            return;
        }
        sqlString.append(tableName);
        sqlString.append(SQLConfig.SPACE);
        sqlString.append(SQLConfig.OPEN_BRACKET);
        Field[] listField = clazz.getDeclaredFields();
        for (Field field : listField
        ) {
            String columnName = field.getName();
            String columnType = "";
            if (field.getType().getSimpleName().contains("String")) {
                columnType = "VARCHAR(250)";
            } else {
                columnType = field.getType().getSimpleName();
            }
            if (field.isAnnotationPresent(Column.class)) {
                Column column = field.getDeclaredAnnotation(Column.class);
                columnName = column.name();
                columnType = column.type();
                sqlString.append(columnName);
                sqlString.append(SQLConfig.SPACE);
//            sqlString.append(field.getType().getSimpleName());
                sqlString.append(columnType);
                sqlString.append(SQLConfig.SPACE);
                if (field.isAnnotationPresent(Id.class)) {
                    Id id = field.getDeclaredAnnotation(Id.class);
                    sqlString.append(SQLConfig.PRIMARY_KEY);
                    sqlString.append(SQLConfig.SPACE);
                    if (id.AutoIncrement()) {
                        sqlString.append(SQLConfig.AUTO_INCREMENT);
                        sqlString.append(SQLConfig.SPACE);
                    }
                }
                if (field.isAnnotationPresent(ForeignKey.class)) {
                    ForeignKey foreignKey = field.getAnnotation(ForeignKey.class);
                    // check có bảng hay chưa
                    if (!isExistsTable(foreignKey.referenceTable())) {
                        Set<Class<?>> annotated = getAnnotation();
                        for (Class<?> preTable : annotated
                        ) {
                            Table preTableName = preTable.getDeclaredAnnotation(Table.class);
                            if (preTableName.name().equals(foreignKey.referenceTable())) {
                                System.out.println("Create");
                                createTable(preTable);
                            }
                        }
                    }
                    String constraintName = getConstraintName(foreignKey, tableName);

                    //end
                    sqlString.append(SQLConfig.COMMA);
                    sqlString.append(SQLConfig.CONSTRAINT);
                    sqlString.append(SQLConfig.SPACE);
                    sqlString.append(constraintName);
                    sqlString.append(SQLConfig.SPACE);
                    sqlString.append(SQLConfig.FOREIGN_KEY);
                    sqlString.append(SQLConfig.SPACE);
                    sqlString.append(SQLConfig.OPEN_BRACKET);
                    sqlString.append(columnName);
                    sqlString.append(SQLConfig.CLOSE_BRACKET);
                    sqlString.append(SQLConfig.SPACE);
                    sqlString.append(SQLConfig.REFERENCES);
                    sqlString.append(SQLConfig.SPACE);
                    sqlString.append(foreignKey.referenceTable());
                    sqlString.append(SQLConfig.OPEN_BRACKET);
                    sqlString.append(foreignKey.referenceColumn());
                    sqlString.append(SQLConfig.CLOSE_BRACKET);
                }
                sqlString.append(SQLConfig.COMMA);
            }

        }
        sqlString.setLength(sqlString.length() - 1);
        sqlString.append(SQLConfig.CLOSE_BRACKET);
        System.out.println(sqlString);
        try {
            PreparedStatement statement = connection.prepareStatement(sqlString.toString());
            statement.execute();
            System.out.printf(SQLConfig.CREATE_TABLE + " %s success%n", tableName);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static String getConstraintName(ForeignKey foreignKey, String tableName) {
        if (foreignKey.constraint().length() > 0) {
            return foreignKey.constraint();
        } else {
            return "fk_" + tableName + '_' + foreignKey.referenceTable();
        }
    }

    private static Set<Class<?>> getAnnotation() {
        Reflections reflections = new Reflections(scanFolder);
        return reflections.getTypesAnnotatedWith(Table.class);
    }

}

