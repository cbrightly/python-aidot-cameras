package org.apache.http.conn.util;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/* compiled from: PublicSuffixListParser */
public final class d {
    public List<c> a(Reader reader) {
        List<PublicSuffixList> result = new ArrayList<>(2);
        BufferedReader r = new BufferedReader(reader);
        new StringBuilder(256);
        a domainType = null;
        List<String> rules = null;
        List<String> exceptions = null;
        while (true) {
            String readLine = r.readLine();
            String line = readLine;
            if (readLine == null) {
                return result;
            }
            if (!line.isEmpty()) {
                if (line.startsWith("//")) {
                    if (domainType == null) {
                        if (line.contains("===BEGIN ICANN DOMAINS===")) {
                            domainType = a.ICANN;
                        } else if (line.contains("===BEGIN PRIVATE DOMAINS===")) {
                            domainType = a.PRIVATE;
                        }
                    } else if (line.contains("===END ICANN DOMAINS===") || line.contains("===END PRIVATE DOMAINS===")) {
                        if (rules != null) {
                            result.add(new c(domainType, rules, exceptions));
                        }
                        domainType = null;
                        rules = null;
                        exceptions = null;
                    }
                } else if (domainType != null) {
                    if (line.startsWith(".")) {
                        line = line.substring(1);
                    }
                    boolean isException = line.startsWith("!");
                    if (isException) {
                        line = line.substring(1);
                    }
                    if (isException) {
                        if (exceptions == null) {
                            exceptions = new ArrayList<>();
                        }
                        exceptions.add(line);
                    } else {
                        if (rules == null) {
                            rules = new ArrayList<>();
                        }
                        rules.add(line);
                    }
                }
            }
        }
    }
}
