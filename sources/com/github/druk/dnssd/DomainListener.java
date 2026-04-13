package com.github.druk.dnssd;

public interface DomainListener extends BaseListener {
    void domainFound(DNSSDService dNSSDService, int i, int i2, String str);

    void domainLost(DNSSDService dNSSDService, int i, int i2, String str);
}
