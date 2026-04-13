package com.github.druk.rx2dnssd;

import androidx.annotation.NonNull;
import com.github.druk.dnssd.DNSSD;
import com.github.druk.dnssd.DNSSDException;
import com.github.druk.dnssd.DNSSDService;
import com.github.druk.dnssd.TXTRecord;
import com.github.druk.rx2dnssd.BonjourService;
import io.reactivex.e;
import io.reactivex.f;
import io.reactivex.g;
import io.reactivex.i;
import java.util.Map;
import org.reactivestreams.a;

public abstract class Rx2DnssdCommon implements Rx2Dnssd {
    private final DNSSD mDNSSD;

    public interface DNSSDServiceCreator<T> {
        DNSSDService getService(f<? super T> fVar);
    }

    Rx2DnssdCommon(DNSSD dnssd) {
        this.mDNSSD = dnssd;
    }

    public DNSSD getDNSSD() {
        return this.mDNSSD;
    }

    @NonNull
    public e<BonjourService> browse(@NonNull String regType, @NonNull String domain) {
        return createFlowable(new h(this, regType, domain));
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$browse$0 */
    public /* synthetic */ DNSSDService a(String regType, String domain, f emitter) {
        return this.mDNSSD.browse(0, 0, regType, domain, new Rx2BrowseListener(emitter));
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$resolve$3 */
    public /* synthetic */ a v(e flowable) {
        return flowable.o(new t(this));
    }

    @NonNull
    public i<BonjourService, BonjourService> resolve() {
        return new f(this);
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$resolve$2 */
    public /* synthetic */ a u(BonjourService bs) {
        if ((bs.getFlags() & 256) == 256) {
            return e.w(bs);
        }
        return createFlowable(new g(this, bs));
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$resolve$1 */
    public /* synthetic */ DNSSDService t(BonjourService bs, f emitter) {
        return this.mDNSSD.resolve(bs.getFlags(), bs.getIfIndex(), bs.getServiceName(), bs.getRegType(), bs.getDomain(), new Rx2ResolveListener(emitter, bs));
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$queryRecords$7 */
    public /* synthetic */ a r(e flowable) {
        return flowable.o(new u(this));
    }

    @NonNull
    @Deprecated
    public i<BonjourService, BonjourService> queryRecords() {
        return new s(this);
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$queryRecords$6 */
    public /* synthetic */ a q(BonjourService bs) {
        if ((bs.getFlags() & 256) == 256) {
            return e.w(bs);
        }
        BonjourService.Builder builder = new BonjourService.Builder(bs);
        return createFlowable(new p(this, bs, builder)).z(createFlowable(new e(this, bs, builder)));
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$queryRecords$4 */
    public /* synthetic */ DNSSDService o(BonjourService bs, BonjourService.Builder builder, f emitter) {
        return this.mDNSSD.queryRecord(0, bs.getIfIndex(), bs.getHostname(), 1, 1, true, new Rx2QueryListener(emitter, builder, true));
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$queryRecords$5 */
    public /* synthetic */ DNSSDService p(BonjourService bs, BonjourService.Builder builder, f emitter) {
        return this.mDNSSD.queryRecord(0, bs.getIfIndex(), bs.getHostname(), 28, 1, true, new Rx2QueryListener(emitter, builder, true));
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$queryIPRecords$11 */
    public /* synthetic */ a c(e flowable) {
        return flowable.o(new o(this));
    }

    @NonNull
    public i<BonjourService, BonjourService> queryIPRecords() {
        return new l(this);
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$queryIPRecords$10 */
    public /* synthetic */ a b(BonjourService bs) {
        if ((bs.getFlags() & 256) == 256) {
            return e.w(bs);
        }
        BonjourService.Builder builder = new BonjourService.Builder(bs);
        return createFlowable(new v(this, bs, builder)).z(createFlowable(new b(this, bs, builder)));
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$queryIPRecords$8 */
    public /* synthetic */ DNSSDService f(BonjourService bs, BonjourService.Builder builder, f emitter) {
        return this.mDNSSD.queryRecord(0, bs.getIfIndex(), bs.getHostname(), 1, 1, true, new Rx2QueryListener(emitter, builder, true));
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$queryIPRecords$9 */
    public /* synthetic */ DNSSDService g(BonjourService bs, BonjourService.Builder builder, f emitter) {
        return this.mDNSSD.queryRecord(0, bs.getIfIndex(), bs.getHostname(), 28, 1, true, new Rx2QueryListener(emitter, builder, true));
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$queryIPV4Records$14 */
    public /* synthetic */ a j(e flowable) {
        return flowable.o(new m(this));
    }

    @NonNull
    public i<BonjourService, BonjourService> queryIPV4Records() {
        return new q(this);
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$queryIPV4Records$13 */
    public /* synthetic */ a i(BonjourService bs) {
        if ((bs.getFlags() & 256) == 256) {
            return e.w(bs);
        }
        return createFlowable(new j(this, bs));
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$queryIPV4Records$12 */
    public /* synthetic */ DNSSDService h(BonjourService bs, f emitter) {
        return this.mDNSSD.queryRecord(0, bs.getIfIndex(), bs.getHostname(), 1, 1, true, new Rx2QueryListener(emitter, new BonjourService.Builder(bs), true));
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$queryIPV6Records$17 */
    public /* synthetic */ a m(e flowable) {
        return flowable.o(new k(this));
    }

    @NonNull
    public i<BonjourService, BonjourService> queryIPV6Records() {
        return new c(this);
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$queryIPV6Records$16 */
    public /* synthetic */ a l(BonjourService bs) {
        if ((bs.getFlags() & 256) == 256) {
            return e.w(bs);
        }
        return createFlowable(new i(this, bs));
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$queryIPV6Records$15 */
    public /* synthetic */ DNSSDService k(BonjourService bs, f emitter) {
        return this.mDNSSD.queryRecord(0, bs.getIfIndex(), bs.getHostname(), 28, 1, true, new Rx2QueryListener(emitter, new BonjourService.Builder(bs), true));
    }

    @NonNull
    public e<BonjourService> queryIPRecords(BonjourService bs) {
        if ((bs.getFlags() & 256) == 256) {
            return e.w(bs);
        }
        BonjourService.Builder builder = new BonjourService.Builder(bs);
        return createFlowable(new n(this, bs, builder)).z(createFlowable(new w(this, bs, builder)));
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$queryIPRecords$18 */
    public /* synthetic */ DNSSDService d(BonjourService bs, BonjourService.Builder builder, f subscriber) {
        return this.mDNSSD.queryRecord(0, bs.getIfIndex(), bs.getHostname(), 1, 1, false, new Rx2QueryListener(subscriber, builder, false));
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$queryIPRecords$19 */
    public /* synthetic */ DNSSDService e(BonjourService bs, BonjourService.Builder builder, f subscriber) {
        return this.mDNSSD.queryRecord(0, bs.getIfIndex(), bs.getHostname(), 28, 1, false, new Rx2QueryListener(subscriber, builder, false));
    }

    @NonNull
    public e<BonjourService> queryIPV4Records(BonjourService bs) {
        return queryRecords(bs, 1);
    }

    @NonNull
    public e<BonjourService> queryIPV6Records(BonjourService bs) {
        return queryRecords(bs, 28);
    }

    @NonNull
    public e<BonjourService> queryTXTRecords(BonjourService bs) {
        return queryRecords(bs, 16);
    }

    private e<BonjourService> queryRecords(BonjourService bs, int type) {
        if ((bs.getFlags() & 256) == 256) {
            return e.w(bs);
        }
        return createFlowable(new d(this, bs, type));
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$queryRecords$20 */
    public /* synthetic */ DNSSDService n(BonjourService bs, int type, f subscriber) {
        return this.mDNSSD.queryRecord(0, bs.getIfIndex(), bs.getHostname(), type, 1, false, new Rx2QueryListener(subscriber, new BonjourService.Builder(bs), false));
    }

    @NonNull
    public e<BonjourService> register(@NonNull BonjourService bs) {
        return createFlowable(new a(this, bs));
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$register$21 */
    public /* synthetic */ DNSSDService s(BonjourService bs, f emitter) {
        return this.mDNSSD.register(bs.getFlags(), bs.getIfIndex(), bs.getServiceName(), bs.getRegType(), bs.getDomain(), (String) null, bs.getPort(), createTxtRecord(bs.getTxtRecords()), new Rx2RegisterListener(emitter));
    }

    public static class DNSSDServiceAction<T> implements g<T>, io.reactivex.functions.a {
        private final DNSSDServiceCreator<T> creator;
        private DNSSDService service;

        DNSSDServiceAction(DNSSDServiceCreator<T> creator2) {
            this.creator = creator2;
        }

        public void subscribe(f<T> emitter) {
            DNSSDServiceCreator<T> dNSSDServiceCreator;
            if (!emitter.isCancelled() && (dNSSDServiceCreator = this.creator) != null) {
                try {
                    this.service = dNSSDServiceCreator.getService(emitter);
                } catch (DNSSDException e) {
                    emitter.onError(e);
                }
            }
        }

        public void run() {
            DNSSDService dNSSDService = this.service;
            if (dNSSDService != null) {
                dNSSDService.stop();
                this.service = null;
            }
        }
    }

    private <T> e<T> createFlowable(DNSSDServiceCreator<T> creator) {
        DNSSDServiceAction<T> action = new DNSSDServiceAction<>(creator);
        return e.d(action, io.reactivex.a.BUFFER).k(action);
    }

    private static TXTRecord createTxtRecord(Map<String, String> records) {
        TXTRecord txtRecord = new TXTRecord();
        for (Map.Entry<String, String> entry : records.entrySet()) {
            txtRecord.set(entry.getKey(), entry.getValue());
        }
        return txtRecord;
    }
}
