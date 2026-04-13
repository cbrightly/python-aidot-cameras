package com.github.druk.dnssd;

public interface InternalResolveListener extends BaseListener {
    void serviceResolved(DNSSDService dNSSDService, int i, int i2, byte[] bArr, byte[] bArr2, int i3, TXTRecord tXTRecord);
}
