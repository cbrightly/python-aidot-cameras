package io.ktor.config;

/* compiled from: MapApplicationConfig.kt */
public final class d {
    /* access modifiers changed from: private */
    public static final String b(String root, String relative) {
        if (root.length() == 0) {
            return relative;
        }
        return root + '.' + relative;
    }
}
