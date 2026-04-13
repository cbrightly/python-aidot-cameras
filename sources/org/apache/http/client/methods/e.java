package org.apache.http.client.methods;

import java.net.URI;

/* compiled from: HttpDelete */
public class e extends m {
    public e(URI uri) {
        k(uri);
    }

    public e(String uri) {
        k(URI.create(uri));
    }

    public String getMethod() {
        return "DELETE";
    }
}
