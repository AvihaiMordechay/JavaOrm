package org.avihai.orm.core.database;

import org.avihai.orm.core.metadata.TablesMetadata;

import java.sql.Connection;
import java.sql.DatabaseMetaData;

public class DatabaseReader {

    public TablesMetadata readDatabase(Connection connection) {
        try {
            if (connection == null || connection.isClosed()) {
                throw new IllegalStateException("Connection is not established or already closed.");
            }
            DatabaseMetaData metaData = connection.getMetaData();
            return new TablesMetadata(metaData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
