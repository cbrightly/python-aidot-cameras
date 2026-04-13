package org.glassfish.tyrus.container.grizzly.client;

import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.http.HttpClientFilter;

public class HttpCodecFilter extends HttpClientFilter {
    HttpCodecFilter() {
    }

    /* access modifiers changed from: package-private */
    public void resetResponseProcessing(Connection connection) {
        clearResponse(connection);
    }
}
