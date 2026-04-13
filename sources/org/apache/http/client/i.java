package org.apache.http.client;

import java.net.URI;
import org.apache.http.protocol.f;
import org.apache.http.q;

@Deprecated
/* compiled from: RedirectHandler */
public interface i {
    URI getLocationURI(q qVar, f fVar);

    boolean isRedirectRequested(q qVar, f fVar);
}
