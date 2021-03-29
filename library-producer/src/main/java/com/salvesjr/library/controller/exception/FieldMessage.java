package com.salvesjr.library.controller.exception;

import java.io.Serializable;

public class FieldMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    private String fieldName;
    private String fieldMessage;

    public FieldMessage() {
    }

    public FieldMessage(String name, String message) {
        this.fieldName = name;
        this.fieldMessage = message;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fildName) {
        this.fieldName = fildName;
    }

    public String getFildMessage() {
        return fieldMessage;
    }

    public void setFildMessage(String fildMessage) {
        this.fieldMessage = fildMessage;
    }
}
