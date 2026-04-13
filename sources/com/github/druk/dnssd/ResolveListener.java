package com.github.druk.dnssd;

import java.util.Map;

public interface ResolveListener extends BaseListener {
    void serviceResolved(DNSSDService dNSSDService, int i, int i2, String str, String str2, int i3, Map<String, String> map);
}
