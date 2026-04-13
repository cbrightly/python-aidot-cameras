package com.github.druk.dnssd;

public interface InternalQueryListener extends BaseListener {
    void queryAnswered(DNSSDService dNSSDService, int i, int i2, byte[] bArr, int i3, int i4, byte[] bArr2, int i5);
}
