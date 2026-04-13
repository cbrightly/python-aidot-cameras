package org.glassfish.grizzly.http.server;

import org.glassfish.grizzly.filterchain.FilterChainBuilder;

public interface AddOn {
    void setup(NetworkListener networkListener, FilterChainBuilder filterChainBuilder);
}
