package org.apache.http.client.methods;

import java.net.URI;
import org.glassfish.grizzly.http.server.Constants;

/* compiled from: HttpPost */
public class k extends f {
    public k(URI uri) {
        k(uri);
    }

    public k(String uri) {
        k(URI.create(uri));
    }

    public String getMethod() {
        return Constants.POST;
    }
}
