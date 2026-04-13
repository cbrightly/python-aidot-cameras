package org.apache.http.conn.ssl;

import org.apache.http.util.a;

/* compiled from: SubjectName */
public final class i {
    private final String a;
    private final int b;

    i(String value, int type) {
        this.a = (String) a.i(value, "Value");
        this.b = a.j(type, "Type");
    }

    public int a() {
        return this.b;
    }

    public String b() {
        return this.a;
    }

    public String toString() {
        return this.a;
    }
}
