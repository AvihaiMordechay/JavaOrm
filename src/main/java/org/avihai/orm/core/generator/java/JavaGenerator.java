package org.avihai.orm.core.generator.java;

import org.avihai.orm.core.metadata.TableMetadata;
import java.util.List;

public abstract class JavaGenerator {

    protected List<TableMetadata> tables;
    protected boolean withLombok;


    public abstract void generate();
}
