package com.github.druk.dnssd;

public interface InternalBrowseListener extends BaseListener {
    void serviceFound(DNSSDService dNSSDService, int i, int i2, byte[] bArr, byte[] bArr2, byte[] bArr3);

    void serviceLost(DNSSDService dNSSDService, int i, int i2, byte[] bArr, byte[] bArr2, byte[] bArr3);
}
