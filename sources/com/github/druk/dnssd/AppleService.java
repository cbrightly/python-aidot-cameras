package com.github.druk.dnssd;

/* compiled from: InternalDNSSD */
public class AppleService implements DNSSDService, Runnable {
    protected BaseListener fListener;
    protected long fNativeContext = 0;

    /* access modifiers changed from: protected */
    public native int BlockForData();

    /* access modifiers changed from: protected */
    public native synchronized void HaltOperation();

    /* access modifiers changed from: protected */
    public native int ProcessResults();

    public AppleService(BaseListener listener) {
        this.fListener = listener;
    }

    public void stop() {
        HaltOperation();
    }

    /* access modifiers changed from: protected */
    public void ThrowOnErr(int rc) {
        if (rc != 0) {
            throw new AppleDNSSDException(rc);
        }
    }

    public void run() {
        while (true) {
            int result = BlockForData();
            synchronized (this) {
                if (this.fNativeContext != 0) {
                    if (result != 0) {
                        int result2 = ProcessResults();
                        if (this.fNativeContext != 0) {
                            if (result2 != 0) {
                                this.fListener.operationFailed(this, result2);
                                return;
                            }
                        } else {
                            return;
                        }
                    }
                } else {
                    return;
                }
            }
        }
    }
}
