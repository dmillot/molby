package model;


import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    // Database connection method
    public static Connection connectDatabase()
    {
        Connection connection = null;
        System.out.println("Attempting to connect...");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3308/molby?useUnicode=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            System.out.println("Connection done !");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Connection error");
        }

        return connection;
    }
}
