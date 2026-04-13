package org.glassfish.tyrus.client.auth;

import java.net.URI;
import java.util.Base64;
import org.glassfish.tyrus.core.l10n.LocalizationMessages;

public final class BasicAuthenticator extends Authenticator {
    BasicAuthenticator() {
    }

    public String generateAuthorizationHeader(URI uri, String wwwAuthenticateHeader, Credentials credentials) {
        return generateAuthorizationHeader(credentials);
    }

    private String generateAuthorizationHeader(Credentials credentials) {
        if (credentials != null) {
            String username = credentials.getUsername();
            byte[] password = credentials.getPassword();
            byte[] prefix = (username + ":").getBytes(AuthConfig.CHARACTER_SET);
            byte[] usernamePassword = new byte[(prefix.length + password.length)];
            System.arraycopy(prefix, 0, usernamePassword, 0, prefix.length);
            System.arraycopy(password, 0, usernamePassword, prefix.length, password.length);
            return "Basic " + Base64.getEncoder().encodeToString(usernamePassword);
        }
        throw new AuthenticationException(LocalizationMessages.AUTHENTICATION_CREDENTIALS_MISSING());
    }
}
