package com.github.druk.dnssd;

public interface InternalDomainListener extends BaseListener {
    void domainFound(DNSSDService dNSSDService, int i, int i2, byte[] bArr);

    void domainLost(DNSSDService dNSSDService, int i, int i2, byte[] bArr);
}
