package org.avihai.orm.core.metadata;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.avihai.orm.core.configuration.schemaconfig.DefaultValue;

@Getter
@AllArgsConstructor
public class ColumnMetadata {
    private String columnName;
    private String sqlType;
    private Class<?> javaType;
    private int length;
    private boolean isNullable;
    private boolean isPrimaryKey;
    private boolean isAutoIncrement;
    private boolean isUnique;
    private DefaultValue defaultValue;
}
