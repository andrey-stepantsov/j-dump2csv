package org.dump2csv;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;

public class Config {
    public DBTYPE dbtype = DBTYPE.UNKNOWN;
    public OUTPUT_FORMAT output_format;
    public String user;
    public String password;
    public String database;
    public String uri;
    public String query;

    public static Config load(InputStream stream) {
        Yaml y = new Yaml();
        return y.loadAs(stream, Config.class);
    }

    public enum DBTYPE {
        ORACLE("ORACLE"),
        H2("H2"),
        UNKNOWN("UNKNOWN");

        private final String value;

        DBTYPE(final String value) {
            this.value = value;
        }
    }

    public enum OUTPUT_FORMAT {
        CSV("CSV"),
        TSV("TSV"),
        YAML("YAML"),
        JSON("JSON"),
        YAMLS("YAMLS");

        private final String value;

        OUTPUT_FORMAT(final String value) {
            this.value = value;
        }
    }

}
