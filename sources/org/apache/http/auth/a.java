package org.apache.http.auth;

/* compiled from: AuthOption */
public final class a {
    private final c a;
    private final l b;

    public a(c authScheme, l creds) {
        org.apache.http.util.a.i(authScheme, "Auth scheme");
        org.apache.http.util.a.i(creds, "User credentials");
        this.a = authScheme;
        this.b = creds;
    }

    public c a() {
        return this.a;
    }

    public l b() {
        return this.b;
    }

    public String toString() {
        return this.a.toString();
    }
}
