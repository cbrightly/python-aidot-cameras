package org.apache.http.conn.util;

import java.net.IDN;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.util.a;

/* compiled from: PublicSuffixMatcher */
public final class e {
    private final Map<String, a> a;
    private final Map<String, a> b;

    public e(Collection<String> rules, Collection<String> exceptions) {
        this(a.UNKNOWN, rules, exceptions);
    }

    public e(a domainType, Collection<String> rules, Collection<String> exceptions) {
        a.i(domainType, "Domain type");
        a.i(rules, "Domain suffix rules");
        this.a = new ConcurrentHashMap(rules.size());
        for (String rule : rules) {
            this.a.put(rule, domainType);
        }
        this.b = new ConcurrentHashMap();
        if (exceptions != null) {
            for (String exception : exceptions) {
                this.b.put(exception, domainType);
            }
        }
    }

    public e(Collection<c> lists) {
        a.i(lists, "Domain suffix lists");
        this.a = new ConcurrentHashMap();
        this.b = new ConcurrentHashMap();
        for (c list : lists) {
            a domainType = list.c();
            for (String rule : list.b()) {
                this.a.put(rule, domainType);
            }
            List<String> exceptions = list.a();
            if (exceptions != null) {
                for (String exception : exceptions) {
                    this.b.put(exception, domainType);
                }
            }
        }
    }

    private static boolean b(Map<String, a> map, String rule, a expectedType) {
        a domainType;
        if (map == null || (domainType = map.get(rule)) == null) {
            return false;
        }
        if (expectedType == null || domainType.equals(expectedType)) {
            return true;
        }
        return false;
    }

    private boolean d(String rule, a expectedType) {
        return b(this.a, rule, expectedType);
    }

    private boolean c(String exception, a expectedType) {
        return b(this.b, exception, expectedType);
    }

    public String a(String domain, a expectedType) {
        if (domain == null || domain.startsWith(".")) {
            return null;
        }
        String domainName = null;
        String segment = domain.toLowerCase(Locale.ROOT);
        while (segment != null) {
            if (!c(IDN.toUnicode(segment), expectedType)) {
                if (d(IDN.toUnicode(segment), expectedType)) {
                    break;
                }
                int nextdot = segment.indexOf(46);
                String nextSegment = nextdot != -1 ? segment.substring(nextdot + 1) : null;
                if (nextSegment != null) {
                    if (d("*." + IDN.toUnicode(nextSegment), expectedType)) {
                        break;
                    }
                }
                if (nextdot != -1) {
                    domainName = segment;
                }
                segment = nextSegment;
            } else {
                return segment;
            }
        }
        return domainName;
    }

    public boolean e(String domain) {
        return f(domain, (a) null);
    }

    public boolean f(String domain, a expectedType) {
        if (domain == null) {
            return false;
        }
        if (a(domain.startsWith(".") ? domain.substring(1) : domain, expectedType) == null) {
            return true;
        }
        return false;
    }
}
