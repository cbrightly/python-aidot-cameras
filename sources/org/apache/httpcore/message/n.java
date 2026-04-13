package org.apache.httpcore.message;

import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import java.io.Serializable;
import org.apache.httpcore.util.a;
import org.apache.httpcore.util.d;
import org.apache.httpcore.v;
import org.apache.httpcore.y;

/* compiled from: BasicStatusLine */
public class n implements y, Cloneable, Serializable {
    private static final long serialVersionUID = -2443303766890459269L;
    private final v protoVersion;
    private final String reasonPhrase;
    private final int statusCode;

    public n(v version, int statusCode2, String reasonPhrase2) {
        this.protoVersion = (v) a.g(version, JsonDocumentFields.VERSION);
        this.statusCode = a.e(statusCode2, "Status code");
        this.reasonPhrase = reasonPhrase2;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public v getProtocolVersion() {
        return this.protoVersion;
    }

    public String getReasonPhrase() {
        return this.reasonPhrase;
    }

    public String toString() {
        return i.b.b((d) null, this).toString();
    }

    public Object clone() {
        return super.clone();
    }
}
