package org.dump2csv;

import oracle.jdbc.pool.OracleDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class OracleDS implements DS {

    OracleDataSource ods;
    Connection conn;
    Config config;

    public OracleDS(){
    }

    public void open(Config config) throws java.sql.SQLException {
        this.config = config;
        ods = new OracleDataSource();
        ods.setURL(config.uri + config.database);
        ods.setUser(config.user);
        ods.setPassword(config.password);
        conn = ods.getConnection();
    }

    public ResultSet query(String query) throws java.sql.SQLException {
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(query);
    }

    public void close() throws java.sql.SQLException {
        conn.close();
    }

}
