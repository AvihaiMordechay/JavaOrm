package org.avihai.orm.core.configuration.schemaconfig;

import lombok.Builder;
import org.avihai.orm.core.configuration.YamlLoader;

import java.util.List;

public class SchemaLoader extends YamlLoader<Schema> {

    private final Schema schema;

    public SchemaLoader() {
        super("schema.yaml", Schema.class);
        this.schema = loadYaml();
    }

    public List<Table> getTables() {
        return schema.getTables();
    }
}
