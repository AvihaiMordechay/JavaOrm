package org.avihai.orm.core.metadata;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.avihai.orm.core.configuration.schemaconfig.DefaultValue;

@Getter
@AllArgsConstructor
public class ColumnMetadata {
    public String columnName;
    public String sqlType;
    public Class<?> javaType;
    public int length;
    public boolean isNullable;
    public boolean isPrimaryKey;
    public boolean isAutoIncrement;
    public boolean isUnique;
    public DefaultValue defaultValue;
}
