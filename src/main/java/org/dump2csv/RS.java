package org.dump2csv;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

class RS {
    private final String name = "";
    private final String comment = "";
    private String SQLQuery;

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    public String getSQLQuery() {
        return SQLQuery;
    }

    public ArrayList<String> getHeader() {
        return header;
    }

    public ArrayList<String> getTypes() {
        return types;
    }

    public ArrayList<ArrayList<Object>> getRows() {
        return rows;
    }

    public String getError() {
        return error;
    }

    private final ArrayList<String> header = new ArrayList<>();
    private final ArrayList<String> types = new ArrayList<>();
    private final ArrayList<ArrayList<Object>> rows = new ArrayList<>();
    private String error;

    public void fillFrom(ResultSet rset) throws SQLException {
        ResultSetMetaData md = rset.getMetaData();
        final int ccount = md.getColumnCount();
        for (int idx = 1; idx <= ccount; idx++) {
            header.add(md.getColumnName(idx));
            types.add(md.getColumnTypeName(idx));
        }
        while (rset.next()) {
            ArrayList<Object> row = new ArrayList<>();
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
