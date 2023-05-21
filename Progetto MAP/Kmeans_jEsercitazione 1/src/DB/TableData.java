package DB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import DB.DbAccess;
import DB.EmptySetException;
import DB.Example;
import DB.NoValueException;
import DB.Table_Schema.Column;


public class TableData {
    private DbAccess db;

    public TableData(DbAccess db) {
        this.db = db;
    }

    public List<Example> getDistinctTransazioni(String table) throws SQLException, EmptySetException {
        List<Example> distinctTransazioni = new ArrayList<>();
        String query = "SELECT DISTINCT * FROM " + table;
        try (Statement stmt = db.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (!rs.next()) {
                throw new EmptySetException ("La tabella è vuota");
            } else {
                do {
                    Example example = new Example();
                    // Aggiungi il codice per estrarre i valori dalle colonne del result set
                    // e aggiungerli all'oggetto Example
                    distinctTransazioni.add(example);
                } while (rs.next());
            }
        }
        return distinctTransazioni;
    }

    public Set<Object> getDistinctColumnValues(String table, Column column) throws SQLException {
        Set<Object> distinctValues = new HashSet<>();
        String query = "SELECT DISTINCT " + column.getColumnName() + " FROM " + table;
        try (Statement stmt = db.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Object value = rs.getObject(column.getColumnName());
                distinctValues.add(value);
            }
        }
        return distinctValues;
    }

    public Object getAggregateColumnValue(String table, Column column, QUERY_TYPE aggregate) throws SQLException, NoValueException {
        Object aggregateValue = null;
        String aggregateFunction = "";

        switch (aggregate) {
            case MIN:
                aggregateFunction = "MIN";
                break;
            case MAX:
                aggregateFunction = "MAX";
                break;
            default:
                throw new IllegalArgumentException("Operatore di aggregazione non supportato: " + aggregate);
        }

        String query = "SELECT " + aggregateFunction + "(" + column.getColumnName() + ") FROM " + table;

        try (Statement stmt = db.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                aggregateValue = rs.getObject(1);

                if (aggregateValue == null) {
                    throw new NoValueException("Il valore aggregato calcolato è pari a null.");
                }
            } else {
                throw new NoValueException("Il resultset è vuoto per la tabella");
            }
        }

        return aggregateValue;
    }

    public enum QUERY_TYPE {
        MIN,
        MAX
    }


}
