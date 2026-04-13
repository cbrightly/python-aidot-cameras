package org.apache.commons.logging;

import java.security.PrivilegedAction;

/* compiled from: LogFactory */
public final class d implements PrivilegedAction {
    private final /* synthetic */ ClassLoader a;
    private final /* synthetic */ String b;

    d(ClassLoader classLoader, String str) {
        this.a = classLoader;
        this.b = str;
    }

    public Object run() {
        ClassLoader classLoader = this.a;
        if (classLoader != null) {
            return classLoader.getResourceAsStream(this.b);
        }
        return ClassLoader.getSystemResourceAsStream(this.b);
    }
}
