package org.apache.http.auth;

import java.io.Serializable;
import java.security.Principal;
import java.util.Locale;
import org.apache.http.util.a;
import org.apache.http.util.h;

/* compiled from: NTUserPrincipal */
public class o implements Principal, Serializable {
    private static final long serialVersionUID = -6870169797924406894L;
    private final String domain;
    private final String ntname;
    private final String username;

    public o(String domain2, String username2) {
        a.i(username2, "User name");
        this.username = username2;
        if (domain2 != null) {
            this.domain = domain2.toUpperCase(Locale.ROOT);
        } else {
            this.domain = null;
        }
        String str = this.domain;
        if (str == null || str.isEmpty()) {
            this.ntname = username2;
            return;
        }
        this.ntname = this.domain + '\\' + username2;
    }

    public String getName() {
        return this.ntname;
    }

    public String getDomain() {
        return this.domain;
    }

    public String getUsername() {
        return this.username;
    }

    public int hashCode() {
        return h.d(h.d(17, this.username), this.domain);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof o)) {
            return false;
        }
        o that = (o) o;
        if (!h.a(this.username, that.username) || !h.a(this.domain, that.domain)) {
            return false;
        }
        return true;
    }

    public String toString() {
        return this.ntname;
    }
}
