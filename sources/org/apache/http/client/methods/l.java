package org.apache.http.client.methods;

import java.net.URI;

/* compiled from: HttpPut */
public class l extends f {
    public l(URI uri) {
        k(uri);
    }

    public l(String uri) {
        k(URI.create(uri));
    }

    public String getMethod() {
        return "PUT";
    }
}
