package org.avihai.orm.cli.configuration;

import org.avihai.orm.core.database.DatabaseConfig;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;

public class ConfigLoader {
    private final OrmConfig config;

    public ConfigLoader() {
        this.config = loadYamlConfig();
    }

    private OrmConfig loadYamlConfig() {
        Yaml yaml = new Yaml();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("ormconfig.yaml")) {
            if (inputStream == null) {
                throw new RuntimeException("ormconfig.yaml not found in resources folder");
            }

            return yaml.loadAs(inputStream, OrmConfig.class);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load ormconfig.yaml", e);
        }
    }

    public DatabaseConfig toDatabaseConfig() {
        var db = config.getDatabase();
        return new DatabaseConfig(db.getUser(), db.getPassword(), db.getUrl());
    }

    public boolean isWithLombok() {
        return config.getCodegen().isWithLombok();
    }
}
