package org.apache.httpcore.message;

import java.io.Serializable;
import org.apache.httpcore.e;
import org.apache.httpcore.f;
import org.apache.httpcore.util.a;
import org.apache.httpcore.util.d;

/* compiled from: BasicHeader */
public class b implements e, Cloneable, Serializable {
    private static final f[] c = new f[0];
    private static final long serialVersionUID = -5427236326487562174L;
    private final String name;
    private final String value;

    public b(String name2, String value2) {
        this.name = (String) a.g(name2, "Name");
        this.value = value2;
    }

    public Object clone() {
        return super.clone();
    }

    public f[] getElements() {
        if (getValue() != null) {
            return e.d(getValue(), (r) null);
        }
        return c;
    }

    public String getName() {
        return this.name;
    }

    public String getValue() {
        return this.value;
    }

    public String toString() {
        return i.b.a((d) null, this).toString();
    }
}
