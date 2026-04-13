package org.apache.http.impl.client;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.auth.l;
import org.apache.http.util.a;

/* compiled from: BasicCredentialsProvider */
public class g implements org.apache.http.client.g {
    private final ConcurrentHashMap<org.apache.http.auth.g, l> a = new ConcurrentHashMap<>();

    public void a(org.apache.http.auth.g authscope, l credentials) {
        a.i(authscope, "Authentication scope");
        this.a.put(authscope, credentials);
    }

    private static l c(Map<org.apache.http.auth.g, l> map, org.apache.http.auth.g authscope) {
        l creds = map.get(authscope);
        if (creds != null) {
            return creds;
        }
        int bestMatchFactor = -1;
        org.apache.http.auth.g bestMatch = null;
        for (org.apache.http.auth.g current : map.keySet()) {
            int factor = authscope.e(current);
            if (factor > bestMatchFactor) {
                bestMatchFactor = factor;
                bestMatch = current;
            }
        }
        if (bestMatch != null) {
            return map.get(bestMatch);
        }
        return creds;
    }

    public l b(org.apache.http.auth.g authscope) {
        a.i(authscope, "Authentication scope");
        return c(this.a, authscope);
    }

    public String toString() {
        return this.a.toString();
    }
}
