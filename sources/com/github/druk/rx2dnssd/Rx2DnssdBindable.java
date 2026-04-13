package com.github.druk.rx2dnssd;

import android.annotation.TargetApi;
import android.content.Context;
import androidx.annotation.NonNull;
import com.github.druk.dnssd.DNSSD;
import com.github.druk.dnssd.DNSSDBindable;
import io.reactivex.e;
import io.reactivex.i;

@TargetApi(16)
public class Rx2DnssdBindable extends Rx2DnssdCommon {
    @NonNull
    public /* bridge */ /* synthetic */ e browse(@NonNull String str, @NonNull String str2) {
        return super.browse(str, str2);
    }

    public /* bridge */ /* synthetic */ DNSSD getDNSSD() {
        return super.getDNSSD();
    }

    @NonNull
    public /* bridge */ /* synthetic */ e queryIPRecords(BonjourService bonjourService) {
        return super.queryIPRecords(bonjourService);
    }

    @NonNull
    public /* bridge */ /* synthetic */ i queryIPRecords() {
        return super.queryIPRecords();
    }

    @NonNull
    public /* bridge */ /* synthetic */ e queryIPV4Records(BonjourService bonjourService) {
        return super.queryIPV4Records(bonjourService);
    }

    @NonNull
    public /* bridge */ /* synthetic */ i queryIPV4Records() {
        return super.queryIPV4Records();
    }

    @NonNull
    public /* bridge */ /* synthetic */ e queryIPV6Records(BonjourService bonjourService) {
        return super.queryIPV6Records(bonjourService);
    }

    @NonNull
    public /* bridge */ /* synthetic */ i queryIPV6Records() {
        return super.queryIPV6Records();
    }

    @NonNull
    @Deprecated
    public /* bridge */ /* synthetic */ i queryRecords() {
        return super.queryRecords();
    }

    @NonNull
    public /* bridge */ /* synthetic */ e queryTXTRecords(BonjourService bonjourService) {
        return super.queryTXTRecords(bonjourService);
    }

    @NonNull
    public /* bridge */ /* synthetic */ e register(@NonNull BonjourService bonjourService) {
        return super.register(bonjourService);
    }

    @NonNull
    public /* bridge */ /* synthetic */ i resolve() {
        return super.resolve();
    }

    public Rx2DnssdBindable(Context context) {
        super(new DNSSDBindable(context));
    }
}
