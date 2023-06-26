package com.example.petroleumsystem;
import javafx.scene.control.Button;

import java.io.IOException;
import  java.sql.*;
public class db{
    public  static  Connection connection;
    public static  Statement statement;

    public static PreparedStatement preparedStatement;
    public static ResultSet resultSet;
    public db(String sql_query) throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/petroleum_system" , "root" , "");
        statement = connection.createStatement();
        resultSet = statement.executeQuery(sql_query);
    }

}
