package io.netty.util;

import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.StringUtil;
import java.net.IDN;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class DomainNameMapping<V> implements Mapping<String, V> {
    final V defaultValue;
    private final Map<String, V> map;
    private final Map<String, V> unmodifiableMap;

    @Deprecated
    public DomainNameMapping(V defaultValue2) {
        this(4, defaultValue2);
    }

    @Deprecated
    public DomainNameMapping(int initialCapacity, V defaultValue2) {
        this(new LinkedHashMap(initialCapacity), defaultValue2);
    }

    DomainNameMapping(Map<String, V> map2, V defaultValue2) {
        this.defaultValue = ObjectUtil.checkNotNull(defaultValue2, "defaultValue");
        this.map = map2;
        this.unmodifiableMap = map2 != null ? Collections.unmodifiableMap(map2) : null;
    }

    @Deprecated
    public DomainNameMapping<V> add(String hostname, V output) {
        this.map.put(normalizeHostname((String) ObjectUtil.checkNotNull(hostname, "hostname")), ObjectUtil.checkNotNull(output, "output"));
        return this;
    }

    static boolean matches(String template, String hostName) {
        if (!template.startsWith("*.")) {
            return template.equals(hostName);
        }
        if (template.regionMatches(2, hostName, 0, hostName.length()) || StringUtil.commonSuffixOfLength(hostName, template, template.length() - 1)) {
            return true;
        }
        return false;
    }

    static String normalizeHostname(String hostname) {
        if (needsNormalization(hostname)) {
            hostname = IDN.toASCII(hostname, 1);
        }
        return hostname.toLowerCase(Locale.US);
    }

    private static boolean needsNormalization(String hostname) {
        int length = hostname.length();
        for (int i = 0; i < length; i++) {
            if (hostname.charAt(i) > 127) {
                return true;
            }
        }
        return false;
    }

    public V map(String hostname) {
        if (hostname != null) {
            String hostname2 = normalizeHostname(hostname);
            for (Map.Entry<String, V> entry : this.map.entrySet()) {
                if (matches(entry.getKey(), hostname2)) {
                    return entry.getValue();
                }
            }
        }
        return this.defaultValue;
    }

    public Map<String, V> asMap() {
        return this.unmodifiableMap;
    }

    public String toString() {
        return StringUtil.simpleClassName((Object) this) + "(default: " + this.defaultValue + ", map: " + this.map + ')';
    }
}
