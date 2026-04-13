package com.clj.fastble.bluetooth;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import com.clj.fastble.callback.h;
import com.clj.fastble.exception.d;
import java.util.LinkedList;
import java.util.Queue;

/* compiled from: SplitWriter */
public class g {
    private HandlerThread a;
    /* access modifiers changed from: private */
    public Handler b = new a(this.a.getLooper());
    private d c;
    private String d;
    private String e;
    private byte[] f;
    private int g;
    /* access modifiers changed from: private */
    public boolean h;
    /* access modifiers changed from: private */
    public long i;
    /* access modifiers changed from: private */
    public h j;
    /* access modifiers changed from: private */
    public Queue<byte[]> k;
    /* access modifiers changed from: private */
    public int l;

    public g() {
        HandlerThread handlerThread = new HandlerThread("splitWriter");
        this.a = handlerThread;
        handlerThread.start();
    }

    /* compiled from: SplitWriter */
    public class a extends Handler {
        a(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 51) {
                g.this.l();
            }
        }
    }

    public void k(d bleBluetooth, String uuid_service, String uuid_write, byte[] data, boolean sendNextWhenLastSuccess, long intervalBetweenTwoPackage, h callback) {
        this.c = bleBluetooth;
        this.d = uuid_service;
        this.e = uuid_write;
        this.f = data;
        this.h = sendNextWhenLastSuccess;
        this.i = intervalBetweenTwoPackage;
        this.g = com.clj.fastble.a.o().v();
        this.j = callback;
        j();
    }

    private void j() {
        byte[] bArr = this.f;
        if (bArr != null) {
            int i2 = this.g;
            if (i2 >= 1) {
                Queue<byte[]> i3 = i(bArr, i2);
                this.k = i3;
                this.l = i3.size();
                l();
                return;
            }
            throw new IllegalArgumentException("split count should higher than 0!");
        }
        throw new IllegalArgumentException("data is Null!");
    }

    /* access modifiers changed from: private */
    public void l() {
        if (this.k.peek() == null) {
            h();
            return;
        }
        this.c.P().v(this.d, this.e).w(this.k.poll(), new b(), this.e);
        if (!this.h) {
            this.b.sendMessageDelayed(this.b.obtainMessage(51), this.i);
        }
    }

    /* compiled from: SplitWriter */
    public class b extends h {
        b() {
        }

        public void b(int current, int total, byte[] justWrite) {
            int position = g.this.l - g.this.k.size();
            if (g.this.j != null) {
                g.this.j.b(position, g.this.l, justWrite);
            }
            if (g.this.h) {
                g.this.b.sendMessageDelayed(g.this.b.obtainMessage(51), g.this.i);
            }
        }

        public void a(com.clj.fastble.exception.a exception) {
            if (g.this.j != null) {
                h d = g.this.j;
                d.a(new d("exception occur while writing: " + exception.getDescription()));
            }
            if (g.this.h) {
                g.this.b.sendMessageDelayed(g.this.b.obtainMessage(51), g.this.i);
            }
        }
    }

    private void h() {
        this.a.quit();
        this.b.removeCallbacksAndMessages((Object) null);
    }

    private static Queue<byte[]> i(byte[] data, int count) {
        int pkgCount;
        byte[] dataPkg;
        if (count > 20) {
            com.clj.fastble.utils.a.c("Be careful: split count beyond 20! Ensure MTU higher than 23!");
        }
        Queue<byte[]> byteQueue = new LinkedList<>();
        if (data.length % count == 0) {
            pkgCount = data.length / count;
        } else {
            pkgCount = Math.round((float) ((data.length / count) + 1));
        }
        if (pkgCount > 0) {
            for (int i2 = 0; i2 < pkgCount; i2++) {
                if (pkgCount == 1 || i2 == pkgCount - 1) {
                    int j2 = data.length % count == 0 ? count : data.length % count;
                    dataPkg = new byte[j2];
                    Object obj = dataPkg;
                    System.arraycopy(data, i2 * count, dataPkg, 0, j2);
                } else {
                    byte[] bArr = new byte[count];
                    dataPkg = bArr;
                    System.arraycopy(data, i2 * count, bArr, 0, count);
                }
                byteQueue.offer(dataPkg);
            }
        }
        return byteQueue;
    }
}
