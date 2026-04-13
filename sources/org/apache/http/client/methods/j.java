package org.apache.http.client.methods;

import java.net.URI;

/* compiled from: HttpOptions */
public class j extends m {
    public j(String uri) {
        k(URI.create(uri));
    }

    public String getMethod() {
        return "OPTIONS";
    }
}
