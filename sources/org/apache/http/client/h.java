package org.apache.http.client;

import java.io.IOException;
import org.apache.http.protocol.f;

/* compiled from: HttpRequestRetryHandler */
public interface h {
    boolean retryRequest(IOException iOException, int i, f fVar);
}
