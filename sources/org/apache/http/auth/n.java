package org.apache.http.auth;

import java.io.Serializable;
import java.security.Principal;
import java.util.Locale;
import org.apache.http.util.a;
import org.apache.http.util.h;

/* compiled from: NTCredentials */
public class n implements l, Serializable {
    private static final long serialVersionUID = -7385699315228907265L;
    private final String password;
    private final o principal;
    private final String workstation;

    @Deprecated
    public n(String usernamePassword) {
        String username;
        a.i(usernamePassword, "Username:password string");
        int atColon = usernamePassword.indexOf(58);
        if (atColon >= 0) {
            username = usernamePassword.substring(0, atColon);
            this.password = usernamePassword.substring(atColon + 1);
        } else {
            username = usernamePassword;
            this.password = null;
        }
        int atSlash = username.indexOf(47);
        if (atSlash >= 0) {
            this.principal = new o(username.substring(0, atSlash).toUpperCase(Locale.ROOT), username.substring(atSlash + 1));
        } else {
            this.principal = new o((String) null, username.substring(atSlash + 1));
        }
        this.workstation = null;
    }

    public n(String userName, String password2, String workstation2, String domain) {
        a.i(userName, "User name");
        this.principal = new o(domain, userName);
        this.password = password2;
        if (workstation2 != null) {
            this.workstation = workstation2.toUpperCase(Locale.ROOT);
        } else {
            this.workstation = null;
        }
    }

    public Principal getUserPrincipal() {
        return this.principal;
    }

    public String getUserName() {
        return this.principal.getUsername();
    }

    public String getPassword() {
        return this.password;
    }

    public String getDomain() {
        return this.principal.getDomain();
    }

    public String getWorkstation() {
        return this.workstation;
    }

    public int hashCode() {
        return h.d(h.d(17, this.principal), this.workstation);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof n)) {
            return false;
        }
        n that = (n) o;
        if (!h.a(this.principal, that.principal) || !h.a(this.workstation, that.workstation)) {
            return false;
        }
        return true;
    }

    public String toString() {
        return "[principal: " + this.principal + "][workstation: " + this.workstation + "]";
    }
}
