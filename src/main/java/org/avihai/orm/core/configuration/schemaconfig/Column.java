package org.avihai.orm.core.configuration.schemaconfig;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Column {
    private String name;
    private String type;
    private int length;
    private boolean primaryKey;
    private boolean autoIncrement;
    private boolean unique;
    private boolean nullable;
    private String defaultValue;
    // TODO: Add relationships (e.g., foreign keys)
}
