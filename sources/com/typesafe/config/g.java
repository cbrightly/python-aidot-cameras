package com.typesafe.config;

/* compiled from: ConfigRenderOptions */
public final class g {
    private final boolean a;
    private final boolean b;
    private final boolean c;
    private final boolean d;

    private g(boolean originComments, boolean comments, boolean formatted, boolean json) {
        this.a = originComments;
        this.b = comments;
        this.c = formatted;
        this.d = json;
    }

    public static g b() {
        return new g(true, true, true, true);
    }

    public static g a() {
        return new g(false, false, false, true);
    }

    public boolean c() {
        return this.b;
    }

    public boolean f() {
        return this.a;
    }

    public boolean d() {
        return this.c;
    }

    public boolean e() {
        return this.d;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("ConfigRenderOptions(");
        if (this.a) {
            sb.append("originComments,");
        }
        if (this.b) {
            sb.append("comments,");
        }
        if (this.c) {
            sb.append("formatted,");
        }
        if (this.d) {
            sb.append("json,");
        }
        if (sb.charAt(sb.length() - 1) == ',') {
            sb.setLength(sb.length() - 1);
        }
        sb.append(")");
        return sb.toString();
    }
}
