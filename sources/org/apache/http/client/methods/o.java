package org.apache.http.client.methods;

import java.net.URI;

/* compiled from: HttpTrace */
public class o extends m {
    public o(String uri) {
        k(URI.create(uri));
    }

    public String getMethod() {
        return "TRACE";
    }
}
