package com.github.druk.rx2dnssd;

import com.github.druk.dnssd.DNSSDRegistration;
import com.github.druk.dnssd.DNSSDService;
import com.github.druk.dnssd.RegisterListener;
import com.github.druk.rx2dnssd.BonjourService;
import io.reactivex.f;

public class Rx2RegisterListener implements RegisterListener {
    private final f<? super BonjourService> emitter;

    Rx2RegisterListener(f<? super BonjourService> emitter2) {
        this.emitter = emitter2;
    }

    public void serviceRegistered(DNSSDRegistration registration, int flags, String serviceName, String regType, String domain) {
        if (!this.emitter.isCancelled()) {
            this.emitter.onNext(new BonjourService.Builder(flags, 0, serviceName, regType, domain).build());
        }
    }

    public void operationFailed(DNSSDService service, int errorCode) {
        if (!this.emitter.isCancelled()) {
            f<? super BonjourService> fVar = this.emitter;
            fVar.onError(new RuntimeException("DNSSD browse error: " + errorCode));
        }
    }
}
