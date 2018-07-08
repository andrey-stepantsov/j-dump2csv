package org.dump2csv;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class RS {
    public String name = "";
    public String comment = "";
    public String SQLQuery;
    public ArrayList<String> header = new ArrayList<>();
    public ArrayList<String> types = new ArrayList<>();
    public ArrayList<ArrayList<Object>> rows = new ArrayList<>();
    public String error;

    public void fillFrom(ResultSet rset) throws SQLException {
        ResultSetMetaData md = rset.getMetaData();
        final int ccount = md.getColumnCount();
        for (int idx = 1; idx <= ccount; idx++) {
            header.add(md.getColumnName(idx));
            types.add(md.getColumnTypeName(idx));
        }
        while (rset.next()) {
            ArrayList<Object> row = new ArrayList<Object>();
            rows.add(row);
            for (int idx = 1; idx <= ccount; idx++) {
                switch (md.getColumnTypeName(idx)) {
                    case "DATE":
                        row.add(rset.getDate(idx));
                        break;
                    case "NUMBER":
                        row.add(rset.getDouble(idx));
                        break;
                    case "VARCHAR2":
                        row.add(rset.getString(idx));
                        break;
                    default:
                        row.add(rset.getString(idx));
                }
            }
        }
    }
}
