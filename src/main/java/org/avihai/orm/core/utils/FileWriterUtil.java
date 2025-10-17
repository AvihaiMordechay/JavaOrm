package org.avihai.orm.core.utils;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.nio.file.Path;

public class FileWriterUtil {

    public static void writeToFile(Path filePath, TypeSpec entityClass, String tableName) {
        try {
            JavaFile javaFile = JavaFile.builder("org.avihai.generated.entities", entityClass)
                    .build();

            javaFile.writeTo(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Error writing file for " + tableName, e);
        }
    }
}
