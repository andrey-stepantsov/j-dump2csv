package org.dump2csv;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.dump2csv.ResourceHelpers.getURL;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class CSVFilterTests {

    @Test
    public void CSVFilterTest(){
        String config = "dbtype: H2\n" +
                "uri: 'jdbc:h2:mem:'\n" +
                "database: test1\n" +
//                "user: sa\n" +
//                "password: sa\n" +
                "query: |-\n" +
                "  SELECT * FROM CSVREAD('"+getURL("csv-filter-test-01.csv")+"') WHERE CODE like '%1';\n" +
                "output_format: CSV";
        System.setIn(new ByteArrayInputStream(config.getBytes(StandardCharsets.UTF_8)));
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(new PrintStream(outContent)));
        assertEquals(0, Dumper.cli(new String[0]));
        assertThat(outContent.toString(), RegexMatcher.matches("CODE,ALIAS\\s+0001,Snake\\s+"));
    }

}
