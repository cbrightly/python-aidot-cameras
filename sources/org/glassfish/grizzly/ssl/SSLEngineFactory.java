package org.glassfish.grizzly.ssl;

import javax.net.ssl.SSLEngine;

public interface SSLEngineFactory {
    SSLEngine createSSLEngine(String str, int i);
}
