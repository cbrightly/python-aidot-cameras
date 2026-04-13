package com.leedarson.serviceimpl.tcp.socket;

import androidx.work.Data;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import timber.log.a;

/* compiled from: LdsSocketClient */
public class a {
    public static ChangeQuickRedirect changeQuickRedirect;
    private final String a = "LdsSocketClient";
    private final int b = Data.MAX_DATA_BYTES;
    /* access modifiers changed from: private */
    public String c;
    /* access modifiers changed from: private */
    public int d;
    /* access modifiers changed from: private */
    public Socket e;
    /* access modifiers changed from: private */
    public InputStream f;
    /* access modifiers changed from: private */
    public OutputStream g;
    /* access modifiers changed from: private */
    public boolean h = false;
    /* access modifiers changed from: private */
    public byte[] i;
    /* access modifiers changed from: private */
    public d j;

    static /* synthetic */ void c(a x0, long x1) {
        if (!PatchProxy.proxy(new Object[]{x0, new Long(x1)}, (Object) null, changeQuickRedirect, true, 9087, new Class[]{a.class, Long.TYPE}, Void.TYPE).isSupported) {
            x0.q(x1);
        }
    }

    static /* synthetic */ void h(a x0, byte[] x1) {
        Class[] clsArr = {a.class, byte[].class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 9085, clsArr, Void.TYPE).isSupported) {
            x0.p(x1);
        }
    }

    static /* synthetic */ void i(a x0, String x1) {
        Class[] clsArr = {a.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 9086, clsArr, Void.TYPE).isSupported) {
            x0.n(x1);
        }
    }

    public a(String ip, Socket socket) {
        this.c = ip;
        this.e = socket;
        this.d = socket.getPort();
        this.i = new byte[]{1};
    }

    public void r() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9078, new Class[0], Void.TYPE).isSupported) {
            this.h = false;
            o();
        }
    }

    private void q(long t) {
        Object[] objArr = {new Long(t)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9079, new Class[]{Long.TYPE}, Void.TYPE).isSupported) {
            try {
                Thread.sleep(t);
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
        }
    }

    private void n(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 9080, new Class[]{String.class}, Void.TYPE).isSupported) {
            a.b g2 = timber.log.a.g("LdsSocketClient");
            g2.a(this.c + ":[" + this.d + "]:" + msg, new Object[0]);
        }
    }

    private void o() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9081, new Class[0], Void.TYPE).isSupported) {
            n("client start read msg...");
            this.h = true;
            new Thread(new C0165a()).start();
        }
    }

    /* renamed from: com.leedarson.serviceimpl.tcp.socket.a$a  reason: collision with other inner class name */
    /* compiled from: LdsSocketClient */
    public class C0165a implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        C0165a() {
        }

        public void run() {
            byte[] buffer;
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9088, new Class[0], Void.TYPE).isSupported) {
                try {
                    if (a.this.e != null) {
                        a aVar = a.this;
                        InputStream unused = aVar.f = aVar.e.getInputStream();
                        a aVar2 = a.this;
                        OutputStream unused2 = aVar2.g = aVar2.e.getOutputStream();
                        while (a.this.h && a.this.f != null) {
                            if (a.this.m()) {
                                a aVar3 = a.this;
                                a.h(aVar3, aVar3.i);
                            }
                            int size = a.this.f.available();
                            if (size > 0) {
                                if (size < 10240) {
                                    buffer = new byte[size];
                                } else {
                                    buffer = new byte[Data.MAX_DATA_BYTES];
                                }
                                a.this.f.read(buffer);
                                a aVar4 = a.this;
                                a.i(aVar4, "rx:" + new String(buffer, StandardCharsets.UTF_8));
                                if (size > 0 && a.this.j != null) {
                                    a.this.j.a(a.this.c, a.this.d, buffer);
                                }
                                a.c(a.this, 10);
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean m() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9082, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        Socket socket = this.e;
        return socket != null && socket.isConnected();
    }

    public void s() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9083, new Class[0], Void.TYPE).isSupported) {
            this.h = false;
            if (m()) {
                try {
                    this.e.close();
                    this.e = null;
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
            InputStream inputStream = this.f;
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
            InputStream inputStream2 = this.f;
            if (inputStream2 != null) {
                try {
                    inputStream2.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
        }
    }

    public void setOnSocketStatusChangeListener(d listener) {
        this.j = listener;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x001d, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void p(byte[] r9) {
        /*
            r8 = this;
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r2 = 0
            r1[r2] = r9
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<byte[]> r0 = byte[].class
            r6[r2] = r0
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 9084(0x237c, float:1.273E-41)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x001d
            return
        L_0x001d:
            r0 = r8
            java.io.OutputStream r1 = r0.g
            if (r1 == 0) goto L_0x0053
            r1.write(r9)     // Catch:{ IOException -> 0x002b }
            java.io.OutputStream r1 = r0.g     // Catch:{ IOException -> 0x002b }
            r1.flush()     // Catch:{ IOException -> 0x002b }
            goto L_0x0053
        L_0x002b:
            r1 = move-exception
            r1.printStackTrace()
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "写入异常:"
            r2.append(r3)
            java.lang.String r3 = r1.toString()
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r0.n(r2)
            com.leedarson.serviceimpl.tcp.socket.d r2 = r0.j
            if (r2 == 0) goto L_0x0052
            java.lang.String r3 = r0.c
            int r4 = r0.d
            r2.b(r3, r4)
        L_0x0052:
            throw r1
        L_0x0053:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.tcp.socket.a.p(byte[]):void");
    }
}
