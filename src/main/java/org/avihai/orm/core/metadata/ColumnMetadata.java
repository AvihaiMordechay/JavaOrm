package org.avihai.orm.core.metadata;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ColumnMetadata {
    private String columnName;
    private String columnType;
    private Class<?> javaType;
    private boolean nullable;
    private int length;
    private String defaultValue;
}
