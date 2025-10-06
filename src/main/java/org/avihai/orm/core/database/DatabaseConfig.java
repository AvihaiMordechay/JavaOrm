package org.avihai.orm.core.database;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@ToString
public class DatabaseConfig {
    private String dbType;
    @Getter
    private String url;
    @Getter
    private String username;
    @Getter
    private String password;

    public String getDbType() {
        return switch (dbType.toLowerCase()) {
            case "postgresql" -> "org.postgresql.Driver";
            case "mysql" -> "com.mysql.cj.jdbc.Driver";
            case "sqlite" -> "org.sqlite.JDBC";
            default -> throw new IllegalArgumentException("Unsupported database type: " + dbType);
        };
    }
}
