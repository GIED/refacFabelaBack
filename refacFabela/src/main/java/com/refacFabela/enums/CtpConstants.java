package com.refacFabela.enums;

public enum CtpConstants {
	
	FORMAT("json"),
    ACCKEY("458950503ddcd4e9758309674cb3fc3b7ff834515bbe77757a87d2067c330a46"),
    USERID("WSU51927"),
    PASSWORD("c4758f1346627f22e7f0cf9c5db24f334abf5fef3220ade0d4c666c56e537b93"),
    CUSTOMER("51927"),
    BRANCH("00");

    private final String value;

    CtpConstants(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

}
