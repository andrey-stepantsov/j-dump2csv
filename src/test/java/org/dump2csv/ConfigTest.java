package org.dump2csv;

import org.junit.Test;

import java.sql.SQLException;

import static org.dump2csv.ResourceHelpers.getStream;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ConfigTest {

    @Test
    public void detectH2source() throws SQLException {
        Config config = Config.load(getStream("test-config-h2-01.yaml"));
        assertEquals(Config.DBTYPE.H2, config.dbtype);
        assertEquals(Config.OUTPUT_FORMAT.TSV, config.output_format);
        DS ds = Dumper.openDS(config);
        assertTrue(ds instanceof H2DS);
    }

}
