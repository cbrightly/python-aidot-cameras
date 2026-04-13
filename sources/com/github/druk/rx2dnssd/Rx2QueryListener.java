package com.github.druk.rx2dnssd;

import com.github.druk.dnssd.DNSSD;
import com.github.druk.dnssd.DNSSDService;
import com.github.druk.dnssd.QueryListener;
import com.github.druk.rx2dnssd.BonjourService;
import io.reactivex.f;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Rx2QueryListener implements QueryListener {
    private final BonjourService.Builder builder;
    private final boolean completable;
    private final f<? super BonjourService> emitter;

    Rx2QueryListener(f<? super BonjourService> emitter2, BonjourService.Builder builder2, boolean completable2) {
        this.emitter = emitter2;
        this.builder = builder2;
        this.completable = completable2;
    }

    public void queryAnswered(DNSSDService query, int flags, int ifIndex, String fullName, int rrtype, int rrclass, byte[] rdata, int ttl) {
        if (!this.emitter.isCancelled()) {
            if (rrtype == 1 || rrtype == 28) {
                try {
                    this.builder.inetAddress(InetAddress.getByAddress(rdata));
                } catch (UnknownHostException e) {
                    this.emitter.tryOnError(e);
                }
            } else if (rrtype == 16) {
                this.builder.dnsRecords(DNSSD.parseTXTRecords(rdata));
            } else {
                f<? super BonjourService> fVar = this.emitter;
                fVar.tryOnError(new Exception("Unsupported type of record: " + rrtype));
            }
            this.emitter.onNext(this.builder.build());
            if (this.completable) {
                this.emitter.onComplete();
            }
        }
    }

    public void operationFailed(DNSSDService service, int errorCode) {
        if (!this.emitter.isCancelled()) {
            f<? super BonjourService> fVar = this.emitter;
            fVar.onError(new RuntimeException("DNSSD queryRecord error: " + errorCode));
        }
    }
}
