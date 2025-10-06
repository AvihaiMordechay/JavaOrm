package org.avihai.orm.cli;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.avihai.orm.core.database.DatabaseConfig;

@Slf4j
@Builder
public class UserInput {
    private String url;
    private String user;
    private String password;

    public DatabaseConfig mapToDatabaseConfig() {
        try {
            String[] urlParts = url.split("://");
            String dbType = urlParts[0].split(":")[1];
            return DatabaseConfig.builder().username(user).password(password).url(url).dbType(dbType).build();
        } catch (Exception e) {
            log.error("error mapping user input to DatabaseConfig", e);
            return null;
        }
    }
}
