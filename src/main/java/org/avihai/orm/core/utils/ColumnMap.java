package org.avihai.orm.core.utils;

import org.avihai.orm.core.database.DatabaseFactory;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.UUID;

import static org.avihai.orm.core.utils.Constants.*;

public class ColumnMap {

    public static Class<?> mapSqlTypeToJavaType(String columnType, boolean isNullable) {
        return switch (columnType.toLowerCase()) {
            case "int", "int4", "integer", "tinyint", "mediumint" -> isNullable ? Integer.class : int.class;
            case "serial", "smallserial" -> isNullable ? Integer.class : int.class;
            case "int2", "smallint" -> isNullable ? Short.class : short.class;
            case "int8", "bigint" -> isNullable ? Long.class : long.class;
            case "float", "double", "real" -> isNullable ? Double.class : double.class;
            case "decimal", "numeric" -> BigDecimal.class;
            case "bit", "boolean" -> isNullable ? Boolean.class : boolean.class;
            case "char", "varchar", "text", "tinytext", "mediumtext", "longtext" -> String.class;
            case "uuid" -> UUID.class;
            case "date" -> Date.class;
            case "time" -> Time.class;
            case "timestamp", "datetime" -> Timestamp.class;
            case "blob", "binary", "varbinary", "tinyblob", "mediumblob", "longblob" -> byte[].class;
            case "json", "jsonb" -> String.class;
            default -> Object.class;
        };
    }

}
