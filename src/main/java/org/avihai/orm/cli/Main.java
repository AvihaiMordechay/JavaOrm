package org.avihai.orm.cli;

import lombok.extern.slf4j.Slf4j;
import org.avihai.orm.core.configuration.ormconfig.ConfigLoader;
import org.avihai.orm.core.configuration.schemaconfig.SchemaLoader;
import org.avihai.orm.core.configuration.schemaconfig.Table;
import org.avihai.orm.core.database.DatabaseFactory;
import org.avihai.orm.core.metadata.TablesMetadata;
import org.avihai.orm.core.utils.enums.SqlCommand;

import java.util.List;

@Slf4j
public class Main {
    public static void main(String[] args) {
        DatabaseFactory dbFactory = DatabaseFactory.getInstance();
        try {
            ConfigLoader configLoader = new ConfigLoader();
            dbFactory.connect(configLoader.getDatabaseConfig());

            SchemaLoader schemaLoader = new SchemaLoader();

            TablesMetadata tablesMetadata = new TablesMetadata(schemaLoader.getTables());
//            dbFactory.generate(SqlCommand.CREATE_TABLES, tablesMetadata);
        } catch (Exception e) {
            log.error("error in main", e);
        } finally {
            dbFactory.closeConnection();

        }


    }
}