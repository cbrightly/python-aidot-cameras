package com.github.druk.dnssd;

import com.github.druk.dnssd.InternalDNSSDService;

public class InternalDNSSDRecordRegistrar implements DNSSDRecordRegistrar {
    private boolean isStopped = false;
    private final InternalDNSSDService.DnssdServiceListener listener;
    private final DNSSDRecordRegistrar originalService;

    InternalDNSSDRecordRegistrar(InternalDNSSDService.DnssdServiceListener listener2, DNSSDRecordRegistrar registration) {
        this.listener = listener2;
        this.originalService = registration;
    }

    public DNSRecord registerRecord(int flags, int ifIndex, String fullname, int rrtype, int rrclass, byte[] rData, int ttl) {
        return this.originalService.registerRecord(flags, ifIndex, fullname, rrtype, rrclass, rData, ttl);
    }

    public void stop() {
        this.originalService.stop();
        synchronized (this) {
            if (!this.isStopped) {
                this.listener.onServiceStopped();
                this.isStopped = true;
            }
        }
    }
}
