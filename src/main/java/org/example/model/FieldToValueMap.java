package org.example.model;

public class FieldToValueMap {
    private String field;
    private String value;

    public FieldToValueMap(String field, String value) {
        this.field = field;
        this.value = value;
    }

    public String getField() {
        return field;
    }

    public String getValue() {
        return value;
    }
}
