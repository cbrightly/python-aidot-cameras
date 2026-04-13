package org.glassfish.tyrus.client.auth;

import org.glassfish.tyrus.core.Beta;
import org.glassfish.tyrus.core.l10n.LocalizationMessages;

@Beta
public final class Credentials {
    private final byte[] password;
    private final String username;

    public Credentials(String username2, byte[] password2) {
        if (username2 == null) {
            throw new IllegalArgumentException(LocalizationMessages.ARGUMENT_NOT_NULL("username"));
        } else if (password2 != null) {
            this.username = username2;
            this.password = password2;
        } else {
            throw new IllegalArgumentException(LocalizationMessages.ARGUMENT_NOT_NULL("password"));
        }
    }

    public Credentials(String username2, String password2) {
        if (username2 == null) {
            throw new IllegalArgumentException(LocalizationMessages.ARGUMENT_NOT_NULL("username"));
        } else if (password2 != null) {
            this.username = username2;
            this.password = password2.getBytes(AuthConfig.CHARACTER_SET);
        } else {
            throw new IllegalArgumentException(LocalizationMessages.ARGUMENT_NOT_NULL("password"));
        }
    }

    public String getUsername() {
        return this.username;
    }

    public byte[] getPassword() {
        return this.password;
    }

    public String toString() {
        return "Credentials{username: " + this.username + ", password: *****}";
    }
}
