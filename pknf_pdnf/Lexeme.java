package com.company.Numeric_Expressions;

public class Lexeme {
    String value;
    Type type;

    public Lexeme(String value, Type type) {
        this.value = value;
        this.type = type;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
