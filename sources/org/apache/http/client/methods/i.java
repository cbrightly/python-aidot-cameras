package org.apache.http.client.methods;

import java.net.URI;
import org.glassfish.grizzly.http.server.Constants;

/* compiled from: HttpHead */
public class i extends m {
    public i(URI uri) {
        k(uri);
    }

    public i(String uri) {
        k(URI.create(uri));
    }

    public String getMethod() {
        return Constants.HEAD;
    }
}
