package org.apache.http.protocol;

import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: BasicHttpContext */
public class a implements f {
    private final f c;
    private final Map<String, Object> d;

    public a() {
        this((f) null);
    }

    public a(f parentContext) {
        this.d = new ConcurrentHashMap();
        this.c = parentContext;
    }

    public Object getAttribute(String id) {
        f fVar;
        org.apache.http.util.a.i(id, JsonDocumentFields.POLICY_ID);
        Object obj = this.d.get(id);
        if (obj != null || (fVar = this.c) == null) {
            return obj;
        }
        return fVar.getAttribute(id);
    }

    public void setAttribute(String id, Object obj) {
        org.apache.http.util.a.i(id, JsonDocumentFields.POLICY_ID);
        if (obj != null) {
            this.d.put(id, obj);
        } else {
            this.d.remove(id);
        }
    }

    public String toString() {
        return this.d.toString();
    }
}
