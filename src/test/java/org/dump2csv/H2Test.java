package org.dump2csv;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.dump2csv.ResourceHelpers.getURL;
import static org.junit.Assert.assertEquals;

/***
 * Module for exploring H2 DB database (mostly in-memory related features)
 */

public class H2Test {

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
    public void helloWorld() throws SQLException {
        ResultSet rs = conn.createStatement().executeQuery("SELECT 'Hello World'");
        while (rs.next())
        {
            System.out.println(rs.getString(1));
        }
        rs.close();
        assertEquals("Hello World\n", outContent.toString());
    }

    @Test
    public void createAndFillATable() throws SQLException {
        conn.createStatement().execute("CREATE TABLE TEST(ID INT, NAME VARCHAR);");
        conn.createStatement().execute("INSERT INTO TEST VALUES(1, 'Hello'), (2, 'World');");

        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM TEST");

        while (rs.next()) {
            System.out.println(rs.getString(1));
            System.out.println(rs.getString(2));
        }
        rs.close();
        assertEquals("1\nHello\n2\nWorld\n", outContent.toString());
    }

    @Test
    public void createAndLoadATableFromCSV() throws SQLException {

        conn.createStatement().execute("CREATE TABLE TESTCSV AS SELECT * FROM CSVREAD('"+
                getURL("test01.csv")
                +"');");
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM TESTCSV;");
        while (rs.next())
        {
            System.out.println(rs.getString(1));
            System.out.println(rs.getString(2));
        }
        rs.close();
        assertEquals("1\nHello\n2\nWorld!\n3\nAgain!\n", outContent.toString());
    }


}
