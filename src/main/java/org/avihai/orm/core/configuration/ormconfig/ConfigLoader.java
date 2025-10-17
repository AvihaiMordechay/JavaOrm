package org.avihai.orm.core.configuration.ormconfig;

import org.avihai.orm.core.configuration.YamlLoader;
import org.avihai.orm.core.database.DatabaseConfig;

public class ConfigLoader extends YamlLoader<OrmConfig> {

    private final OrmConfig config;

    public ConfigLoader() {
        super("ormconfig.yaml", OrmConfig.class);
        this.config = loadYaml();
    }

    public DatabaseConfig getDatabaseConfig() {
        var db = config.getDatabase();
        return new DatabaseConfig(db.getUser(), db.getPassword(), db.getUrl());
    }

    public boolean isWithLombok() {
        return config.getOptions().isWithLombok();
    }
}
