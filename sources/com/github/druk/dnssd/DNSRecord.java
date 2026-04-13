package com.github.druk.dnssd;

public interface DNSRecord {
    void remove();

    void update(int i, byte[] bArr, int i2);
}
