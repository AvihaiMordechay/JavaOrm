package org.avihai.orm.core.generator;

import lombok.AllArgsConstructor;
import org.avihai.orm.core.metadata.TableMetadata;
import java.util.List;

@AllArgsConstructor
public abstract class Generator {

    protected List<TableMetadata> tables;
    protected boolean withLombok;


    public abstract void generate();
}
