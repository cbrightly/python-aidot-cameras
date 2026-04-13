package com.tencent.wcdb.support;

/* compiled from: CancellationSignal */
public final class a {
    private boolean a;
    private C0224a b;
    private boolean c;

    /* renamed from: com.tencent.wcdb.support.a$a  reason: collision with other inner class name */
    /* compiled from: CancellationSignal */
    public interface C0224a {
        void onCancel();
    }

    public boolean a() {
        boolean z;
        synchronized (this) {
            z = this.a;
        }
        return z;
    }

    public void b() {
        if (a()) {
            throw new OperationCanceledException();
        }
    }

    public void setOnCancelListener(C0224a listener) {
        synchronized (this) {
            c();
            if (this.b != listener) {
                this.b = listener;
                if (this.a) {
                    if (listener != null) {
                        listener.onCancel();
                    }
                }
            }
        }
    }

    private void c() {
        while (this.c) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
    }
}
