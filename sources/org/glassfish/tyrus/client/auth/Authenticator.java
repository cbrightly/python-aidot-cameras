package org.glassfish.tyrus.client.auth;

import java.net.URI;
import org.glassfish.tyrus.core.Beta;

@Beta
public abstract class Authenticator {
    public abstract String generateAuthorizationHeader(URI uri, String str, Credentials credentials);
}
