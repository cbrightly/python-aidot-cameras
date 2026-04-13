package org.apache.http.message;

import java.io.Serializable;
import org.apache.http.u;
import org.apache.http.util.a;
import org.apache.http.util.h;

/* compiled from: BasicNameValuePair */
public class m implements u, Cloneable, Serializable {
    private static final long serialVersionUID = -6437800749411518984L;
    private final String name;
    private final String value;

    public m(String name2, String value2) {
        this.name = (String) a.i(name2, "Name");
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
        m that = (m) object;
        if (!this.name.equals(that.name) || !h.a(this.value, that.value)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return h.d(h.d(17, this.name), this.value);
    }

    public Object clone() {
        return super.clone();
    }
}
