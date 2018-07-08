package org.dump2csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.dump2csv.ResourceHelpers.getURL;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/***
 * Module for exploring H2 DB database (mostly in-memory related features)
 */

public class H2FakeDSTest {

    private Connection conn = null;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Before
    public void before() throws SQLException {
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:db1");
        ds.setUser("sa");
        ds.setPassword("sa");
        conn = ds.getConnection();
        conn.createStatement().execute("CREATE TABLE TESTCSV AS SELECT * FROM CSVREAD('"+
                getURL("test01.csv")
                +"');");
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void after() throws SQLException {
        System.setOut(null);
        System.setErr(null);
        conn.close();
    }


    @Test
    public void createAndLoadATableFromCSV() throws SQLException {
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:db1");
        ds.setUser("sa");
        ds.setPassword("sa");
        Connection conn = ds.getConnection();
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM TESTCSV;");
        while (rs.next())
        {
            System.out.println(rs.getString(1));
            System.out.println(rs.getString(2));
        }
        rs.close();
        conn.close();
        assertEquals("1\nHello\n2\nWorld!\n3\nAgain!\n", outContent.toString());
    }

    @Test
    public void printCSV() throws SQLException, IOException {
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:db1");
        ds.setUser("sa");
        ds.setPassword("sa");
        Connection conn = ds.getConnection();
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM TESTCSV;");
        final CSVPrinter printer = CSVFormat.DEFAULT.withHeader(rs).print(new PrintStream(outContent));
        ResultSetMetaData md = rs.getMetaData();
        int columnCount = md.getColumnCount();
        while (rs.next())
        {
            ArrayList<Object> l = new ArrayList(columnCount);
            for(int i = 1; i <= columnCount; i++){
                l.add(rs.getObject(i));
            }
            printer.printRecord(l);
        }
        rs.close();
        conn.close();
        assertThat( outContent.toString(), RegexMatcher.matches("NUM,TEXT\\s+1,Hello\\s+2,World!\\s+3,Again!\\s+"));
    }

}
