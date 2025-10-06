package org.avihai.orm.core.metadata;

public class ColumnMap {

    public static String mapSqlTypeToJavaType(String columnType, boolean isNullable) {
        return switch (columnType.toLowerCase()) {
            case "int", "integer", "smallint", "tinyint", "mediumint" -> isNullable ? "Integer" : "int";
            case "bigint" -> isNullable ? "Long" : "long";
            case "float", "double", "real" -> isNullable ? "Double" : "double";
            case "decimal", "numeric" -> "java.math.BigDecimal";
            case "bit", "boolean" -> isNullable ? "Boolean" : "boolean";
            case "char", "varchar", "text", "tinytext", "mediumtext", "longtext" -> "String";
            case "uuid" -> "java.util.UUID";
            case "date" -> "java.sql.Date";
            case "time" -> "java.sql.Time";
            case "timestamp", "datetime" -> "java.sql.Timestamp";
            case "blob", "binary", "varbinary", "tinyblob", "mediumblob", "longblob" -> "byte[]";
            case "json", "jsonb" -> "String";
            default -> "Object";
        };
    }

}
