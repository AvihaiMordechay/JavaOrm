package org.avihai.orm.core.database;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

@Slf4j
public class DatabaseFactory {
    @Getter
    private Connection connection;
    public void connect(DatabaseConfig databaseConfig) {
        try {
            Class.forName(databaseConfig.getDriverName());
            this.connection = DriverManager.getConnection(databaseConfig.getUrl(), databaseConfig.getUsername(), databaseConfig.getPassword());
            log.info("Connected to database successfully!");
        } catch (Exception e) {
            log.error("error connecting to database", e);
            throw new RuntimeException(e);
        }
    }

    public Statement createStatement() {
        try {
            if (this.connection == null || this.connection.isClosed()) {
                throw new IllegalStateException("Connection is not established or already closed.");
            }
            return this.connection.createStatement();
        } catch (Exception e) {
            log.error("error creating statement", e);
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
            log.error("error closing statement", e);
        }
    }

    public void closeConnection() {
        try {
            if (this.connection != null && !this.connection.isClosed()) {
                this.connection.close();
                log.info("Database connection closed successfully!");
            }
        } catch (Exception e) {
            log.error("error closing database connection", e);
        }
    }
}
