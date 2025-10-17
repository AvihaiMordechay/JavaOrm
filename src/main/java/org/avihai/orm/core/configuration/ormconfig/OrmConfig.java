package org.avihai.orm.core.configuration.ormconfig;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrmConfig {
    private DatabaseSection database;
    private OptionsSection options;
}
