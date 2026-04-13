package com.github.druk.dnssd;

public interface InternalRegisterListener extends BaseListener {
    void serviceRegistered(DNSSDRegistration dNSSDRegistration, int i, byte[] bArr, byte[] bArr2, byte[] bArr3);
}
