/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import control.PersonDAOImpl;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author DuongLee
 */
public class ConnectDB {
    public static Connection connect;
    
    public static Connection getConnect(String dbClass, String dbUrl, String userName, String password) throws SQLException {
        Connection conn = null;
        try {
            Class.forName(dbClass);
            conn = DriverManager.getConnection(dbUrl, userName, password);
            
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
        
        return conn;
    }

    public static Connection getCon() {
        if (connect == null)
        {
            String dbUrl = "jdbc:mysql://localhost:3306/miostore"+"?useUnicode=true&characterEncoding=UTF-8";
            String dbClass = "com.mysql.jdbc.Driver";
            try {
                Class.forName(dbClass);
                connect =  getConnect(dbClass, dbUrl, "root", "");
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println(e);
            }
        }
        return connect;
    }
}
