package io.netty.handler.ssl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public final class IdentityCipherSuiteFilter implements CipherSuiteFilter {
    public static final IdentityCipherSuiteFilter INSTANCE = new IdentityCipherSuiteFilter();

    private IdentityCipherSuiteFilter() {
    }

    public String[] filterCipherSuites(Iterable<String> ciphers, List<String> defaultCiphers, Set<String> supportedCiphers) {
        String c;
        if (ciphers == null) {
            return (String[]) defaultCiphers.toArray(new String[defaultCiphers.size()]);
        }
        List<String> newCiphers = new ArrayList<>(supportedCiphers.size());
        Iterator<String> it = ciphers.iterator();
        while (it.hasNext() && (c = it.next()) != null) {
            newCiphers.add(c);
        }
        return (String[]) newCiphers.toArray(new String[newCiphers.size()]);
    }
}
