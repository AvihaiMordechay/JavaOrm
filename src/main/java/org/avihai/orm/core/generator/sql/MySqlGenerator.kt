package org.avihai.orm.core.generator.sql

import org.avihai.orm.core.metadata.ColumnMetadata
import org.avihai.orm.core.metadata.TableMetadata
import org.avihai.orm.core.utils.Constants.*

class MySqlGenerator : SqlGenerator {

    override fun getCreateTable(table: TableMetadata): String {
        val sql = StringBuilder("CREATE TABLE `${table.name}` (\n")
        var primaryKeyClause = ""

        for (column in table.columns) {
            sql.append("`${column.columnName}` ${column.sqlType}")
            if (column.length > 0){
                sql.append("(${column.length})")
            }

            addAttributes(column, sql)

            if (column.isPrimaryKey) {
                if (primaryKeyClause.isEmpty()) {
                    primaryKeyClause = "  PRIMARY KEY (`${column.columnName}`"
                } else {
                    primaryKeyClause += ", `${column.columnName}`"
                }
            }
        }

        if (primaryKeyClause.isNotEmpty()) {
            primaryKeyClause += ")"
            sql.append(primaryKeyClause).append("\n")
        } else {
            sql.trimEnd(',', '\n')
            sql.append("\n")
        }

        sql.append(");")
        return sql.toString()
    }

    override fun getDeleteTable(table: TableMetadata): String {
        return "DROP TABLE IF EXISTS `${table.name}`;"
    }

    override fun mapSchemaTypeToSqlType(schemaType: String): String {
        return when (schemaType) {
            S_TEXT -> "varchar"
            S_INT -> "int"
            S_BOOLEAN -> "BOOLEAN"
            else -> throw IllegalArgumentException("Unsupported schema type: $schemaType")
        }
    }

    override fun addAttributes(column: ColumnMetadata, sql: StringBuilder) {
        if (!column.isNullable) sql.append(" NOT NULL")
        if (column.isAutoIncrement) sql.append(" AUTO_INCREMENT")
        if (column.isUnique) sql.append(" UNIQUE")
        if (column.defaultValue != null) sql.append(" DEFAULT ${column.defaultValue.value}")

        sql.append(",\n")
    }

}
