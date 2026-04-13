package org.apache.commons.logging;

import java.security.PrivilegedAction;

/* compiled from: LogFactory */
public final class g implements PrivilegedAction {
    private final /* synthetic */ String a;
    private final /* synthetic */ String b;

    g(String str, String str2) {
        this.a = str;
        this.b = str2;
    }

    public Object run() {
        return System.getProperty(this.a, this.b);
    }
}
