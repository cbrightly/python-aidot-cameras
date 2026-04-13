package io.ktor.utils.io.utils;

/* compiled from: Atomic.kt */
public final class a {
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0022, code lost:
        r0 = kotlin.text.v.o(r0);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final int a(@org.jetbrains.annotations.NotNull java.lang.String r2, int r3) {
        /*
            java.lang.String r0 = "name"
            kotlin.jvm.internal.k.f(r2, r0)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ SecurityException -> 0x001d }
            r0.<init>()     // Catch:{ SecurityException -> 0x001d }
            java.lang.String r1 = "io.ktor.utils.io."
            r0.append(r1)     // Catch:{ SecurityException -> 0x001d }
            r0.append(r2)     // Catch:{ SecurityException -> 0x001d }
            java.lang.String r0 = r0.toString()     // Catch:{ SecurityException -> 0x001d }
            java.lang.String r0 = java.lang.System.getProperty(r0)     // Catch:{ SecurityException -> 0x001d }
            goto L_0x0020
        L_0x001d:
            r0 = move-exception
            r1 = 0
            r0 = r1
        L_0x0020:
            if (r0 == 0) goto L_0x002d
            java.lang.Integer r0 = kotlin.text.v.o(r0)
            if (r0 == 0) goto L_0x002d
            int r0 = r0.intValue()
            goto L_0x002e
        L_0x002d:
            r0 = r3
        L_0x002e:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.utils.a.a(java.lang.String, int):int");
    }
}
