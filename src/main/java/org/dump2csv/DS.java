package org.dump2csv;


interface DS {
    void open(final Config config) throws java.sql.SQLException;
//    RS query(String query) throws java.sql.SQLException;
    java.sql.ResultSet query(String query) throws java.sql.SQLException;
    void close() throws java.sql.SQLException;
}
