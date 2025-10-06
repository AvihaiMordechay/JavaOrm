package org.avihai.orm.core.metadata;

import lombok.Builder;
import lombok.ToString;

import java.util.List;

@Builder
@ToString
public class TableMetadata {
    private String tableName;
    private List<ColumnMetadata> columns;
    private List<String> primaryKeys;
    private List<String> foreignKeys;
}
