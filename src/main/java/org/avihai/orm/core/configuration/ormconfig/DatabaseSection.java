package org.avihai.orm.core.configuration.ormconfig;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DatabaseSection {
    private String user;
    private String password;
    private String url;
}