package org.avihai.orm.core.database;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@ToString
@Slf4j
public class DatabaseConfig {
    @Getter
    private String driverName;
    @Getter
    private String url;
    @Getter
    private String username;
    @Getter
    private String password;

    public DatabaseConfig(String user, String password, String url) {
        try {
            this.username = user;
            this.password = password;
            this.url = url;
            String[] urlParts = url.split("://");
            String dbType = urlParts[0].split(":")[1];
            this.driverName = getDriverName(dbType.toLowerCase());
        } catch (Exception e) {
            log.error("error mapping user input to DatabaseConfig", e);
        }
    }

    public String getDriverName(String dbType) {
        return switch (dbType) {
            case "postgresql" -> "org.postgresql.Driver";
            case "mysql" -> "com.mysql.cj.jdbc.Driver";
            case "sqlite" -> "org.sqlite.JDBC";
            default -> throw new IllegalArgumentException("Unsupported database type: " + dbType);
        };
    }

}
