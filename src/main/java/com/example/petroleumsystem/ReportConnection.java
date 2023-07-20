package com.example.petroleumsystem;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ReportConnection {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/petroleum_system";
    private static final String USER = "root";
    private static final String PASS = "";

    public static Connection createConnection() throws SQLException, ClassNotFoundException {
        Connection conn;

        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
//        try {
//            Class.forName(JDBC_DRIVER);
//            conn = DriverManager.getConnection(DB_URL, USER, PASS);
//        } catch (ClassNotFoundException | SQLException ex) {
//            ex.printStackTrace();
//        }
        return conn;
    }
}
