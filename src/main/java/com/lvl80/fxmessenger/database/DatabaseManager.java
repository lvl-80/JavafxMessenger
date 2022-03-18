package com.lvl80.fxmessenger.database;

import java.sql.*;
import java.util.Objects;

public class DatabaseManager {

    // Ссылка на локальную БД
    private final static String url = "jdbc:mysql://localhost:8889/Messenger";
    // Имя пользователя
    private final static String user = "user";
    // Пароль для пользователя
    private final static String password = "testuser";

    // Метод подключения к БД
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    // Метод получения Statement
    public static Statement getStatement() {
        try {
            return getConnection().createStatement();
        }catch (SQLException e){System.out.println("#Error DatabaseManager -> getStatement()");}
        return null;
    }

    // Метод получения результата SQL-запроса из БД
    public static ResultSet getResultSet(String _request) {
        try {
            return Objects.requireNonNull(getStatement()).executeQuery("SELECT " + _request + " FROM Users");
        }catch (SQLException e){System.out.println("#Error DatabaseManager -> getResultSet()");}
        return null;
    }

    // Завершине работы с БД
    public static void disconnect(){
        try {
            getConnection().close();
        } catch (SQLException e) {System.out.println("#Error DatabaseManager -> disconnect");};
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {System.out.println("#Error DatabaseManager -> disconnect");}});
    }
}
