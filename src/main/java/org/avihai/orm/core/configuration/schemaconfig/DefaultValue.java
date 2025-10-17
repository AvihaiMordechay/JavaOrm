package org.avihai.orm.core.configuration.schemaconfig;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DefaultValue {
    private Object value;
    private Class<?> type;

    public DefaultValue(String value, Class<?> type) {
        this.type = type;
        this.value = getValue(value);
    }

    private Object getValue(String value) {
        if (this.type == String.class) {
            return value;
        } else if (this.type == Integer.class || this.type == int.class) {
            return Integer.parseInt(value);
        } else if (this.type == Long.class || this.type == long.class) {
            return Long.parseLong(value);
        } else if (this.type == Boolean.class || this.type == boolean.class) {
            return Boolean.parseBoolean(value);
        } else if (this.type == Double.class || this.type == double.class) {
            return Double.parseDouble(value);
        } else if (this.type == Float.class || this.type == float.class) {
            return Float.parseFloat(value);
        } else if (this.type == Short.class || this.type == short.class) {
            return Short.parseShort(value);
        } else if (this.type == Byte.class || this.type == byte.class) {
            return Byte.parseByte(value);
        } else if (this.type == Character.class ||  this.type == char.class) {
            return value.charAt(0);
        } else {
            throw new IllegalArgumentException("Unsupported type: " + type);
        }
    }
}
