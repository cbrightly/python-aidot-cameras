package com.caverock.androidsvg;

public class CSSParseException extends Exception {
    CSSParseException(String msg) {
        super(msg);
    }

    CSSParseException(String msg, Exception cause) {
        super(msg, cause);
    }
}
