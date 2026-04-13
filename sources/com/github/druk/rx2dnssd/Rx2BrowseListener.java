package com.github.druk.rx2dnssd;

import com.github.druk.dnssd.BrowseListener;
import com.github.druk.dnssd.DNSSDService;
import com.github.druk.rx2dnssd.BonjourService;
import io.reactivex.f;

public class Rx2BrowseListener implements BrowseListener {
    private final f<? super BonjourService> emitter;

    Rx2BrowseListener(f<? super BonjourService> emitter2) {
        this.emitter = emitter2;
    }

    public void serviceFound(DNSSDService browser, int flags, int ifIndex, String serviceName, String regType, String domain) {
        if (!this.emitter.isCancelled()) {
            this.emitter.onNext(new BonjourService.Builder(flags, ifIndex, serviceName, regType, domain).build());
        }
    }

    public void serviceLost(DNSSDService browser, int flags, int ifIndex, String serviceName, String regType, String domain) {
        if (!this.emitter.isCancelled()) {
            this.emitter.onNext(new BonjourService.Builder(flags | 256, ifIndex, serviceName, regType, domain).build());
        }
    }

    public void operationFailed(DNSSDService service, int errorCode) {
        if (!this.emitter.isCancelled()) {
            f<? super BonjourService> fVar = this.emitter;
            fVar.onError(new RuntimeException("DNSSD browse error: " + errorCode));
        }
    }
}
