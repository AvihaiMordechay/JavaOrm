package org.avihai.orm.cli.configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrmConfig {
    private DatabaseSection database;
    private CodegenSection codegen;
}
