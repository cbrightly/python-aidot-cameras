package com.github.druk.rx2dnssd;

import com.github.druk.dnssd.DNSSDService;
import com.github.druk.dnssd.ResolveListener;
import com.github.druk.rx2dnssd.BonjourService;
import io.reactivex.f;
import java.util.Map;

public class Rx2ResolveListener implements ResolveListener {
    private final BonjourService.Builder builder;
    private final f<? super BonjourService> emitter;

    Rx2ResolveListener(f<? super BonjourService> emitter2, BonjourService service) {
        this.emitter = emitter2;
        this.builder = new BonjourService.Builder(service);
    }

    public void serviceResolved(DNSSDService resolver, int flags, int ifIndex, String fullName, String hostName, int port, Map<String, String> txtRecord) {
        if (!this.emitter.isCancelled()) {
            this.emitter.onNext(this.builder.port(port).hostname(hostName).dnsRecords(txtRecord).build());
            this.emitter.onComplete();
        }
    }

    public void operationFailed(DNSSDService service, int errorCode) {
        if (!this.emitter.isCancelled()) {
            f<? super BonjourService> fVar = this.emitter;
            fVar.onError(new RuntimeException("DNSSD resolve error: " + errorCode));
        }
    }
}
