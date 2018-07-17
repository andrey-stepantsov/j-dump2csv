package org.dump2csv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class MySQLDS implements DS {

    private static final String jar = "com.mysql.cj.jdbc.Driver";
    private static final String notFoundMessage = "Where is your MySQL JDBC Driver?";

    private Logger logger = LoggerFactory.getLogger(MySQLDS.class);

    private Connection conn;

    public MySQLDS(){
    }

    public void open(final Config config) throws java.sql.SQLException {
        try {
            Class.forName(jar);
        } catch (ClassNotFoundException e) {
            logger.error(notFoundMessage);
            throw new java.sql.SQLException(notFoundMessage);
        }
        Properties info = new Properties();
        String url = config.uri + config.database;
        if(null != config.user && !config.user.isEmpty())
            info.setProperty("user", config.user);
        if(null != config.password && !config.password.isEmpty())
            info.setProperty("password", config.password);
        conn = DriverManager.getConnection(url, info);
    }

    public ResultSet query(String query) throws java.sql.SQLException {
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(query);
    }

    public void close() throws java.sql.SQLException {
        conn.close();
    }

}
