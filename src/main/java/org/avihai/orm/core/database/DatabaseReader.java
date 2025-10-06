package org.avihai.orm.core.database;

import org.avihai.orm.core.metadata.ColumnMap;
import org.avihai.orm.core.metadata.ColumnMetadata;
import org.avihai.orm.core.metadata.TableMetadata;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DatabaseReader {

    public List<TableMetadata> readDatabase(Connection connection) {
        try {
            if (connection == null || connection.isClosed()) {
                throw new IllegalStateException("Connection is not established or already closed.");
            }
            DatabaseMetaData metaData = connection.getMetaData();
            return getTables(metaData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private List<TableMetadata> getTables(DatabaseMetaData metaData) throws SQLException {
        List<TableMetadata> result = new java.util.ArrayList<>();
        ResultSet tables = metaData.getTables(null, null, "%", new String[]{"TABLE"});
        while (tables.next()) {
            String tableName = tables.getString("TABLE_NAME");
            result.add(TableMetadata.builder()
                    .tableName(tableName)
                    .columns(getColumns(metaData, tableName))
                    .primaryKeys(getPrimaryKeys(metaData, tableName))
                    .foreignKeys(getForeignKeys(metaData, tableName))
                    .build());
        }
        return result;
    }

    private List<ColumnMetadata> getColumns(DatabaseMetaData metaData, String tableName) throws SQLException {
        List<ColumnMetadata> result = new java.util.ArrayList<>();
        ResultSet columns = metaData.getColumns(null, null, tableName, null);
        while (columns.next()) {
            String columnName = columns.getString("COLUMN_NAME");
            String columnType = columns.getString("TYPE_NAME");
            int columnSize = columns.getInt("COLUMN_SIZE");
            boolean isNullable = columns.getString("IS_NULLABLE").equals("YES");
            String javaType = ColumnMap.mapSqlTypeToJavaType(columnType, isNullable);
            String columnDef = columns.getString("COLUMN_DEF");

            ColumnMetadata columnMetadata = ColumnMetadata.builder()
                    .columnName(columnName)
                    .columnType(columnType)
                    .length(columnSize)
                    .nullable(isNullable)
                    .defaultValue(columnDef)
                    .javaType(javaType)
                    .build();
            result.add(columnMetadata);
        }
        return result.isEmpty() ? null : result;
    }

    private List<String> getPrimaryKeys(DatabaseMetaData metaData, String tableName) throws SQLException {
        List<String> result = new java.util.ArrayList<>();
        ResultSet primaryKeys = metaData.getPrimaryKeys(null, null, tableName);
        while (primaryKeys.next()) {
            String columnName = primaryKeys.getString("COLUMN_NAME");
            result.add(columnName);
        }
        return result.isEmpty() ? null : result;
    }

    private List<String> getForeignKeys(DatabaseMetaData metaData, String tableName) throws SQLException {
        List<String> result = new java.util.ArrayList<>();
        ResultSet foreignKeys = metaData.getImportedKeys(null, null, tableName);
        while (foreignKeys.next()) {
            String columnName = foreignKeys.getString("FKCOLUMN_NAME");
            result.add(columnName);
        }
        return result.isEmpty() ? null : result;
    }
}
