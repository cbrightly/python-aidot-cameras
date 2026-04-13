package com.clj.fastble.exception;

/* compiled from: TimeoutException */
public class e extends a {
    private String ref = "";

    public e(String reasonRef) {
        super(100, "Timeout Exception Occurred! " + reasonRef);
    }
}
