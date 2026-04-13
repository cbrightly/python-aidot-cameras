package org.glassfish.grizzly.http.server.accesslog;

import java.text.SimpleDateFormat;

public class SimpleDateFormatThreadLocal extends ThreadLocal<SimpleDateFormat> {
    private final SimpleDateFormat format;

    SimpleDateFormatThreadLocal(String format2) {
        this.format = new SimpleDateFormat(format2);
    }

    /* access modifiers changed from: protected */
    public SimpleDateFormat initialValue() {
        return (SimpleDateFormat) this.format.clone();
    }
}
