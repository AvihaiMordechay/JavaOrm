package org.avihai.orm.core.generator.java;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeSpec;
import lombok.extern.slf4j.Slf4j;
import org.avihai.orm.core.metadata.ColumnMetadata;
import org.avihai.orm.core.metadata.TableMetadata;
import org.avihai.orm.core.utils.FileWriterUtil;

import javax.lang.model.element.Modifier;
import java.nio.file.Path;
import java.util.List;

import static org.avihai.orm.core.utils.Constants.*;

@Slf4j
public class EntityGenerator extends JavaGenerator {

    public EntityGenerator(List<TableMetadata> tables, boolean withLombok) {
        super(tables, withLombok);
    }

    @Override
    public void generate() {
        try {
            this.tables.forEach(table -> {
                TypeSpec.Builder entityBuilder;
                if (this.withLombok) {
                    entityBuilder = generateEntityWithLombok(table);
                } else {
                    entityBuilder = generateEntityWithoutLombok(table);
                }
                TypeSpec entity = entityBuilder.build();

                FileWriterUtil.writeToFile(Path.of("src/main/java"), entity, table.getName());
            });
        } catch (Exception e) {
            log.error("error in entity generator", e);
            throw new RuntimeException(e);
        }
    }

    private TypeSpec.Builder generateEntityWithLombok(TableMetadata table) {
        TypeSpec.Builder entityBuilder = TypeSpec.classBuilder(toClassName(table.getName()))
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(ClassName.get(LOMBOK, L_DATA))
                .addAnnotation(ClassName.get(LOMBOK, L_BUILDER))
                .addAnnotation(ClassName.get(LOMBOK, L_NO_ARGS_CON))
                .addAnnotation(ClassName.get(LOMBOK, L_ALL_ARGS_CON));
        addColumnsData(table, entityBuilder);
        return entityBuilder;
    }

    private TypeSpec.Builder generateEntityWithoutLombok(TableMetadata table) {
        TypeSpec.Builder entityBuilder = TypeSpec.classBuilder(toClassName(table.getName()))
                .addModifiers(Modifier.PUBLIC);
        addConstructorNoArgs(entityBuilder);
        addConstructorAllArgs(entityBuilder, table);
        addColumnsData(table, entityBuilder);
        return entityBuilder;
    }

    private void addColumnsData(TableMetadata table, TypeSpec.Builder entityBuilder) {
        StringBuilder toStringBody = new StringBuilder("return \"" + toClassName(table.getName()) + "(\" +\n");
        List<ColumnMetadata> columns = table.getColumns();
        for (int i = 0; i < columns.size(); i++) {
            ColumnMetadata column = columns.get(i);
            addField(entityBuilder, column);
            if (!this.withLombok) {
                addGetterAndSetter(entityBuilder, column);
                addToString(entityBuilder ,column, toStringBody, i, columns.size());
            }
        }
    }

    private void addConstructorNoArgs(TypeSpec.Builder entityBuilder) {
        entityBuilder.addMethod(com.squareup.javapoet.MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .build());
    }

    private void addConstructorAllArgs(TypeSpec.Builder entityBuilder, TableMetadata table) {
        com.squareup.javapoet.MethodSpec.Builder constructorBuilder = com.squareup.javapoet.MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC);
        table.getColumns().forEach(column -> {
            String fieldName = getFieldName(column.getColumnName());
            constructorBuilder.addParameter(column.getJavaType(), fieldName);
            constructorBuilder.addStatement("this.$N = $N", fieldName, fieldName);
        });
        entityBuilder.addMethod(constructorBuilder.build());
    }

    private void addToString(TypeSpec.Builder entityBuilder,ColumnMetadata column, StringBuilder toStringBody, int i, int size) {
        String fieldName = getFieldName(column.getColumnName());
        toStringBody.append("                \"").append(fieldName).append("='\" + ").append(fieldName).append(" + '\\''");
        if (i < size - 1) {
            toStringBody.append(" + \", \" +\n");
        } else {
            toStringBody.append(" +\n                ')'");

            entityBuilder.addMethod(com.squareup.javapoet.MethodSpec.methodBuilder("toString")
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.PUBLIC)
                    .returns(String.class)
                    .addStatement(toStringBody.toString())
                    .build());
        }
    }

    private void addGetterAndSetter(TypeSpec.Builder entityBuilder, ColumnMetadata column) {
        String fieldName = getFieldName(column.getColumnName());
        String methodSuffix = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);

        // Getter
        entityBuilder.addMethod(com.squareup.javapoet.MethodSpec.methodBuilder("get" + methodSuffix)
                .addModifiers(Modifier.PUBLIC)
                .returns(column.getJavaType())
                .addStatement("return this.$N", fieldName)
                .build());

        // Setter
        entityBuilder.addMethod(com.squareup.javapoet.MethodSpec.methodBuilder("set" + methodSuffix)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(column.getJavaType(), fieldName)
                .addStatement("this.$N = $N", fieldName, fieldName)
                .build());
    }

    private void addField(TypeSpec.Builder entityBuilder, ColumnMetadata column) {
        String fieldName = getFieldName(column.getColumnName());
        FieldSpec field = FieldSpec.builder(column.getJavaType(), fieldName, Modifier.PRIVATE).build();
        entityBuilder.addField(field);
    }

    private String getFieldName(String str) {
        String caseType = getCaseType(str);
        return switch (caseType) {
            case CAMEL_CASE -> str;
            case SNAKE_CASE -> convertSnakeToCamel(str);
            case PASCAL_CASE -> convertPascalToCamel(str);
            case UPPER_SNAKE_CASE -> convertSnakeToCamel(str.toLowerCase());
            default -> str; // TODO: handle unknown case appropriately
        };
    }

    private String toClassName(String str) {
        String caseType = getCaseType(str);
        return switch (caseType) {
            case CAMEL_CASE -> convertCamelToPascal(str);
            case SNAKE_CASE -> ConvertSnakeToPascal(str);
            case PASCAL_CASE -> str;
            case UPPER_SNAKE_CASE -> ConvertSnakeToPascal(str.toLowerCase());
            default -> str; // TODO: handle unknown case appropriately
        };
    }

    private String convertPascalToCamel(String str) {
        return Character.toLowerCase(str.charAt(0)) + str.substring(1);
    }

    private String convertSnakeToCamel(String str) {
        String[] parts = str.split("_");
        StringBuilder camelCaseString = new StringBuilder(parts[0].toLowerCase());
        for (int i = 1; i < parts.length; i++) {
            camelCaseString.append(parts[i].substring(0, 1).toUpperCase()).append(parts[i].substring(1).toLowerCase());
        }
        return camelCaseString.toString();
    }

    private String convertCamelToPascal(String str) {
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    private String ConvertSnakeToPascal(String str) {
        String[] parts = str.split("_");
        StringBuilder pascalCaseString = new StringBuilder();
        for (String part : parts) {
            pascalCaseString.append(part.substring(0, 1).toUpperCase()).append(part.substring(1).toLowerCase());
        }
        return pascalCaseString.toString();
    }

    private String getCaseType(String name) {
        if (isCamelCase(name)) {
            return CAMEL_CASE;
        } else if (isSnakeCase(name)) {
            return SNAKE_CASE;
        } else if (isPascalCase(name)) {
            return PASCAL_CASE;
        } else if (isUpperSnakeCase(name)) {
            return UPPER_SNAKE_CASE;
        } else {
            return UNKNOWN;
        }
    }

    private boolean isCamelCase(String name) {
        return name.matches("^[a-z]+([A-Z][a-z0-9]+)*$");
    }

    private boolean isUpperSnakeCase(String name) {
        return name.matches("^[A-Z]+(_[A-Z0-9]+)*$");
    }

    private boolean isSnakeCase(String name) {
        return name.matches("^[a-z]+(_[a-z0-9]+)*$");
    }

    private boolean isPascalCase(String name) {
        return name.matches("^[A-Z][a-z0-9]+([A-Z][a-z0-9]+)*$");
    }
}
