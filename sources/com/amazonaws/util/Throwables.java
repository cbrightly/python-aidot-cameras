package com.amazonaws.util;

import com.amazonaws.logging.Log;
import com.amazonaws.logging.LogFactory;

public enum Throwables {
    ;
    
    private static final int MAX_RETRY = 1000;

    public static Throwable getRootCause(Throwable orig) {
        if (orig == null) {
            return orig;
        }
        Throwable t = orig;
        for (int i = 0; i < 1000; i++) {
            Throwable cause = t.getCause();
            if (cause == null) {
                return t;
            }
            t = cause;
        }
        Log log = LogFactory.getLog(Throwables.class);
        log.debug("Possible circular reference detected on " + orig.getClass() + ": [" + orig + "]");
        return orig;
    }
}
