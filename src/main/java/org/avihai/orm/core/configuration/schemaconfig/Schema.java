package org.avihai.orm.core.configuration.schemaconfig;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Schema {
    private List<Table> tables;
}
