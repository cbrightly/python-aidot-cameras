package org.apache.commons.logging;

import java.security.PrivilegedAction;

/* compiled from: LogFactory */
public final class c implements PrivilegedAction {
    private final /* synthetic */ String a;
    private final /* synthetic */ ClassLoader b;

    c(String str, ClassLoader classLoader) {
        this.a = str;
        this.b = classLoader;
    }

    public Object run() {
        return h.d(this.a, this.b);
    }
}
