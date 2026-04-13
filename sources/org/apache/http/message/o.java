package org.apache.http.message;

import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import java.io.Serializable;
import org.apache.http.util.a;
import org.apache.http.util.d;
import org.apache.http.v;
import org.apache.http.y;

/* compiled from: BasicStatusLine */
public class o implements y, Cloneable, Serializable {
    private static final long serialVersionUID = -2443303766890459269L;
    private final v protoVersion;
    private final String reasonPhrase;
    private final int statusCode;

    public o(v version, int statusCode2, String reasonPhrase2) {
        this.protoVersion = (v) a.i(version, JsonDocumentFields.VERSION);
        this.statusCode = a.g(statusCode2, "Status code");
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
        return j.b.h((d) null, this).toString();
    }

    public Object clone() {
        return super.clone();
    }
}
