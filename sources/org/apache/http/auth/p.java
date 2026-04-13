package org.apache.http.auth;

import java.io.Serializable;
import java.security.Principal;
import org.apache.http.util.a;
import org.apache.http.util.h;

/* compiled from: UsernamePasswordCredentials */
public class p implements l, Serializable {
    private static final long serialVersionUID = 243343858802739403L;
    private final String password;
    private final i principal;

    @Deprecated
    public p(String usernamePassword) {
        a.i(usernamePassword, "Username:password string");
        int atColon = usernamePassword.indexOf(58);
        if (atColon >= 0) {
            this.principal = new i(usernamePassword.substring(0, atColon));
            this.password = usernamePassword.substring(atColon + 1);
            return;
        }
        this.principal = new i(usernamePassword);
        this.password = null;
    }

    public p(String userName, String password2) {
        a.i(userName, "Username");
        this.principal = new i(userName);
        this.password = password2;
    }

    public Principal getUserPrincipal() {
        return this.principal;
    }

    public String getUserName() {
        return this.principal.getName();
    }

    public String getPassword() {
        return this.password;
    }

    public int hashCode() {
        return this.principal.hashCode();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof p) || !h.a(this.principal, ((p) o).principal)) {
            return false;
        }
        return true;
    }

    public String toString() {
        return this.principal.toString();
    }
}
