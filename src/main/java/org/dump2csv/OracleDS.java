package org.dump2csv;

import oracle.jdbc.pool.OracleDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class OracleDS implements DS {

    private Connection conn;

    public OracleDS(){
    }

    public void open(final Config config) throws java.sql.SQLException {
        OracleDataSource ods = new OracleDataSource();
        ods.setURL(config.uri + config.database);
        if(null != config.user && !config.user.isEmpty())
            ods.setUser(config.user);
        if(null != config.password && !config.password.isEmpty())
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
