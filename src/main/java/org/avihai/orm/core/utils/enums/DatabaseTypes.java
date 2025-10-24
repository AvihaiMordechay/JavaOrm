package org.avihai.orm.core.utils.enums;

public enum DatabaseTypes {
    MYSQL,
    POSTGRES,
    SQLITE;

    public static DatabaseTypes getDatabaseType(String dbType){
        return switch (dbType) {
            case "postgresql" -> POSTGRES;
            case "mysql" -> MYSQL;
            case "sqlite" -> SQLITE;
            default -> throw new IllegalArgumentException("Unsupported database type: " + dbType);
        };
    }
    }

