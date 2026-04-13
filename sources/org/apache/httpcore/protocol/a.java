package org.apache.httpcore.protocol;

import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: BasicHttpContext */
public class a implements d {
    private final d a;
    private final Map<String, Object> b;

    public a() {
        this((d) null);
    }

    public a(d parentContext) {
        this.b = new ConcurrentHashMap();
        this.a = parentContext;
    }

    public Object getAttribute(String id) {
        d dVar;
        org.apache.httpcore.util.a.g(id, JsonDocumentFields.POLICY_ID);
        Object obj = this.b.get(id);
        if (obj != null || (dVar = this.a) == null) {
            return obj;
        }
        return dVar.getAttribute(id);
    }

    public void setAttribute(String id, Object obj) {
        org.apache.httpcore.util.a.g(id, JsonDocumentFields.POLICY_ID);
        if (obj != null) {
            this.b.put(id, obj);
        } else {
            this.b.remove(id);
        }
    }

    public void a() {
        this.b.clear();
    }

    public String toString() {
        return this.b.toString();
    }
}
