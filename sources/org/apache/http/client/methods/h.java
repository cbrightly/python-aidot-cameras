package org.apache.http.client.methods;

import java.net.URI;
import org.glassfish.grizzly.http.server.Constants;

/* compiled from: HttpGet */
public class h extends m {
    public h(URI uri) {
        k(uri);
    }

    public h(String uri) {
        k(URI.create(uri));
    }

    public String getMethod() {
        return Constants.GET;
    }
}
