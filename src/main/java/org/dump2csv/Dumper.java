package org.dump2csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;


public class Dumper {

    public static void main(String[] args) {
        System.exit(cli(args));
    }

    public static int cli(String[] args) {
        Logger logger = LoggerFactory.getLogger(Dumper.class);
        int status = 0;
        try {
            if(1 == args.length) {
                if ("--version".equals(args[0])) {
                    System.out.println("j-dump2csv, version:" + version());
                }
                if ("--help".equals(args[0]) || "-h".equals(args[0])) {
                    System.out.println("j-dump2csv, version:" + version());
                    System.out.println(help());
                }
            }else {
                Config config = Config.load(System.in);
                DS ds = openDS(config);
                ResultSet rs = ds.query(config.query);
                dump(rs, config.output_format == Config.OUTPUT_FORMAT.CSV);
                rs.close();
            }
        }catch (Throwable t){
            status = 1;
            logger.error(t.getMessage());
        }
        return status;
    }

    public static void dump(ResultSet rs, boolean useDefault) throws SQLException, IOException {
        final CSVPrinter printer = (useDefault? CSVFormat.DEFAULT : CSVFormat.TDF)
                .withHeader(rs).print(new PrintStream(System.out));
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
    }

    /**
     * Factory method to open DS
     * @param config
     * @return
     * @throws SQLException
     */
    static DS openDS(Config config) throws SQLException {
        DS ds = null;
        switch (config.dbtype){
            case ORACLE: ds = new OracleDS(); break;
            case H2: ds = new H2DS(); break;
            case UNKNOWN:
            default:
                throw new RuntimeException("unsupported DB type: '"+ config.dbtype+"'");
        }
        ds.open(config);
        return ds;
    }

    static public String version() {
        return System.getProperty("org.dump2csv.Dumper.version");
    }

    static public String help() throws IOException {
        return ResourceHelpers.readText(ResourceHelpers.getReader("help.txt"));
    }
}
