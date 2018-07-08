package org.dump2csv;

import org.h2.jdbcx.JdbcDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class H2DS implements DS {

    JdbcDataSource h2ds;
    Connection conn;
    Config config;

    public H2DS(){
    }

    public void open(Config config) throws java.sql.SQLException {
        this.config = config;
        h2ds = new JdbcDataSource();
        h2ds.setURL(config.uri + config.database);
        if(null != config.user && !config.user.isEmpty())
            h2ds.setUser(config.user);
        if(null != config.password && !config.password.isEmpty())
            h2ds.setPassword(config.password);
        conn = h2ds.getConnection();
    }

    public ResultSet query(String query) throws java.sql.SQLException {
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(query);
    }

    public void close() throws java.sql.SQLException {
        conn.close();
    }

}
