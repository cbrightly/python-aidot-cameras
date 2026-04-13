package org.apache.commons.lang3.concurrent;

import org.apache.commons.lang3.b;

/* compiled from: ConcurrentUtils */
public class a {
    static Throwable a(Throwable ex) {
        boolean z = ex != null && !(ex instanceof RuntimeException) && !(ex instanceof Error);
        b.a(z, "Not a checked exception: " + ex, new Object[0]);
        return ex;
    }
}
