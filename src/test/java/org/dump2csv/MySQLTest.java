package org.dump2csv;


import org.h2.jdbcx.JdbcDataSource;
import org.junit.Test;

import java.sql.*;

public class MySQLTest {

    @Test
    public void example() {


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
            return;
        }

        System.out.println("MySQL JDBC Driver Registered!");
        Connection connection = null;

        try {
            connection = DriverManager
                    .getConnection("jdbc:mysql://localhost:13306/testdb"
                                    + "?useUnicode=true&useJDBCCompliantTimezoneShift=true"
                                    + "&useLegacyDatetimeCode=true&serverTimezone=UTC"
//                           + "?user=vagrant&password=test");
                            , "testuser", "test");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from info;");
            int colCount = rs.getMetaData().getColumnCount();
            for (int i = 1; i <= colCount; i++){
                System.out.print(rs.getMetaData().getColumnName(i)+"|");
            }
            System.out.println("");
            while (rs.next()){
                for (int i = 1; i <= colCount; i++){
                    System.out.print(rs.getString(i)+"|");
                }
                System.out.println("");
            }

        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
        }

        if (connection != null) {
            System.out.println("You made it, take control your database now!");
        } else {
            System.out.println("Failed to make connection!");
        }
    }

    @Test
    public void example1() {


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
            return;
        }

        System.out.println("MySQL JDBC Driver Registered!");
        Connection connection = null;

        try {
            JdbcDataSource mySqlDS = new JdbcDataSource();
            mySqlDS.setURL( "jdbc:mysql://localhost:13306/testdb"
                            + "?useUnicode=true&useJDBCCompliantTimezoneShift=true"
                            + "&useLegacyDatetimeCode=true&serverTimezone=UTC"
            );
            mySqlDS.setUser("testuser");
            mySqlDS.setPassword("test");
            connection = mySqlDS .getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from info;");
            int colCount = rs.getMetaData().getColumnCount();
            for (int i = 1; i <= colCount; i++){
                System.out.print(rs.getMetaData().getColumnName(i)+"|");
            }
            System.out.println("");
            while (rs.next()){
                for (int i = 1; i <= colCount; i++){
                    System.out.print(rs.getString(i)+"|");
                }
                System.out.println("");
            }

        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
        }

        if (connection != null) {
            System.out.println("You made it, take control your database now!");
        } else {
            System.out.println("Failed to make connection!");
        }
    }
}

