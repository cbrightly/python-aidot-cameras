package org.glassfish.grizzly.http.server.naming;

public interface NamingContext {
    Object lookup(String str);
}
