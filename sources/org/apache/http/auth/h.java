package org.apache.http.auth;

import com.meituan.robust.Constants;
import java.util.Queue;
import org.apache.http.util.a;

/* compiled from: AuthState */
public class h {
    private b a = b.UNCHALLENGED;
    private c b;
    private g c;
    private l d;
    private Queue<a> e;

    public void e() {
        this.a = b.UNCHALLENGED;
        this.e = null;
        this.b = null;
        this.c = null;
        this.d = null;
    }

    public b d() {
        return this.a;
    }

    public void f(b state) {
        this.a = state != null ? state : b.UNCHALLENGED;
    }

    public c b() {
        return this.b;
    }

    public l c() {
        return this.d;
    }

    public void h(c authScheme, l credentials) {
        a.i(authScheme, "Auth scheme");
        a.i(credentials, "Credentials");
        this.b = authScheme;
        this.d = credentials;
        this.e = null;
    }

    public Queue<a> a() {
        return this.e;
    }

    public void g(Queue<a> authOptions) {
        a.f(authOptions, "Queue of auth options");
        this.e = authOptions;
        this.b = null;
        this.d = null;
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("state:");
        buffer.append(this.a);
        buffer.append(Constants.PACKNAME_END);
        if (this.b != null) {
            buffer.append("auth scheme:");
            buffer.append(this.b.getSchemeName());
            buffer.append(Constants.PACKNAME_END);
        }
        if (this.d != null) {
            buffer.append("credentials present");
        }
        return buffer.toString();
    }
}
