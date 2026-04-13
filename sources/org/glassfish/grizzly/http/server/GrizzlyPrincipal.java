package org.glassfish.grizzly.http.server;

import java.io.Serializable;
import java.security.Principal;

public class GrizzlyPrincipal implements Principal, Serializable {
    private static final long serialVersionUID = 1;
    protected String name = null;

    public GrizzlyPrincipal(String name2) {
        this.name = name2;
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return "GrizzlyPrincipal[" + this.name + "]";
    }
}
