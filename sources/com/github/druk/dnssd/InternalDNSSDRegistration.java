package com.github.druk.dnssd;

import com.github.druk.dnssd.InternalDNSSDService;

public class InternalDNSSDRegistration implements DNSSDRegistration {
    private boolean isStopped = false;
    private final InternalDNSSDService.DnssdServiceListener listener;
    private final DNSSDRegistration originalDNSSDService;

    InternalDNSSDRegistration(InternalDNSSDService.DnssdServiceListener listener2, DNSSDRegistration registration) {
        this.listener = listener2;
        this.originalDNSSDService = registration;
    }

    public DNSRecord getTXTRecord() {
        return this.originalDNSSDService.getTXTRecord();
    }

    public DNSRecord addRecord(int flags, int rrType, byte[] rData, int ttl) {
        return this.originalDNSSDService.addRecord(flags, rrType, rData, ttl);
    }

    public void stop() {
        this.originalDNSSDService.stop();
        synchronized (this) {
            if (!this.isStopped) {
                this.listener.onServiceStopped();
                this.isStopped = true;
            }
        }
    }
}
