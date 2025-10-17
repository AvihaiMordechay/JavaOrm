package org.avihai.orm.core.database;

import lombok.Builder;
import org.avihai.orm.core.metadata.TableMetadata;
import org.avihai.orm.core.metadata.TablesMetadata;

import java.util.List;

@Builder
public class DatabaseSchemaCreator {

    private TablesMetadata tablesMetadata;

    public void createTables() {
        for (TableMetadata tableMetadata: tablesMetadata.getTables()){

        }
    }


}
