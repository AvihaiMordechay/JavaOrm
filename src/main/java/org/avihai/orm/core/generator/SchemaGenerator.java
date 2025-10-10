package org.avihai.orm.core.generator;

import org.avihai.orm.core.metadata.TableMetadata;

import java.util.List;

public class SchemaGenerator extends Generator {
    public SchemaGenerator(List<TableMetadata> tables, boolean withLombok) {
        super(tables, withLombok);
    }

    @Override
    public void generate() {
        // Implementation for schema generation goes here
    }


}
