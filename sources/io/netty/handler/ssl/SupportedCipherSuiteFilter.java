package io.netty.handler.ssl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public final class SupportedCipherSuiteFilter implements CipherSuiteFilter {
    public static final SupportedCipherSuiteFilter INSTANCE = new SupportedCipherSuiteFilter();

    private SupportedCipherSuiteFilter() {
    }

    public String[] filterCipherSuites(Iterable<String> ciphers, List<String> defaultCiphers, Set<String> supportedCiphers) {
        List<String> newCiphers;
        String c;
        if (defaultCiphers == null) {
            throw new NullPointerException("defaultCiphers");
        } else if (supportedCiphers != null) {
            if (ciphers == null) {
                newCiphers = new ArrayList<>(defaultCiphers.size());
                ciphers = defaultCiphers;
            } else {
                newCiphers = new ArrayList<>(supportedCiphers.size());
            }
            Iterator<String> it = ciphers.iterator();
            while (it.hasNext() && (c = it.next()) != null) {
                if (supportedCiphers.contains(c)) {
                    newCiphers.add(c);
                }
            }
            return (String[]) newCiphers.toArray(new String[newCiphers.size()]);
        } else {
            throw new NullPointerException("supportedCiphers");
        }
    }
}
