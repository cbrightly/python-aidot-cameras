package org.apache.http.message;

import java.io.Serializable;
import org.apache.http.d;
import org.apache.http.e;
import org.apache.http.util.a;

/* compiled from: BasicHeader */
public class b implements d, Cloneable, Serializable {
    private static final long serialVersionUID = -5427236326487562174L;
    private final String name;
    private final String value;

    public b(String name2, String value2) {
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
        return j.b.b((org.apache.http.util.d) null, this).toString();
    }

    public e[] getElements() {
        String str = this.value;
        if (str != null) {
            return g.e(str, (s) null);
        }
        return new e[0];
    }

    public Object clone() {
        return super.clone();
    }
}
