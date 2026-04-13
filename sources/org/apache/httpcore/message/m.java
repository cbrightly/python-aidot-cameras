package org.apache.httpcore.message;

import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import java.io.Serializable;
import org.apache.httpcore.util.a;
import org.apache.httpcore.util.d;
import org.apache.httpcore.v;
import org.apache.httpcore.x;

/* compiled from: BasicRequestLine */
public class m implements x, Cloneable, Serializable {
    private static final long serialVersionUID = 2810581718468737193L;
    private final String method;
    private final v protoversion;
    private final String uri;

    public m(String method2, String uri2, v version) {
        this.method = (String) a.g(method2, "Method");
        this.uri = (String) a.g(uri2, "URI");
        this.protoversion = (v) a.g(version, JsonDocumentFields.VERSION);
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
        return i.b.h((d) null, this).toString();
    }

    public Object clone() {
        return super.clone();
    }
}
