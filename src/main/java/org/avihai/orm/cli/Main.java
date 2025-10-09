package org.avihai.orm.cli;

import lombok.extern.slf4j.Slf4j;
import org.avihai.generated.entities.Guest;
import org.avihai.orm.core.database.DatabaseConfig;
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
            UserInput userInput = UserInput.builder().user("postgres").password("am887887").url("jdbc:postgresql://localhost:5432/eventsManagementDb").build();
            databaseFactory.connect(userInput);

            DatabaseReader databaseReader = new DatabaseReader();
            List<TableMetadata> tableMetadata = databaseReader.readDatabase(databaseFactory.getConnection());

            EntityGenerator entityGenerator = new EntityGenerator(tableMetadata, false);
            entityGenerator.generate();



        }catch (Exception e){
            log.error("error in main", e);
        } finally {
            databaseFactory.closeConnection();

        }





    }
}