package com.clj.fastble.exception;

import java.io.Serializable;

/* compiled from: BleException */
public abstract class a implements Serializable {
    public static final int ERROR_CODE_GATT = 101;
    public static final int ERROR_CODE_OTHER = 102;
    public static final int ERROR_CODE_TIMEOUT = 100;
    private static final long serialVersionUID = 8004414918500865564L;
    private int code;
    private String description;

    public a(int code2, String description2) {
        this.code = code2;
        this.description = description2;
    }

    public int getCode() {
        return this.code;
    }

    public a setCode(int code2) {
        this.code = code2;
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public a setDescription(String description2) {
        this.description = description2;
        return this;
    }

    public String toString() {
        return "BleException { code=" + this.code + ", description='" + this.description + '\'' + '}';
    }
}
