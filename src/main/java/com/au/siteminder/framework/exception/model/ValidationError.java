package com.au.siteminder.framework.exception.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

public class ValidationError {

    @Getter
    @Setter
    private String label;

    @Getter
    @Setter
    private ValidationErrorMessage message;

    public ValidationError() {

    }

    public ValidationError(String label, ValidationErrorMessage message) {
        this.label = label;
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValidationError that = (ValidationError) o;
        return Objects.equals(label, that.label) &&
                message == that.message;
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, message);
    }

    @Override
    public String toString() {
        return "ValidationError{" +
                "label='" + label + '\'' +
                ", message=" + message +
                '}';
    }
}
