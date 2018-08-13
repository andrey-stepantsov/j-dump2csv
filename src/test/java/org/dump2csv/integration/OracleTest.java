package org.dump2csv.integration;

import org.dump2csv.Config;
import org.dump2csv.Dumper;
import org.junit.Before;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;

import static org.junit.Assert.assertTrue;

public class OracleTest {

    private Config config = new Config();
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUp(){

        config.user = System.getProperty("test.oracle.user", "testuser");
        config.password = System.getProperty("test.oracle.password", "test");
        config.uri = System.getProperty("test.oracle.uri","jdbc:oracle://localhost:1521/");
        config.database = System.getProperty("test.oracle.database", "testdb");

        config.dbtype = Config.DBTYPE.ORACLE;
        System.setOut(new PrintStream(outContent));
    }

    void setConfig(){
        ByteArrayInputStream bs =
                new ByteArrayInputStream((new Yaml().dump(config)).getBytes(Charset.defaultCharset()));
        System.setIn(bs);
    }

    @Test
    public void test(){
        config.query = "select * from info;";
        config.output_format = Config.OUTPUT_FORMAT.TSV;
        setConfig();
        Dumper.cli(new String[0]);
        assertTrue(outContent.toString().contains("snake\tlives in the desert"));
        assertTrue(outContent.toString().contains("rat\tlives in the hole"));
    }

}

