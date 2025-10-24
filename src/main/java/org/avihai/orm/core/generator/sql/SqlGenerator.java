package org.avihai.orm.core.generator.sql;

import org.avihai.orm.core.metadata.ColumnMetadata;
import org.avihai.orm.core.metadata.TableMetadata;

public interface SqlGenerator {

    String getCreateTable(TableMetadata table);

    String getDeleteTable(TableMetadata table); // TODO: check if there is id to all databases and if yes replace the tableName to id

    String mapSchemaTypeToSqlType(String schemaType);

    void addAttributes(ColumnMetadata column, StringBuilder sql);
}
