package org.avihai.orm.cli;

import lombok.extern.slf4j.Slf4j;
import org.avihai.orm.core.database.DatabaseConfig;
import org.avihai.orm.core.database.DatabaseFactory;
import org.avihai.orm.core.database.DatabaseReader;
import org.avihai.orm.core.metadata.TableMetadata;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Slf4j
public class Main {
    public static void main(String[] args) throws SQLException {
        DatabaseFactory databaseFactory = new DatabaseFactory();
        try {
            UserInput userInput = UserInput.builder().user("postgres").password("am887887").url("jdbc:postgresql://localhost:5432/eventsManagementDb").build();
            databaseFactory.connect(userInput);

            DatabaseReader databaseReader = new DatabaseReader();
            List<TableMetadata> tableMetadata = databaseReader.readDatabase(databaseFactory.getConnection());

            tableMetadata.forEach(System.out::println);

        }catch (Exception e){
            log.error("error in main", e);
        } finally {
            databaseFactory.closeConnection();

        }





    }
}