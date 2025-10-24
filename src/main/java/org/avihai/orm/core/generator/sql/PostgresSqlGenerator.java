package org.avihai.orm.core.generator.sql;

import org.avihai.orm.core.metadata.ColumnMetadata;
import org.avihai.orm.core.metadata.TableMetadata;

import static org.avihai.orm.core.utils.Constants.*;

public class PostgresSqlGenerator implements SqlGenerator {

    @Override
    public String getCreateTable(TableMetadata table) {
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE IF NOT EXISTS ")
                .append(table.getName())
                .append(" (");

        for (int i = 0; i < table.getColumns().size(); i++) {
            ColumnMetadata column = table.getColumns().get(i);

            sql.append(column.getColumnName()).append(" ");

            addAttributes(column, sql);

            if (i < table.getColumns().size() - 1) {
                sql.append(", ");
            }
        }

        sql.append(");");
        return sql.toString();
    }

    @Override
    public String getDeleteTable(TableMetadata table) {
        return "DROP TABLE IF EXISTS " + table.getName() + ";";
    }

    @Override
    public String mapSchemaTypeToSqlType(String schemaType) {
        return switch (schemaType) {
            case S_TEXT -> "VARCHAR";
            case S_INT -> "INTEGER";
            case S_BOOLEAN -> "BOOLEAN";
            default -> throw new IllegalArgumentException("Unsupported schema type: " + schemaType);
        };
    }

    @Override
    public void addAttributes(ColumnMetadata column, StringBuilder sql) {
        if (column.isAutoIncrement()) {
            sql.append("SERIAL");
        } else {
            sql.append(column.getSqlType());
            if (column.getLength() > 0) {
                sql.append("(").append(column.getLength()).append(")");
            }
        }

        if (!column.isNullable()) {
            sql.append(" NOT NULL");
        }

        if (column.isUnique()) {
            sql.append(" UNIQUE");
        }

        if (column.getDefaultValue() != null) {
            Object def = column.getDefaultValue().getValue();
            if (def instanceof String) {
                sql.append(" DEFAULT '").append(def).append("'");
            } else {
                sql.append(" DEFAULT ").append(def);
            }
        }

        if (column.isPrimaryKey()) {
            sql.append(" PRIMARY KEY");
        }

    }
}
