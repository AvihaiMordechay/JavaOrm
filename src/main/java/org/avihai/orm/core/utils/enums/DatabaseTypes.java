package org.avihai.orm.core.utils.enums;

public enum DatabaseTypes {
    MY_SQL,
    POSTGRES,
    SQLITE;

    public static DatabaseTypes getDatabaseType(String dbType){
        return switch (dbType) {
            case "postgresql" -> POSTGRES;
            case "mysql" -> MY_SQL;
            case "sqlite" -> SQLITE;
            default -> throw new IllegalArgumentException("Unsupported database type: " + dbType);
        };
    }
    }

