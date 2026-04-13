package com.leedarson.serviceimpl.udp.manager;

import android.net.Network;
import android.os.Handler;
import android.os.Message;
import androidx.work.Data;
import com.leedarson.base.http.observer.l;
import com.leedarson.secret.JNIUtil;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import org.json.JSONObject;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/* compiled from: UdpSocket */
public class b {
    private static final b a = new b();
    private static final String b = JNIUtil.getInstance().getStr5();
    public static ChangeQuickRedirect changeQuickRedirect;
    private final String c = "UdpSocket";
    /* access modifiers changed from: private */
    public DatagramChannel d;
    private Thread e;
    /* access modifiers changed from: private */
    public Selector f;
    /* access modifiers changed from: private */
    public Boolean g = false;
    d h;
    d i;
    private DatagramSocket j;
    private ExecutorService k = l.i(2, "udp-manager-sendAsyn", 4);
    private Network l;
    private DatagramSocket m;
    private DatagramPacket n;
    private boolean o = false;
    private boolean p = true;
    private byte[] q = new byte[Data.MAX_DATA_BYTES];
    private boolean r = false;
    Handler s = new c();

    /* compiled from: UdpSocket */
    public interface d {
        void a(String str, int i, String str2);
    }

    static /* synthetic */ void a(b x0, boolean z, String x2, int i2, String x4, byte[] x5, d x6) {
        Class<String> cls = String.class;
        boolean z2 = z;
        Object[] objArr = {x0, new Byte(z ? (byte) 1 : 0), x2, new Integer(i2), x4, x5, x6};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 9147, new Class[]{b.class, Boolean.TYPE, cls, Integer.TYPE, cls, byte[].class, d.class}, Void.TYPE).isSupported) {
            boolean x1 = z;
            int x3 = i2;
            x0.p(x1, x2, x3, x4, x5, x6);
        }
    }

    static /* synthetic */ void f(b x0, SelectionKey x1) {
        Class[] clsArr = {b.class, SelectionKey.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 9148, clsArr, Void.TYPE).isSupported) {
            x0.n(x1);
        }
    }

    private b() {
    }

    public static b k() {
        return a;
    }

    /* compiled from: UdpSocket */
    public class a implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ boolean c;
        final /* synthetic */ String d;
        final /* synthetic */ int f;
        final /* synthetic */ String q;
        final /* synthetic */ byte[] x;
        final /* synthetic */ d y;

        a(boolean z2, String str, int i, String str2, byte[] bArr, d dVar) {
            this.c = z2;
            this.d = str;
            this.f = i;
            this.q = str2;
            this.x = bArr;
            this.y = dVar;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9149, new Class[0], Void.TYPE).isSupported) {
                try {
                    b.a(b.this, this.c, this.d, this.f, this.q, this.x, this.y);
                } catch (IOException e) {
                }
            }
        }
    }

    public void o(boolean z, String str, int i2, String str2, byte[] bArr, d dVar) {
        Class<String> cls = String.class;
        boolean z2 = z;
        Object[] objArr = {new Byte(z ? (byte) 1 : 0), str, new Integer(i2), str2, bArr, dVar};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9138, new Class[]{Boolean.TYPE, cls, Integer.TYPE, cls, byte[].class, d.class}, Void.TYPE).isSupported) {
            String host = str;
            d responseListener = dVar;
            String message = str2;
            boolean isEncrypt = z;
            int port = i2;
            byte[] msgBytes = bArr;
            ExecutorService executorService = this.k;
            if (executorService != null && !executorService.isShutdown()) {
                this.k.execute(new a(isEncrypt, host, port, message, msgBytes, responseListener));
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x00a9 A[Catch:{ UnknownHostException -> 0x00b7 }] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x00b1 A[Catch:{ UnknownHostException -> 0x00b7 }] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00be A[SYNTHETIC, Splitter:B:32:0x00be] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00dd A[Catch:{ Exception -> 0x0103 }] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00ee A[Catch:{ Exception -> 0x0103 }] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00f0 A[Catch:{ Exception -> 0x0103 }] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00fd A[Catch:{ Exception -> 0x0103 }] */
    /* JADX WARNING: Removed duplicated region for block: B:57:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void p(boolean r15, java.lang.String r16, int r17, java.lang.String r18, byte[] r19, com.leedarson.serviceimpl.udp.manager.b.d r20) {
        /*
            r14 = this;
            java.lang.String r1 = "LdsUdp"
            java.lang.Class<java.lang.String> r0 = java.lang.String.class
            r2 = 6
            java.lang.Object[] r3 = new java.lang.Object[r2]
            java.lang.Byte r4 = new java.lang.Byte
            r10 = r15
            r4.<init>(r15)
            r11 = 0
            r3[r11] = r4
            r12 = 1
            r3[r12] = r16
            java.lang.Integer r4 = new java.lang.Integer
            r13 = r17
            r4.<init>(r13)
            r5 = 2
            r3[r5] = r4
            r4 = 3
            r3[r4] = r18
            r6 = 4
            r3[r6] = r19
            r7 = 5
            r3[r7] = r20
            com.meituan.robust.ChangeQuickRedirect r8 = changeQuickRedirect
            java.lang.Class[] r2 = new java.lang.Class[r2]
            java.lang.Class r9 = java.lang.Boolean.TYPE
            r2[r11] = r9
            r2[r12] = r0
            java.lang.Class r9 = java.lang.Integer.TYPE
            r2[r5] = r9
            r2[r4] = r0
            java.lang.Class<byte[]> r0 = byte[].class
            r2[r6] = r0
            java.lang.Class<com.leedarson.serviceimpl.udp.manager.b$d> r0 = com.leedarson.serviceimpl.udp.manager.b.d.class
            r2[r7] = r0
            java.lang.Class r9 = java.lang.Void.TYPE
            r6 = 0
            r7 = 9139(0x23b3, float:1.2806E-41)
            r4 = r14
            r5 = r8
            r8 = r2
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r3, r4, r5, r6, r7, r8, r9)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x004f
            return
        L_0x004f:
            r2 = r14
            r3 = r16
            r4 = r20
            r5 = r18
            r6 = r15
            r7 = r17
            r8 = r19
            r2.p = r12     // Catch:{ Exception -> 0x0103 }
            java.net.DatagramSocket r0 = r2.m     // Catch:{ Exception -> 0x0103 }
            if (r0 == 0) goto L_0x006b
            boolean r0 = r0.isClosed()     // Catch:{ Exception -> 0x0103 }
            if (r0 == 0) goto L_0x0068
            goto L_0x006b
        L_0x0068:
            r2.o = r11     // Catch:{ Exception -> 0x0103 }
            goto L_0x009e
        L_0x006b:
            timber.log.a$b r0 = timber.log.a.g(r1)     // Catch:{ Exception -> 0x0103 }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0103 }
            r9.<init>()     // Catch:{ Exception -> 0x0103 }
            java.lang.String r10 = "sendBroadcastAsyn: ============"
            r9.append(r10)     // Catch:{ Exception -> 0x0103 }
            android.net.Network r10 = r2.l     // Catch:{ Exception -> 0x0103 }
            r9.append(r10)     // Catch:{ Exception -> 0x0103 }
            java.lang.String r9 = r9.toString()     // Catch:{ Exception -> 0x0103 }
            java.lang.Object[] r10 = new java.lang.Object[r11]     // Catch:{ Exception -> 0x0103 }
            r0.h(r9, r10)     // Catch:{ Exception -> 0x0103 }
            java.net.DatagramSocket r0 = new java.net.DatagramSocket     // Catch:{ Exception -> 0x0103 }
            r0.<init>()     // Catch:{ Exception -> 0x0103 }
            r2.m = r0     // Catch:{ Exception -> 0x0103 }
            r2.o = r12     // Catch:{ Exception -> 0x0103 }
            android.net.Network r9 = r2.l     // Catch:{ Exception -> 0x0103 }
            if (r9 == 0) goto L_0x009e
            r9.bindSocket(r0)     // Catch:{ Exception -> 0x0098 }
            goto L_0x009e
        L_0x0098:
            r0 = move-exception
            r9 = r0
            r0 = r9
            r0.printStackTrace()     // Catch:{ Exception -> 0x0103 }
        L_0x009e:
            java.net.InetAddress r0 = java.net.InetAddress.getLocalHost()     // Catch:{ Exception -> 0x0103 }
            r9 = r0
            boolean r0 = android.text.TextUtils.isEmpty(r3)     // Catch:{ UnknownHostException -> 0x00b7 }
            if (r0 == 0) goto L_0x00b1
            java.lang.String r0 = "255.255.255.255"
            java.net.InetAddress r0 = java.net.InetAddress.getByName(r0)     // Catch:{ UnknownHostException -> 0x00b7 }
            r9 = r0
            goto L_0x00b6
        L_0x00b1:
            java.net.InetAddress r0 = r2.j(r3)     // Catch:{ UnknownHostException -> 0x00b7 }
            r9 = r0
        L_0x00b6:
            goto L_0x00bb
        L_0x00b7:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ Exception -> 0x0103 }
        L_0x00bb:
            r10 = 0
            if (r6 == 0) goto L_0x00dd
            boolean r0 = android.text.TextUtils.isEmpty(r5)     // Catch:{ Exception -> 0x00d8 }
            if (r0 == 0) goto L_0x00cc
            java.lang.String r0 = b     // Catch:{ Exception -> 0x00d8 }
            byte[] r0 = com.leedarson.serviceinterface.prefs.aes.LDSAESUtils.encrypt((java.lang.String) r0, (byte[]) r8)     // Catch:{ Exception -> 0x00d8 }
            r10 = r0
            goto L_0x00dc
        L_0x00cc:
            java.lang.String r0 = b     // Catch:{ Exception -> 0x00d8 }
            byte[] r13 = r5.getBytes()     // Catch:{ Exception -> 0x00d8 }
            byte[] r0 = com.leedarson.serviceinterface.prefs.aes.LDSAESUtils.encrypt((java.lang.String) r0, (byte[]) r13)     // Catch:{ Exception -> 0x00d8 }
            r10 = r0
            goto L_0x00dc
        L_0x00d8:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ Exception -> 0x0103 }
        L_0x00dc:
            goto L_0x00ea
        L_0x00dd:
            boolean r0 = android.text.TextUtils.isEmpty(r5)     // Catch:{ Exception -> 0x0103 }
            if (r0 == 0) goto L_0x00e5
            r10 = r8
            goto L_0x00ea
        L_0x00e5:
            byte[] r0 = r5.getBytes()     // Catch:{ Exception -> 0x0103 }
            r10 = r0
        L_0x00ea:
            java.net.DatagramPacket r0 = new java.net.DatagramPacket     // Catch:{ Exception -> 0x0103 }
            if (r10 == 0) goto L_0x00f0
            int r13 = r10.length     // Catch:{ Exception -> 0x0103 }
            goto L_0x00f1
        L_0x00f0:
            r13 = r11
        L_0x00f1:
            r0.<init>(r10, r13, r9, r7)     // Catch:{ Exception -> 0x0103 }
            java.net.DatagramSocket r13 = r2.m     // Catch:{ Exception -> 0x0103 }
            r13.send(r0)     // Catch:{ Exception -> 0x0103 }
            boolean r13 = r2.r     // Catch:{ Exception -> 0x0103 }
            if (r13 != 0) goto L_0x0102
            r2.r = r12     // Catch:{ Exception -> 0x0103 }
            r2.l(r6, r3, r7, r4)     // Catch:{ Exception -> 0x0103 }
        L_0x0102:
            goto L_0x0122
        L_0x0103:
            r0 = move-exception
            timber.log.a$b r1 = timber.log.a.g(r1)
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r10 = "UDP.Error===========================sendBroadcastAsyn:"
            r9.append(r10)
            java.lang.String r10 = r0.toString()
            r9.append(r10)
            java.lang.String r9 = r9.toString()
            java.lang.Object[] r10 = new java.lang.Object[r11]
            r1.c(r9, r10)
        L_0x0122:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.udp.manager.b.p(boolean, java.lang.String, int, java.lang.String, byte[], com.leedarson.serviceimpl.udp.manager.b$d):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:67:0x016e, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x016f, code lost:
        r7 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x0174, code lost:
        r7 = false;
        r2.r = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x0178, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x0179, code lost:
        r7 = r10;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x0173 A[ExcHandler: Exception (e java.lang.Exception), Splitter:B:11:0x006b] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void l(boolean r17, java.lang.String r18, int r19, com.leedarson.serviceimpl.udp.manager.b.d r20) {
        /*
            r16 = this;
            java.lang.String r1 = "payload"
            r0 = 4
            java.lang.Object[] r2 = new java.lang.Object[r0]
            java.lang.Byte r3 = new java.lang.Byte
            r9 = r17
            r3.<init>(r9)
            r10 = 0
            r2[r10] = r3
            r11 = 1
            r2[r11] = r18
            java.lang.Integer r3 = new java.lang.Integer
            r12 = r19
            r3.<init>(r12)
            r4 = 2
            r2[r4] = r3
            r3 = 3
            r2[r3] = r20
            com.meituan.robust.ChangeQuickRedirect r5 = changeQuickRedirect
            java.lang.Class[] r7 = new java.lang.Class[r0]
            java.lang.Class r0 = java.lang.Boolean.TYPE
            r7[r10] = r0
            java.lang.Class<java.lang.String> r0 = java.lang.String.class
            r7[r11] = r0
            java.lang.Class r0 = java.lang.Integer.TYPE
            r7[r4] = r0
            java.lang.Class<com.leedarson.serviceimpl.udp.manager.b$d> r0 = com.leedarson.serviceimpl.udp.manager.b.d.class
            r7[r3] = r0
            java.lang.Class r8 = java.lang.Void.TYPE
            r0 = 0
            r6 = 9140(0x23b4, float:1.2808E-41)
            r3 = r16
            r4 = r5
            r5 = r0
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r2, r3, r4, r5, r6, r7, r8)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0045
            return
        L_0x0045:
            r2 = r16
            r3 = r18
            r4 = r20
            r5 = r17
            r6 = r19
        L_0x004f:
            boolean r0 = r2.r
            if (r0 == 0) goto L_0x0183
            java.net.DatagramSocket r0 = r2.m
            if (r0 == 0) goto L_0x0183
            boolean r0 = r0.isClosed()
            if (r0 != 0) goto L_0x0183
            r0 = 0
            r2.n = r0
            java.net.DatagramPacket r0 = new java.net.DatagramPacket
            byte[] r7 = r2.q
            r8 = 10240(0x2800, float:1.4349E-41)
            r0.<init>(r7, r8)
            r2.n = r0
            java.net.DatagramSocket r7 = r2.m     // Catch:{ InterruptedIOException -> 0x0178, Exception -> 0x0173 }
            r7.receive(r0)     // Catch:{ InterruptedIOException -> 0x0178, Exception -> 0x0173 }
            java.net.DatagramPacket r0 = r2.n     // Catch:{ InterruptedIOException -> 0x0178, Exception -> 0x0173 }
            int r0 = r0.getLength()     // Catch:{ InterruptedIOException -> 0x0178, Exception -> 0x0173 }
            byte[] r0 = new byte[r0]     // Catch:{ InterruptedIOException -> 0x0178, Exception -> 0x0173 }
            r7 = r0
            java.net.DatagramPacket r0 = r2.n     // Catch:{ InterruptedIOException -> 0x0178, Exception -> 0x0173 }
            byte[] r0 = r0.getData()     // Catch:{ InterruptedIOException -> 0x0178, Exception -> 0x0173 }
            java.net.DatagramPacket r9 = r2.n     // Catch:{ InterruptedIOException -> 0x0178, Exception -> 0x0173 }
            int r9 = r9.getLength()     // Catch:{ InterruptedIOException -> 0x0178, Exception -> 0x0173 }
            java.lang.System.arraycopy(r0, r10, r7, r10, r9)     // Catch:{ InterruptedIOException -> 0x0178, Exception -> 0x0173 }
            r9 = 0
            r12 = 1
            java.lang.String r0 = new java.lang.String     // Catch:{ Exception -> 0x00a5 }
            r0.<init>(r7)     // Catch:{ Exception -> 0x00a5 }
            java.lang.String r0 = r0.trim()     // Catch:{ Exception -> 0x00a5 }
            boolean r13 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Exception -> 0x00a5 }
            if (r13 != 0) goto L_0x00a3
            java.lang.String r13 = "{"
            boolean r13 = r0.startsWith(r13)     // Catch:{ Exception -> 0x00a5 }
            if (r13 == 0) goto L_0x00a3
            r12 = 0
            goto L_0x00a4
        L_0x00a3:
            r12 = 1
        L_0x00a4:
            goto L_0x00be
        L_0x00a5:
            r0 = move-exception
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ InterruptedIOException -> 0x0178, Exception -> 0x0173 }
            r13.<init>()     // Catch:{ InterruptedIOException -> 0x0178, Exception -> 0x0173 }
            java.lang.String r14 = "UDPSocket.Exception="
            r13.append(r14)     // Catch:{ InterruptedIOException -> 0x0178, Exception -> 0x0173 }
            java.lang.String r14 = r0.toString()     // Catch:{ InterruptedIOException -> 0x0178, Exception -> 0x0173 }
            r13.append(r14)     // Catch:{ InterruptedIOException -> 0x0178, Exception -> 0x0173 }
            java.lang.String r13 = r13.toString()     // Catch:{ InterruptedIOException -> 0x0178, Exception -> 0x0173 }
            com.leedarson.base.logger.a.e(r2, r13)     // Catch:{ InterruptedIOException -> 0x0178, Exception -> 0x0173 }
        L_0x00be:
            if (r12 == 0) goto L_0x00ee
            int r0 = r7.length     // Catch:{ Exception -> 0x00e9 }
            int r0 = r0 % 32
            r13 = 16
            if (r0 != r13) goto L_0x00e1
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00e9 }
            r0.<init>()     // Catch:{ Exception -> 0x00e9 }
            java.lang.String r13 = com.leedarson.serviceimpl.udp.manager.c.a(r7)     // Catch:{ Exception -> 0x00e9 }
            r0.append(r13)     // Catch:{ Exception -> 0x00e9 }
            java.lang.String r13 = "6cd95c0f8a25084d506e8cd8af85232e"
            r0.append(r13)     // Catch:{ Exception -> 0x00e9 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x00e9 }
            byte[] r0 = com.leedarson.serviceimpl.udp.manager.c.b(r0)     // Catch:{ Exception -> 0x00e9 }
            r7 = r0
        L_0x00e1:
            java.lang.String r0 = b     // Catch:{ Exception -> 0x00e9 }
            byte[] r0 = com.leedarson.serviceinterface.prefs.aes.LDSAESUtils.decrypt((java.lang.String) r0, (byte[]) r7)     // Catch:{ Exception -> 0x00e9 }
            r9 = r0
            goto L_0x00ed
        L_0x00e9:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ InterruptedIOException -> 0x0178, Exception -> 0x0173 }
        L_0x00ed:
            goto L_0x00ef
        L_0x00ee:
            r9 = r7
        L_0x00ef:
            if (r9 == 0) goto L_0x0171
            java.lang.String r0 = new java.lang.String     // Catch:{ InterruptedIOException -> 0x0178, Exception -> 0x0173 }
            r0.<init>(r9)     // Catch:{ InterruptedIOException -> 0x0178, Exception -> 0x0173 }
            java.lang.String r0 = r0.trim()     // Catch:{ InterruptedIOException -> 0x0178, Exception -> 0x0173 }
            r13 = r0
            java.net.DatagramPacket r0 = r2.n     // Catch:{ InterruptedIOException -> 0x0178, Exception -> 0x0173 }
            java.net.InetAddress r0 = r0.getAddress()     // Catch:{ InterruptedIOException -> 0x0178, Exception -> 0x0173 }
            java.lang.String r0 = r0.getHostAddress()     // Catch:{ InterruptedIOException -> 0x0178, Exception -> 0x0173 }
            r14 = r0
            java.net.DatagramPacket r0 = r2.n     // Catch:{ InterruptedIOException -> 0x0178, Exception -> 0x0173 }
            int r0 = r0.getPort()     // Catch:{ InterruptedIOException -> 0x0178, Exception -> 0x0173 }
            r15 = r0
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ Exception -> 0x0137 }
            r0.<init>((java.lang.String) r13)     // Catch:{ Exception -> 0x0137 }
            java.lang.String r8 = "isEncrypt"
            if (r12 == 0) goto L_0x011a
            r0.put((java.lang.String) r8, (int) r11)     // Catch:{ Exception -> 0x0137 }
            goto L_0x011d
        L_0x011a:
            r0.put((java.lang.String) r8, (int) r10)     // Catch:{ Exception -> 0x0137 }
        L_0x011d:
            boolean r8 = r0.has(r1)     // Catch:{ Exception -> 0x0137 }
            if (r8 == 0) goto L_0x0131
            org.json.JSONObject r8 = r0.getJSONObject(r1)     // Catch:{ Exception -> 0x0137 }
            java.lang.String r11 = "ip"
            r8.put((java.lang.String) r11, (java.lang.Object) r14)     // Catch:{ Exception -> 0x0137 }
            java.lang.String r11 = "port"
            r8.put((java.lang.String) r11, (int) r15)     // Catch:{ Exception -> 0x0137 }
        L_0x0131:
            java.lang.String r8 = r0.toString()     // Catch:{ Exception -> 0x0137 }
            r13 = r8
            goto L_0x015e
        L_0x0137:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ InterruptedIOException -> 0x0178, Exception -> 0x0173 }
            java.lang.String r8 = "LdsUdp"
            timber.log.a$b r8 = timber.log.a.g(r8)     // Catch:{ InterruptedIOException -> 0x0178, Exception -> 0x0173 }
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ InterruptedIOException -> 0x0178, Exception -> 0x0173 }
            r11.<init>()     // Catch:{ InterruptedIOException -> 0x0178, Exception -> 0x0173 }
            java.lang.String r10 = "======JSONObject parse error:"
            r11.append(r10)     // Catch:{ InterruptedIOException -> 0x016e, Exception -> 0x0173 }
            java.lang.String r10 = r0.toString()     // Catch:{ InterruptedIOException -> 0x016e, Exception -> 0x0173 }
            r11.append(r10)     // Catch:{ InterruptedIOException -> 0x016e, Exception -> 0x0173 }
            java.lang.String r10 = r11.toString()     // Catch:{ InterruptedIOException -> 0x016e, Exception -> 0x0173 }
            r18 = r0
            r11 = 0
            java.lang.Object[] r0 = new java.lang.Object[r11]     // Catch:{ InterruptedIOException -> 0x016b, Exception -> 0x0173 }
            r8.h(r10, r0)     // Catch:{ InterruptedIOException -> 0x016e, Exception -> 0x0173 }
        L_0x015e:
            java.net.DatagramPacket r0 = r2.n     // Catch:{ InterruptedIOException -> 0x016e, Exception -> 0x0173 }
            r8 = 10240(0x2800, float:1.4349E-41)
            r0.setLength(r8)     // Catch:{ InterruptedIOException -> 0x016e, Exception -> 0x0173 }
            if (r4 == 0) goto L_0x0171
            r4.a(r3, r6, r13)     // Catch:{ InterruptedIOException -> 0x016e, Exception -> 0x0173 }
            goto L_0x0171
        L_0x016b:
            r0 = move-exception
            r7 = r11
            goto L_0x017a
        L_0x016e:
            r0 = move-exception
            r7 = 0
            goto L_0x017a
        L_0x0171:
            r10 = 0
            goto L_0x0180
        L_0x0173:
            r0 = move-exception
            r7 = 0
            r2.r = r7
            goto L_0x017f
        L_0x0178:
            r0 = move-exception
            r7 = r10
        L_0x017a:
            r0.printStackTrace()
            r2.r = r7
        L_0x017f:
            r10 = r7
        L_0x0180:
            r11 = 1
            goto L_0x004f
        L_0x0183:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.udp.manager.b.l(boolean, java.lang.String, int, com.leedarson.serviceimpl.udp.manager.b$d):void");
    }

    public void h(int port, d listener) {
        Object[] objArr = {new Integer(port), listener};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9141, new Class[]{Integer.TYPE, d.class}, Void.TYPE).isSupported) {
            this.i = listener;
            if (this.d != null) {
                i();
            }
            Thread thread = new Thread(new C0167b(port));
            this.e = thread;
            thread.start();
        }
    }

    /* renamed from: com.leedarson.serviceimpl.udp.manager.b$b  reason: collision with other inner class name */
    /* compiled from: UdpSocket */
    public class C0167b implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int c;

        C0167b(int i) {
            this.c = i;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9150, new Class[0], Void.TYPE).isSupported) {
                try {
                    Selector unused = b.this.f = Selector.open();
                    DatagramChannel unused2 = b.this.d = DatagramChannel.open();
                    b.this.d.configureBlocking(false);
                    b.this.d.socket().bind(new InetSocketAddress(this.c));
                    SelectionKey clientKey = b.this.d.register(b.this.f, 1);
                    while (b.this.f.select() > 0) {
                        timber.log.a.g("LdsUdp").h("有新channel加入", new Object[0]);
                        Iterator iterator = b.this.f.selectedKeys().iterator();
                        while (iterator.hasNext()) {
                            SelectionKey key = null;
                            try {
                                SelectionKey key2 = iterator.next();
                                iterator.remove();
                                if (key2.isReadable()) {
                                    b.f(b.this, key2);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                                if (key != null) {
                                    try {
                                        key.cancel();
                                        key.channel().close();
                                    } catch (ClosedChannelException e2) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                    clientKey.cancel();
                    try {
                        b.this.d.close();
                        b.this.f.close();
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                } catch (Exception e4) {
                    e4.printStackTrace();
                    b.this.d.close();
                    b.this.f.close();
                } catch (Throwable th) {
                    try {
                        b.this.d.close();
                        b.this.f.close();
                    } catch (Exception e5) {
                        e5.printStackTrace();
                    }
                    throw th;
                }
            }
        }
    }

    public boolean i() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9142, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        this.g = true;
        DatagramChannel datagramChannel = this.d;
        if (datagramChannel != null) {
            try {
                datagramChannel.socket().close();
                this.d.close();
                this.f.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        this.g = true;
        this.h = null;
        this.i = null;
        DatagramSocket datagramSocket = this.j;
        if (datagramSocket != null) {
            datagramSocket.close();
        }
        this.e = null;
        this.p = false;
        DatagramSocket datagramSocket2 = this.m;
        if (datagramSocket2 != null) {
            datagramSocket2.close();
            this.m = null;
        }
        this.n = null;
        return true;
    }

    private synchronized void n(SelectionKey key) {
        if (!PatchProxy.proxy(new Object[]{key}, this, changeQuickRedirect, false, 9143, new Class[]{SelectionKey.class}, Void.TYPE).isSupported) {
            if (key != null) {
                String content = "";
                ByteBuffer buf = ByteBuffer.allocate(1024);
                buf.clear();
                SocketAddress address = ((DatagramChannel) key.channel()).receive(buf);
                buf.flip();
                while (buf.hasRemaining()) {
                    buf.get(new byte[buf.limit()]);
                    content = content + new String(buf.array());
                }
                buf.clear();
                timber.log.a.g("LdsUdp").h("接收：" + content.trim(), new Object[0]);
                try {
                    String[] strs = address.toString().replace("/", "").split(":");
                    String dev_ip = strs[0];
                    int dev_port = Integer.parseInt(strs[1]);
                    JSONObject jsonObject1 = new JSONObject(content.trim());
                    JSONObject paloadObject = jsonObject1.getJSONObject("payload");
                    paloadObject.put(IjkMediaPlayer.OnNativeInvokeListener.ARG_IP, (Object) dev_ip);
                    paloadObject.put(IjkMediaPlayer.OnNativeInvokeListener.ARG_PORT, dev_port);
                    String sendMsg = jsonObject1.toString();
                    if (sendMsg != null) {
                        Message msg = new Message();
                        msg.what = 2;
                        msg.obj = sendMsg;
                        Handler handler = this.s;
                        if (handler != null) {
                            handler.sendMessage(msg);
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            } else {
                return;
            }
        } else {
            return;
        }
        return;
    }

    /* compiled from: UdpSocket */
    public class c extends Handler {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public void handleMessage(Message msg) {
            if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 9151, new Class[]{Message.class}, Void.TYPE).isSupported) {
                switch (msg.what) {
                    case 0:
                        Boolean unused = b.this.g = true;
                        return;
                    case 1:
                        b.this.h.a((String) null, 0, (String) msg.obj);
                        return;
                    case 2:
                        b.this.i.a((String) null, 0, (String) msg.obj);
                        return;
                    default:
                        return;
                }
            }
        }
    }

    private InetAddress j(String ip) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{ip}, this, changeQuickRedirect, false, 9144, new Class[]{String.class}, InetAddress.class);
        if (proxy.isSupported) {
            return (InetAddress) proxy.result;
        }
        return InetAddress.getByName(ip);
    }

    public void q(Network network) {
        if (!PatchProxy.proxy(new Object[]{network}, this, changeQuickRedirect, false, 9145, new Class[]{Network.class}, Void.TYPE).isSupported) {
            this.l = network;
            try {
                this.p = false;
                DatagramSocket datagramSocket = this.m;
                if (datagramSocket != null) {
                    datagramSocket.close();
                    this.m = null;
                }
                this.n = null;
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void m() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9146, new Class[0], Void.TYPE).isSupported) {
            this.l = null;
            try {
                this.p = false;
                DatagramSocket datagramSocket = this.m;
                if (datagramSocket != null) {
                    datagramSocket.close();
                    this.m = null;
                }
                this.n = null;
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}
