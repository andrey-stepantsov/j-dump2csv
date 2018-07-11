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

    /**
     * Checks the CLI options and returns 0 if all is well
     * In the current version no control options are being fed through
     * the command line, only --help and --version
     * @param args
     * @return 0 if continue work, not 0 if to quit
     * @throws IOException
     */
    public static int options(String[] args) throws IOException {
        int status = 0;
        switch (args.length) {
            case 0:
                break;
            case 1:
                switch (args[0]) {
                    case "--version":
                        System.out.println("j-dump2csv, version:" + version());
                        status = 1;
                        break;
                    case "-h":
                    case "--help":
                        System.out.println(help());
                        status = 1;
                        break;
                    default:
                        System.out.println("use -h or --help for information; an unknown option: " + args[0]);
                        status = 1;
                }
                break;
            default:
                System.out.println("too many options; use -h or --help for information");
        }
        return status;
    }

    public static int cli(String[] args) {
        Logger logger = LoggerFactory.getLogger(Dumper.class);
        int status = 0;
        try {
            if (0 == options(args)) {
                Config config = Config.load(System.in);
                DS ds = openDS(config);
                ResultSet rs = ds.query(config.query);
                dump(rs, config.output_format == Config.OUTPUT_FORMAT.CSV);
                rs.close();
            }
        } catch (Throwable t) {
            status = 1;
            logger.error(t.getMessage());
        }
        return status;
    }

    public static void dump(ResultSet rs, boolean useDefault) throws SQLException, IOException {
        final CSVPrinter printer = (useDefault ? CSVFormat.DEFAULT : CSVFormat.TDF)
                .withHeader(rs).print(new PrintStream(System.out));
        ResultSetMetaData md = rs.getMetaData();
        int columnCount = md.getColumnCount();
        while (rs.next()) {
            ArrayList<Object> l = new ArrayList(columnCount);
            for (int i = 1; i <= columnCount; i++) {
                l.add(rs.getObject(i));
            }
            printer.printRecord(l);
        }
    }

    /**
     * Factory method to open DS
     *
     * @param config
     * @return
     * @throws SQLException
     */
    static DS openDS(Config config) throws SQLException {
        DS ds = null;
        switch (config.dbtype) {
            case ORACLE:
                ds = new OracleDS();
                break;
            case H2:
                ds = new H2DS();
                break;
            case UNKNOWN:
            default:
                throw new RuntimeException("unsupported DB type: '" + config.dbtype + "'");
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
