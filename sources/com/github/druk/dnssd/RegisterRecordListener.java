package com.github.druk.dnssd;

public interface RegisterRecordListener extends BaseListener {
    void recordRegistered(DNSRecord dNSRecord, int i);
}
