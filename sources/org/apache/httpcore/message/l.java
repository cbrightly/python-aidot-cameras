package org.apache.httpcore.message;

import java.io.Serializable;
import org.apache.httpcore.u;
import org.apache.httpcore.util.a;
import org.apache.httpcore.util.g;

/* compiled from: BasicNameValuePair */
public class l implements u, Cloneable, Serializable {
    private static final long serialVersionUID = -6437800749411518984L;
    private final String name;
    private final String value;

    public l(String name2, String value2) {
        this.name = (String) a.g(name2, "Name");
        this.value = value2;
    }

    public String getName() {
        return this.name;
    }

    public String getValue() {
        return this.value;
    }

    public String toString() {
        if (this.value == null) {
            return this.name;
        }
        StringBuilder buffer = new StringBuilder(this.name.length() + 1 + this.value.length());
        buffer.append(this.name);
        buffer.append("=");
        buffer.append(this.value);
        return buffer.toString();
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof u)) {
            return false;
        }
        l that = (l) object;
        if (!this.name.equals(that.name) || !g.a(this.value, that.value)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return g.d(g.d(17, this.name), this.value);
    }

    public Object clone() {
        return super.clone();
    }
}
