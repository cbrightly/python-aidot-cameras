package com.github.druk.rx2dnssd;

import androidx.annotation.NonNull;
import io.reactivex.e;
import io.reactivex.i;

public interface Rx2Dnssd {
    @NonNull
    e<BonjourService> browse(@NonNull String str, @NonNull String str2);

    @NonNull
    e<BonjourService> queryIPRecords(BonjourService bonjourService);

    @NonNull
    i<BonjourService, BonjourService> queryIPRecords();

    @NonNull
    e<BonjourService> queryIPV4Records(BonjourService bonjourService);

    @NonNull
    i<BonjourService, BonjourService> queryIPV4Records();

    @NonNull
    e<BonjourService> queryIPV6Records(BonjourService bonjourService);

    @NonNull
    i<BonjourService, BonjourService> queryIPV6Records();

    @NonNull
    @Deprecated
    i<BonjourService, BonjourService> queryRecords();

    @NonNull
    e<BonjourService> queryTXTRecords(BonjourService bonjourService);

    @NonNull
    e<BonjourService> register(@NonNull BonjourService bonjourService);

    @NonNull
    i<BonjourService, BonjourService> resolve();
}
