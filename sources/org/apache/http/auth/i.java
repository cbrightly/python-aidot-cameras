package org.apache.http.auth;

import java.io.Serializable;
import java.security.Principal;
import org.apache.http.util.a;
import org.apache.http.util.h;

/* compiled from: BasicUserPrincipal */
public final class i implements Principal, Serializable {
    private static final long serialVersionUID = -2266305184969850467L;
    private final String username;

    public i(String username2) {
        a.i(username2, "User name");
        this.username = username2;
    }

    public String getName() {
        return this.username;
    }

    public int hashCode() {
        return h.d(17, this.username);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof i) || !h.a(this.username, ((i) o).username)) {
            return false;
        }
        return true;
    }

    public String toString() {
        return "[principal: " + this.username + "]";
    }
}
