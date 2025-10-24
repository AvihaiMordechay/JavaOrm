package org.avihai.orm.core.metadata;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class TableMetadata {
    public String name;
    public List<ColumnMetadata> columns;
}
