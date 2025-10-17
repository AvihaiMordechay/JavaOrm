package org.avihai.orm.core.configuration;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;

public abstract class YamlLoader<T> {

    private final String fileName;
    private final Class<T> type;

    protected YamlLoader(String fileName, Class<T> type) {
        this.fileName = fileName;
        this.type = type;
    }

    protected T loadYaml() {
        Yaml yaml = new Yaml();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream == null) {
                throw new RuntimeException(fileName + " not found in resources folder");
            }

            return yaml.loadAs(inputStream, type);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load " + fileName, e);
        }
    }
}
