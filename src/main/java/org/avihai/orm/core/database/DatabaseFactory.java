package org.avihai.orm.core.database;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.avihai.orm.core.generator.sql.MySqlGenerator;
import org.avihai.orm.core.generator.sql.PostgresSqlGenerator;
import org.avihai.orm.core.generator.sql.SqlGenerator;
import org.avihai.orm.core.metadata.TableMetadata;
import org.avihai.orm.core.metadata.TablesMetadata;
import org.avihai.orm.core.utils.enums.SqlCommand;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Slf4j
public class DatabaseFactory {

    // Singleton Instance
    private static DatabaseFactory instance;
    private Connection connection;
    private SqlGenerator sqlGenerator;

    @Getter
    private static boolean isInitialized = false;
    private DatabaseFactory() {}

    public static synchronized DatabaseFactory getInstance() {
        if (instance == null) {
            instance = new DatabaseFactory();
            isInitialized = true;
        }
        return instance;
    }

    public void connect(DatabaseConfig databaseConfig) {
        try {
            Class.forName(databaseConfig.getDriverName());
            this.connection = DriverManager.getConnection(
                    databaseConfig.getUrl(),
                    databaseConfig.getUsername(),
                    databaseConfig.getPassword()
            );
            switch (databaseConfig.getDbType()){
                case POSTGRES -> this.sqlGenerator = new PostgresSqlGenerator();
                case MYSQL -> this.sqlGenerator = new MySqlGenerator();
                default -> throw new IllegalArgumentException("Unsupported database type: " + databaseConfig.getDbType());
            }
            log.info("Connected to database successfully!");
        } catch (Exception e) {
            log.error("Error connecting to database", e);
            throw new RuntimeException(e);
        }
    }

    public void closeStatement(Statement statement) {
        try {
            if (statement != null && !statement.isClosed()) {
                statement.close();
                log.info("Statement closed successfully!");
            }
        } catch (Exception e) {
            log.error("Error closing statement", e);
        }
    }

    public void closeConnection() {
        try {
            if (isConnected()) {
                this.connection.close();
                log.info("Database connection closed successfully!");
            }
        } catch (Exception e) {
            log.error("Error closing database connection", e);
        }
    }

    public boolean isConnected() {
        try {
            return this.connection != null && !this.connection.isClosed();
        } catch (SQLException e) {
            log.error("Error checking connection status", e);
            return false;
        }
    }

    public Statement createStatement() {
        try {
            if (this.connection == null || this.connection.isClosed()) {
                throw new IllegalStateException("Connection is not established or already closed.");
            }
            return this.connection.createStatement();
        } catch (Exception e) {
            log.error("Error creating statement", e);
            throw new RuntimeException(e);
        }
    }

    public void generate(SqlCommand command, TablesMetadata tablesMetadata){
        switch (command){
            case CREATE_TABLES -> createTables(tablesMetadata.getTables());

        }
    }

    public void execute(String sql){
        try (Statement stmt = createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            log.error("Failed execute command: '{}'", sql);
            throw new RuntimeException(e);
        }
    }

    public void createTables(List<TableMetadata> tables){
        for (TableMetadata table : tables) {
            String sql = this.sqlGenerator.getCreateTable(table);
            execute(sql);
        }
    }

    public String getSqlType(String schemaType){
        return this.sqlGenerator.mapSchemaTypeToSqlType(schemaType);
    }

}
