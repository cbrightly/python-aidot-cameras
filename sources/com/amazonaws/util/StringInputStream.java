package com.amazonaws.util;

import java.io.ByteArrayInputStream;

public class StringInputStream extends ByteArrayInputStream {
    private final String string;

    public StringInputStream(String s) {
        super(s.getBytes(StringUtils.UTF8));
        this.string = s;
    }

    public String getString() {
        return this.string;
    }
}
