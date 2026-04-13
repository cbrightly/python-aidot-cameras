package com.github.druk.dnssd;

public class InternalDNSSDService implements DNSSDService {
    private boolean isStopped = false;
    private final DnssdServiceListener listener;
    private final DNSSDService originalDNSSDService;

    public interface DnssdServiceListener {
        void onServiceStarting();

        void onServiceStopped();
    }

    InternalDNSSDService(DnssdServiceListener listener2, DNSSDService DNSSDService) {
        this.listener = listener2;
        this.originalDNSSDService = DNSSDService;
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
