package org.avihai.orm.core.metadata;

import lombok.Builder;

@Builder
public class ColumnMetadata {
    private String columnName;
    private String columnType;
    private String javaType;
    private boolean nullable;
    private int length;
    private String defaultValue;
}
