package org.apache.http.auth;

import org.apache.http.d;
import org.apache.http.o;

/* compiled from: AuthScheme */
public interface c {
    @Deprecated
    d authenticate(l lVar, o oVar);

    String getRealm();

    String getSchemeName();

    boolean isComplete();

    boolean isConnectionBased();

    void processChallenge(d dVar);
}
