package org.apache.httpcore.message;

import org.apache.httpcore.f;
import org.apache.httpcore.u;
import org.apache.httpcore.util.a;
import org.apache.httpcore.util.g;

/* compiled from: BasicHeaderElement */
public class c implements f, Cloneable {
    private final String c;
    private final String d;
    private final u[] f;

    public c(String name, String value, u[] parameters) {
        this.c = (String) a.g(name, "Name");
        this.d = value;
        if (parameters != null) {
            this.f = parameters;
        } else {
            this.f = new u[0];
        }
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

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof f)) {
            return false;
        }
        c that = (c) object;
        if (!this.c.equals(that.c) || !g.a(this.d, that.d) || !g.b(this.f, that.f)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int hash = g.d(g.d(17, this.c), this.d);
        for (u parameter : this.f) {
            hash = g.d(hash, parameter);
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
