package org.apache.http.message;

import org.apache.http.e;
import org.apache.http.u;
import org.apache.http.util.a;
import org.apache.http.util.h;

/* compiled from: BasicHeaderElement */
public class c implements e, Cloneable {
    private final String c;
    private final String d;
    private final u[] f;

    public c(String name, String value, u[] parameters) {
        this.c = (String) a.i(name, "Name");
        this.d = value;
        if (parameters != null) {
            this.f = parameters;
        } else {
            this.f = new u[0];
        }
    }

    public c(String name, String value) {
        this(name, value, (u[]) null);
    }

    public String getName() {
        return this.c;
    }

    public String getValue() {
        return this.d;
    }

    public u[] getParameters() {
        return (u[]) this.f.clone();
    }

    public int a() {
        return this.f.length;
    }

    public u b(int index) {
        return this.f[index];
    }

    public u c(String name) {
        a.i(name, "Name");
        for (u current : this.f) {
            if (current.getName().equalsIgnoreCase(name)) {
                return current;
            }
        }
        return null;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof e)) {
            return false;
        }
        c that = (c) object;
        if (!this.c.equals(that.c) || !h.a(this.d, that.d) || !h.b(this.f, that.f)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int hash = h.d(h.d(17, this.c), this.d);
        for (u parameter : this.f) {
            hash = h.d(hash, parameter);
        }
        return hash;
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(this.c);
        if (this.d != null) {
            buffer.append("=");
            buffer.append(this.d);
        }
        for (u parameter : this.f) {
            buffer.append("; ");
            buffer.append(parameter);
        }
        return buffer.toString();
    }

    public Object clone() {
        return super.clone();
    }
}
