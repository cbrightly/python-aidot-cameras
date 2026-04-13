package com.typesafe.config;

/* compiled from: ConfigResolveOptions */
public final class h {
    private final boolean a;
    private final boolean b;

    private h(boolean useSystemEnvironment, boolean allowUnresolved) {
        this.a = useSystemEnvironment;
        this.b = allowUnresolved;
    }

    public static h a() {
        return new h(true, false);
    }

    public boolean c() {
        return this.a;
    }

    public boolean b() {
        return this.b;
    }
}
