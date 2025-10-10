package org.avihai.orm.cli;

import lombok.extern.slf4j.Slf4j;
import org.avihai.orm.cli.configuration.ConfigLoader;
import org.avihai.orm.core.database.DatabaseFactory;
import org.avihai.orm.core.database.DatabaseReader;
import org.avihai.orm.core.generator.EntityGenerator;
import org.avihai.orm.core.metadata.TableMetadata;

import java.util.List;

@Slf4j
public class Main {
    public static void main(String[] args) {
        DatabaseFactory databaseFactory = new DatabaseFactory();
        try {
            ConfigLoader configLoader = new ConfigLoader();
            databaseFactory.connect(configLoader.toDatabaseConfig());

            DatabaseReader databaseReader = new DatabaseReader();
            List<TableMetadata> tableMetadata = databaseReader.readDatabase(databaseFactory.getConnection());

            EntityGenerator entityGenerator = new EntityGenerator(tableMetadata, configLoader.isWithLombok());
            entityGenerator.generate();
        }catch (Exception e){
            log.error("error in main", e);
        } finally {
            databaseFactory.closeConnection();

        }





    }
}