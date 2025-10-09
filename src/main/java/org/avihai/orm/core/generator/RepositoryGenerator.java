package org.avihai.orm.core.generator;


import org.avihai.orm.core.metadata.TableMetadata;

import java.util.List;

public class RepositoryGenerator extends Generator {
    public RepositoryGenerator(List<TableMetadata> tables, boolean withLombok) {
        super(tables, withLombok);
    }
    @Override
    public void generate() {
        // Implementation for generating repository classes
    }

}
