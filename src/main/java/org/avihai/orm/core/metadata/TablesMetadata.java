package org.avihai.orm.core.metadata;

import lombok.Getter;
import org.avihai.orm.core.configuration.schemaconfig.Column;
import org.avihai.orm.core.configuration.schemaconfig.DefaultValue;
import org.avihai.orm.core.configuration.schemaconfig.Table;
import org.avihai.orm.core.database.DatabaseFactory;
import org.avihai.orm.core.utils.ColumnMap;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Getter
public class TablesMetadata {
        private final List<TableMetadata> tables;

        public TablesMetadata(DatabaseMetaData metaData) throws SQLException {
            List<TableMetadata> result = new java.util.ArrayList<>();
            ResultSet metaDataTables = metaData.getTables(null, null, "%", new String[]{"TABLE"});
            while (metaDataTables.next()) {
                String tableName = metaDataTables.getString("TABLE_NAME");
                result.add(TableMetadata.builder()
                        .name(tableName)
                        .columns(getColumns(metaData, tableName))
                        .build());
            }
            this.tables = result;
        }

        public TablesMetadata(List<Table> schemaTables) {
            List<TableMetadata> result = new ArrayList<>();
            for (Table table : schemaTables) {
                result.add(TableMetadata.builder()
                        .name(table.getName()) // TODO: map table.getName to SQL format
                        .columns(getColumns(table))
                        .build());
            }
            this.tables = result;
        }

        // For schema config
        private List<ColumnMetadata> getColumns(Table table) {
            List<ColumnMetadata> result = new ArrayList<>();
            DatabaseFactory dbInstance = DatabaseFactory.getInstance();
            for (Column column : table.getColumns()) {
                Class<?> javaType = ColumnMap.mapSqlTypeToJavaType(column.getType(), column.isNullable());

                DefaultValue defaultValue;
                if (column.getDefaultValue() != null) {
                    defaultValue = new DefaultValue(column.getDefaultValue(), javaType);
                } else {
                    defaultValue = null;
                }
                ColumnMetadata columnMetadata = new ColumnMetadata(
                        column.getName(), dbInstance.getSqlType(column.getType()),
                        javaType, column.getLength(), column.isNullable(),
                        column.isPrimaryKey(), column.isAutoIncrement(),
                        column.isUnique(), defaultValue);

                result.add(columnMetadata);
            }
            return result.isEmpty() ? null : result;
        }

        // For database metadata
        private List<ColumnMetadata> getColumns(DatabaseMetaData metaData, String tableName) throws SQLException {
            List<ColumnMetadata> result = new java.util.ArrayList<>();
            ResultSet columns = metaData.getColumns(null, null, tableName, null);
            while (columns.next()) {
                String columnName = columns.getString("COLUMN_NAME");
                String columnType = columns.getString("TYPE_NAME");
                int columnSize = columns.getInt("COLUMN_SIZE");
                boolean isNullable = columns.getString("IS_NULLABLE").equals("YES");
                boolean isAutoIncrement = columns.getString("IS_AUTOINCREMENT").equals("YES");
                boolean isUnique = isColumnUnique(metaData, tableName, columnName);
                boolean isPrimaryKey = false;
                List<String> primaryKeys = getPrimaryKeys(metaData, tableName);
                if (primaryKeys != null && primaryKeys.contains(columnName)) {
                    isPrimaryKey = true;
                }
                Class<?> javaType = ColumnMap.mapSqlTypeToJavaType(columnType, isNullable);
                String columnDef = columns.getString("COLUMN_DEF");
                DefaultValue defaultValue = columnDef != null ? new DefaultValue(columnDef, javaType) : null;

                ColumnMetadata columnMetadata = new ColumnMetadata(columnName, columnType, javaType,
                        columnSize, isNullable, isPrimaryKey,
                        isAutoIncrement, isUnique, defaultValue);

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

    private boolean isColumnUnique(DatabaseMetaData metaData, String tableName, String columnName) throws SQLException {
        ResultSet indexInfo = metaData.getIndexInfo(null, null, tableName, true, false);
        while (indexInfo.next()) {
            String idxColumn = indexInfo.getString("COLUMN_NAME");
            boolean nonUnique = indexInfo.getBoolean("NON_UNIQUE"); // false = unique index
            if (idxColumn != null && idxColumn.equalsIgnoreCase(columnName) && !nonUnique) {
                return true;
            }
        }
        return false;
    }
}
