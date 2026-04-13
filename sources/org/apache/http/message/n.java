package org.apache.http.message;

import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import java.io.Serializable;
import org.apache.http.util.a;
import org.apache.http.util.d;
import org.apache.http.v;
import org.apache.http.x;

/* compiled from: BasicRequestLine */
public class n implements x, Cloneable, Serializable {
    private static final long serialVersionUID = 2810581718468737193L;
    private final String method;
    private final v protoversion;
    private final String uri;

    public n(String method2, String uri2, v version) {
        this.method = (String) a.i(method2, "Method");
        this.uri = (String) a.i(uri2, "URI");
        this.protoversion = (v) a.i(version, JsonDocumentFields.VERSION);
    }

    public String getMethod() {
        return this.method;
    }

    public v getProtocolVersion() {
        return this.protoversion;
    }

    public String getUri() {
        return this.uri;
    }

    public String toString() {
        return j.b.a((d) null, this).toString();
    }

    public Object clone() {
        return super.clone();
    }
}
