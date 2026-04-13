package com.github.druk.dnssd;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.Log;

@TargetApi(16)
public final class DNSSDBindable extends DNSSD {
    private static final String TAG = "DNSSDBindable";
    private final Context context;

    public DNSSDBindable(Context context2) {
        super(context2, "jdns_sd");
        this.context = context2.getApplicationContext();
    }

    public void onServiceStarting() {
        super.onServiceStarting();
        try {
            this.context.getSystemService("servicediscovery");
        } catch (Exception e) {
            Log.e(TAG, "Can't start NSD_SERVICE: ", e);
        }
    }

    public void onServiceStopped() {
        super.onServiceStopped();
    }

    public String getNameForIfIndex(int ifIndex) {
        return InternalDNSSD.getNameForIfIndex(ifIndex);
    }
}
