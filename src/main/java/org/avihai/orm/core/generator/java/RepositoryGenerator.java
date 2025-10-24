package org.avihai.orm.core.generator.java;


import org.avihai.orm.core.metadata.TableMetadata;

import java.util.List;

public class RepositoryGenerator extends JavaGenerator {
    public RepositoryGenerator(List<TableMetadata> tables, boolean withLombok) {
        this.tables = tables;
        this.withLombok = withLombok;
    }
    @Override
    public void generate() {
        // Implementation for generating repository classes
    }

}
