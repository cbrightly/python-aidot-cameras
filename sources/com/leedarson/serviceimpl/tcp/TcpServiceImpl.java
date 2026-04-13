package com.leedarson.serviceimpl.tcp;

import android.content.Context;
import com.leedarson.bean.Constants;
import com.leedarson.serviceimpl.tcp.beans.TCPChannelMapBean;
import com.leedarson.serviceimpl.tcp.beans.TCPSendTaskBean;
import com.leedarson.serviceimpl.tcp.manager.INettyManager;
import com.leedarson.serviceimpl.tcp.manager.NettyManager;
import com.leedarson.serviceinterface.DatabaseService;
import com.leedarson.serviceinterface.TcpService;
import com.leedarson.serviceinterface.event.JsBridgeCallbackEvent;
import com.leedarson.serviceinterface.event.JsCallH5ByNativeEvent;
import com.leedarson.serviceinterface.event.SocketStatusChangeEvent;
import com.leedarson.serviceinterface.listener.OnGetRecordListener;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.leedarson.serviceinterface.utils.PlayBackCacheUtils;
import com.leedarson.tcp.ipc.g;
import com.leedarson.tcp.ipc.h;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import io.reactivex.l;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.json.JSONObject;
import timber.log.a;
import tv.danmaku.ijk.media.player.IjkMediaCodecInfo;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

public class TcpServiceImpl implements TcpService {
    public static ChangeQuickRedirect changeQuickRedirect;
    private HashSet<String> a = new HashSet<>();
    Context b;
    private short c;
    private short d;
    private int e;
    /* access modifiers changed from: private */
    public boolean f = false;
    /* access modifiers changed from: private */
    public long g = 0;
    /* access modifiers changed from: private */
    public long h = 0;
    /* access modifiers changed from: private */
    public long i = 0;
    /* access modifiers changed from: private */
    public String j = "";
    private Future k;
    private ExecutorService l = Executors.newSingleThreadExecutor();
    private ConcurrentHashMap<String, TCPChannelMapBean> m = new ConcurrentHashMap<>();
    /* access modifiers changed from: private */
    public ConcurrentHashMap<String, ConcurrentLinkedDeque> n = new ConcurrentHashMap<>();
    private volatile io.reactivex.disposables.b o;
    /* access modifiers changed from: private */
    public List<byte[]> p = new ArrayList();

    static /* synthetic */ long q(TcpServiceImpl x0, long x1) {
        long j2 = x0.g + x1;
        x0.g = j2;
        return j2;
    }

    static /* synthetic */ void s(TcpServiceImpl x0, String x1) {
        Class[] clsArr = {TcpServiceImpl.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 8972, clsArr, Void.TYPE).isSupported) {
            x0.h(x1);
        }
    }

    static /* synthetic */ void u(TcpServiceImpl x0, ConcurrentLinkedDeque x1) {
        Class[] clsArr = {TcpServiceImpl.class, ConcurrentLinkedDeque.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 8973, clsArr, Void.TYPE).isSupported) {
            x0.D(x1);
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0099, code lost:
        if (r3.equals("disconnect") != false) goto L_0x00bb;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void handleData(java.lang.String r34, java.lang.String r35, java.lang.String r36) {
        /*
            r33 = this;
            java.lang.Class<java.lang.String> r0 = java.lang.String.class
            r1 = 3
            java.lang.Object[] r2 = new java.lang.Object[r1]
            r9 = 0
            r2[r9] = r34
            r10 = 1
            r2[r10] = r35
            r11 = 2
            r2[r11] = r36
            com.meituan.robust.ChangeQuickRedirect r4 = changeQuickRedirect
            java.lang.Class[] r7 = new java.lang.Class[r1]
            r7[r9] = r0
            r7[r10] = r0
            r7[r11] = r0
            java.lang.Class r8 = java.lang.Void.TYPE
            r5 = 0
            r6 = 8949(0x22f5, float:1.254E-41)
            r3 = r33
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r2, r3, r4, r5, r6, r7, r8)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0028
            return
        L_0x0028:
            r2 = r33
            r3 = r35
            r4 = r34
            r5 = r36
            java.lang.String r0 = "TcpServiceImpl"
            timber.log.a$b r0 = timber.log.a.g(r0)
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "handleData: "
            r6.append(r7)
            r6.append(r3)
            java.lang.String r7 = " data:"
            r6.append(r7)
            r6.append(r5)
            java.lang.String r6 = r6.toString()
            java.lang.Object[] r7 = new java.lang.Object[r9]
            r0.h(r6, r7)
            com.google.gson.internal.LinkedTreeMap r6 = com.leedarson.serviceimpl.tcp.manager.a.c(r5)
            int r0 = r3.hashCode()
            switch(r0) {
                case -775651598: goto L_0x00b0;
                case 3526536: goto L_0x00a6;
                case 126626304: goto L_0x009c;
                case 530405532: goto L_0x0093;
                case 761106247: goto L_0x0088;
                case 951351530: goto L_0x007e;
                case 990157655: goto L_0x0074;
                case 1979923348: goto L_0x006a;
                case 2038372806: goto L_0x0060;
                default: goto L_0x005f;
            }
        L_0x005f:
            goto L_0x00ba
        L_0x0060:
            java.lang.String r0 = "getConnectStatus"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x005f
            r1 = r11
            goto L_0x00bb
        L_0x006a:
            java.lang.String r0 = "sendipc"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x005f
            r1 = 7
            goto L_0x00bb
        L_0x0074:
            java.lang.String r0 = "reconnect"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x005f
            r1 = r10
            goto L_0x00bb
        L_0x007e:
            java.lang.String r0 = "connect"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x005f
            r1 = r9
            goto L_0x00bb
        L_0x0088:
            java.lang.String r0 = "getRecord"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x005f
            r1 = 8
            goto L_0x00bb
        L_0x0093:
            java.lang.String r0 = "disconnect"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x005f
            goto L_0x00bb
        L_0x009c:
            java.lang.String r0 = "disconnectipc"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x005f
            r1 = 6
            goto L_0x00bb
        L_0x00a6:
            java.lang.String r0 = "send"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x005f
            r1 = 4
            goto L_0x00bb
        L_0x00b0:
            java.lang.String r0 = "connectipc"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x005f
            r1 = 5
            goto L_0x00bb
        L_0x00ba:
            r1 = -1
        L_0x00bb:
            java.lang.String r0 = "message"
            java.lang.String r12 = "ip"
            java.lang.String r13 = "isTLS"
            java.lang.String r15 = "aesKey"
            java.lang.String r14 = "heartbeat"
            java.lang.String r8 = "port"
            java.lang.String r11 = "commandParam"
            java.lang.String r7 = "cmdParam"
            java.lang.String r9 = "subCommand"
            java.lang.String r10 = "subCmd"
            r23 = r3
            java.lang.String r3 = "command"
            r36 = r13
            java.lang.String r13 = "cmd"
            r19 = r15
            java.lang.String r15 = "sessionId"
            java.lang.String r20 = ""
            switch(r1) {
                case 0: goto L_0x058c;
                case 1: goto L_0x058c;
                case 2: goto L_0x0520;
                case 3: goto L_0x0460;
                case 4: goto L_0x0428;
                case 5: goto L_0x02e6;
                case 6: goto L_0x0293;
                case 7: goto L_0x018d;
                case 8: goto L_0x00e5;
                default: goto L_0x00e0;
            }
        L_0x00e0:
            r9 = r4
            r24 = r5
            goto L_0x0697
        L_0x00e5:
            r0 = r20
            if (r6 == 0) goto L_0x0173
            boolean r1 = r6.containsKey(r13)     // Catch:{ Exception -> 0x0184 }
            if (r1 == 0) goto L_0x00f9
            java.lang.Object r1 = r6.get(r13)     // Catch:{ Exception -> 0x0184 }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x0184 }
            r0 = r1
            goto L_0x0108
        L_0x00f9:
            boolean r1 = r6.containsKey(r3)     // Catch:{ Exception -> 0x0184 }
            if (r1 == 0) goto L_0x0108
            java.lang.Object r1 = r6.get(r3)     // Catch:{ Exception -> 0x0184 }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x0184 }
            r0 = r1
        L_0x0108:
            r1 = r20
            boolean r3 = r6.containsKey(r10)     // Catch:{ Exception -> 0x0184 }
            if (r3 == 0) goto L_0x011a
            java.lang.Object r3 = r6.get(r10)     // Catch:{ Exception -> 0x0184 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x0184 }
            r1 = r3
            goto L_0x0129
        L_0x011a:
            boolean r3 = r6.containsKey(r9)     // Catch:{ Exception -> 0x0184 }
            if (r3 == 0) goto L_0x0129
            java.lang.Object r3 = r6.get(r9)     // Catch:{ Exception -> 0x0184 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x0184 }
            r1 = r3
        L_0x0129:
            r3 = r20
            boolean r8 = r6.containsKey(r7)     // Catch:{ Exception -> 0x0184 }
            if (r8 == 0) goto L_0x013b
            java.lang.Object r7 = r6.get(r7)     // Catch:{ Exception -> 0x0184 }
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x0184 }
            r3 = r7
            goto L_0x014a
        L_0x013b:
            boolean r7 = r6.containsKey(r11)     // Catch:{ Exception -> 0x0184 }
            if (r7 == 0) goto L_0x014a
            java.lang.Object r7 = r6.get(r11)     // Catch:{ Exception -> 0x0184 }
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x0184 }
            r3 = r7
        L_0x014a:
            byte[] r7 = com.leedarson.serviceimpl.tcp.manager.a.f(r0)     // Catch:{ Exception -> 0x0184 }
            short r7 = com.leedarson.serviceimpl.tcp.manager.a.b(r7)     // Catch:{ Exception -> 0x0184 }
            r2.c = r7     // Catch:{ Exception -> 0x0184 }
            byte[] r7 = com.leedarson.serviceimpl.tcp.manager.a.f(r1)     // Catch:{ Exception -> 0x0184 }
            short r7 = com.leedarson.serviceimpl.tcp.manager.a.b(r7)     // Catch:{ Exception -> 0x0184 }
            r2.d = r7     // Catch:{ Exception -> 0x0184 }
            byte[] r7 = com.leedarson.serviceimpl.tcp.manager.a.f(r3)     // Catch:{ Exception -> 0x0184 }
            int r7 = com.leedarson.serviceimpl.tcp.manager.a.a(r7)     // Catch:{ Exception -> 0x0184 }
            r2.e = r7     // Catch:{ Exception -> 0x0184 }
            java.lang.String r7 = "0109"
            boolean r7 = r0.equals(r7)     // Catch:{ Exception -> 0x0184 }
            if (r7 == 0) goto L_0x0173
            r7 = 1
            r2.f = r7     // Catch:{ Exception -> 0x0184 }
        L_0x0173:
            org.greenrobot.eventbus.c r1 = org.greenrobot.eventbus.c.c()     // Catch:{ Exception -> 0x0184 }
            com.leedarson.serviceinterface.event.GetRecordEvent r3 = new com.leedarson.serviceinterface.event.GetRecordEvent     // Catch:{ Exception -> 0x0184 }
            r3.<init>(r5)     // Catch:{ Exception -> 0x0184 }
            r1.l(r3)     // Catch:{ Exception -> 0x0184 }
            r9 = r4
            r24 = r5
            goto L_0x0697
        L_0x0184:
            r0 = move-exception
            r0.printStackTrace()
            r9 = r4
            r24 = r5
            goto L_0x0697
        L_0x018d:
            if (r6 == 0) goto L_0x028e
            java.lang.Object r0 = r6.get(r0)
            java.lang.String r1 = com.leedarson.serviceimpl.tcp.manager.a.e(r0)
            java.lang.String r8 = ""
            r12 = 0
            r14 = 0
            r18 = 0
            boolean r0 = r6.containsKey(r13)     // Catch:{ Exception -> 0x0213 }
            if (r0 == 0) goto L_0x01ad
            java.lang.Object r0 = r6.get(r13)     // Catch:{ Exception -> 0x0213 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0213 }
            r8 = r0
            goto L_0x01bc
        L_0x01ad:
            boolean r0 = r6.containsKey(r3)     // Catch:{ Exception -> 0x0213 }
            if (r0 == 0) goto L_0x01bc
            java.lang.Object r0 = r6.get(r3)     // Catch:{ Exception -> 0x0213 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0213 }
            r8 = r0
        L_0x01bc:
            r12 = r20
            boolean r0 = r6.containsKey(r10)     // Catch:{ Exception -> 0x0213 }
            if (r0 == 0) goto L_0x01ce
            java.lang.Object r0 = r6.get(r10)     // Catch:{ Exception -> 0x0213 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0213 }
            r12 = r0
            goto L_0x01dd
        L_0x01ce:
            boolean r0 = r6.containsKey(r9)     // Catch:{ Exception -> 0x0213 }
            if (r0 == 0) goto L_0x01dd
            java.lang.Object r0 = r6.get(r9)     // Catch:{ Exception -> 0x0213 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0213 }
            r12 = r0
        L_0x01dd:
            r14 = r20
            boolean r0 = r6.containsKey(r7)     // Catch:{ Exception -> 0x0213 }
            if (r0 == 0) goto L_0x01ef
            java.lang.Object r0 = r6.get(r7)     // Catch:{ Exception -> 0x0213 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0213 }
            r14 = r0
            goto L_0x01fe
        L_0x01ef:
            boolean r0 = r6.containsKey(r11)     // Catch:{ Exception -> 0x0213 }
            if (r0 == 0) goto L_0x01fe
            java.lang.Object r0 = r6.get(r11)     // Catch:{ Exception -> 0x0213 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0213 }
            r14 = r0
        L_0x01fe:
            r18 = r20
            boolean r0 = r6.containsKey(r15)     // Catch:{ Exception -> 0x0213 }
            if (r0 == 0) goto L_0x0210
            java.lang.Object r0 = r6.get(r15)     // Catch:{ Exception -> 0x0213 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0213 }
            r18 = r0
        L_0x0210:
            r3 = r18
            goto L_0x0219
        L_0x0213:
            r0 = move-exception
            r0.printStackTrace()
            r3 = r18
        L_0x0219:
            byte[] r0 = com.leedarson.serviceimpl.tcp.manager.a.f(r8)
            short r7 = com.leedarson.serviceimpl.tcp.manager.a.b(r0)
            byte[] r0 = com.leedarson.serviceimpl.tcp.manager.a.f(r12)
            short r9 = com.leedarson.serviceimpl.tcp.manager.a.b(r0)
            byte[] r0 = com.leedarson.serviceimpl.tcp.manager.a.f(r14)
            int r10 = com.leedarson.serviceimpl.tcp.manager.a.a(r0)
            com.leedarson.tcp.ipc.g r0 = new com.leedarson.tcp.ipc.g
            r0.<init>()
            r11 = r0
            r0 = 256(0x100, float:3.59E-43)
            r11.u(r0)
            java.util.Random r0 = new java.util.Random
            r0.<init>()
            int r0 = r0.nextInt()
            r11.r(r0)
            r11.k(r7)
            r11.s(r9)
            r11.l(r10)
            r36 = r7
            r34 = r8
            long r7 = java.lang.System.currentTimeMillis()
            r11.t(r7)
            r0 = 1005(0x3ed, float:1.408E-42)
            r11.m(r0)
            r7 = 0
            r11.n(r7)
            r7 = 4
            r11.q(r7)
            r7 = 2
            r11.p(r7)
            com.leedarson.tcp.ipc.h r0 = new com.leedarson.tcp.ipc.h     // Catch:{ UnsupportedEncodingException -> 0x0285 }
            java.lang.String r7 = "UTF-8"
            byte[] r7 = r1.getBytes(r7)     // Catch:{ UnsupportedEncodingException -> 0x0285 }
            r0.<init>(r11, r7)     // Catch:{ UnsupportedEncodingException -> 0x0285 }
            com.leedarson.serviceimpl.tcp.manager.INettyManager r7 = com.leedarson.serviceimpl.tcp.manager.INettyManager.h()     // Catch:{ UnsupportedEncodingException -> 0x0285 }
            com.leedarson.serviceimpl.tcp.TcpServiceImpl$b r8 = new com.leedarson.serviceimpl.tcp.TcpServiceImpl$b     // Catch:{ UnsupportedEncodingException -> 0x0285 }
            r8.<init>(r4)     // Catch:{ UnsupportedEncodingException -> 0x0285 }
            r7.i(r3, r0, r8)     // Catch:{ UnsupportedEncodingException -> 0x0285 }
            goto L_0x0289
        L_0x0285:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0289:
            r9 = r4
            r24 = r5
            goto L_0x0697
        L_0x028e:
            r9 = r4
            r24 = r5
            goto L_0x0697
        L_0x0293:
            java.util.concurrent.Future r0 = r2.k     // Catch:{ Exception -> 0x02dd }
            if (r0 == 0) goto L_0x02a3
            boolean r0 = r0.isCancelled()     // Catch:{ Exception -> 0x02dd }
            if (r0 != 0) goto L_0x02a3
            java.util.concurrent.Future r0 = r2.k     // Catch:{ Exception -> 0x02dd }
            r1 = 1
            r0.cancel(r1)     // Catch:{ Exception -> 0x02dd }
        L_0x02a3:
            java.util.List<byte[]> r0 = r2.p     // Catch:{ Exception -> 0x02dd }
            r0.clear()     // Catch:{ Exception -> 0x02dd }
            org.greenrobot.eventbus.c r0 = org.greenrobot.eventbus.c.c()     // Catch:{ Exception -> 0x02dd }
            com.leedarson.serviceinterface.event.StopCloudRecordEvent r1 = new com.leedarson.serviceinterface.event.StopCloudRecordEvent     // Catch:{ Exception -> 0x02dd }
            r1.<init>()     // Catch:{ Exception -> 0x02dd }
            r0.l(r1)     // Catch:{ Exception -> 0x02dd }
            if (r6 == 0) goto L_0x02ca
            java.lang.Object r0 = r6.get(r15)     // Catch:{ Exception -> 0x02dd }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x02dd }
            com.leedarson.serviceimpl.tcp.manager.INettyManager r1 = com.leedarson.serviceimpl.tcp.manager.INettyManager.h()     // Catch:{ Exception -> 0x02dd }
            com.leedarson.serviceimpl.tcp.TcpServiceImpl$a r3 = new com.leedarson.serviceimpl.tcp.TcpServiceImpl$a     // Catch:{ Exception -> 0x02dd }
            r3.<init>(r0)     // Catch:{ Exception -> 0x02dd }
            r1.g(r0, r3)     // Catch:{ Exception -> 0x02dd }
        L_0x02ca:
            org.greenrobot.eventbus.c r0 = org.greenrobot.eventbus.c.c()     // Catch:{ Exception -> 0x02dd }
            com.leedarson.serviceinterface.event.JsBridgeCallbackEvent r1 = new com.leedarson.serviceinterface.event.JsBridgeCallbackEvent     // Catch:{ Exception -> 0x02dd }
            java.lang.String r3 = "{ \"code\": 200, \"desc\": \"\" } "
            r1.<init>(r4, r3)     // Catch:{ Exception -> 0x02dd }
            r0.l(r1)     // Catch:{ Exception -> 0x02dd }
            r9 = r4
            r24 = r5
            goto L_0x0697
        L_0x02dd:
            r0 = move-exception
            r0.printStackTrace()
            r9 = r4
            r24 = r5
            goto L_0x0697
        L_0x02e6:
            if (r6 == 0) goto L_0x0420
            java.lang.Object r0 = r6.get(r12)     // Catch:{ NumberFormatException -> 0x0414 }
            java.lang.String r0 = r0.toString()     // Catch:{ NumberFormatException -> 0x0414 }
            r1 = r14
            r14 = r0
            r0 = 0
            boolean r12 = r6.containsKey(r8)     // Catch:{ NumberFormatException -> 0x0414 }
            if (r12 == 0) goto L_0x030b
            java.lang.Object r8 = r6.get(r8)     // Catch:{ NumberFormatException -> 0x0414 }
            java.lang.String r8 = r8.toString()     // Catch:{ NumberFormatException -> 0x0414 }
            r35 = r4
            r24 = r5
            double r4 = java.lang.Double.parseDouble(r8)     // Catch:{ NumberFormatException -> 0x0412 }
            int r0 = (int) r4     // Catch:{ NumberFormatException -> 0x0412 }
            goto L_0x030f
        L_0x030b:
            r35 = r4
            r24 = r5
        L_0x030f:
            r4 = 0
            boolean r5 = r6.containsKey(r1)     // Catch:{ NumberFormatException -> 0x0412 }
            if (r5 == 0) goto L_0x0326
            java.lang.Object r1 = r6.get(r1)     // Catch:{ NumberFormatException -> 0x0412 }
            java.lang.String r1 = r1.toString()     // Catch:{ NumberFormatException -> 0x0412 }
            r34 = r4
            double r4 = java.lang.Double.parseDouble(r1)     // Catch:{ NumberFormatException -> 0x0412 }
            int r4 = (int) r4     // Catch:{ NumberFormatException -> 0x0412 }
            goto L_0x0328
        L_0x0326:
            r34 = r4
        L_0x0328:
            r1 = r20
            java.lang.String r5 = "avAESKey"
            boolean r5 = r6.containsKey(r5)     // Catch:{ NumberFormatException -> 0x0412 }
            if (r5 == 0) goto L_0x033e
            java.lang.String r5 = "avAESKey"
            java.lang.Object r5 = r6.get(r5)     // Catch:{ NumberFormatException -> 0x0412 }
            java.lang.String r5 = r5.toString()     // Catch:{ NumberFormatException -> 0x0412 }
            r1 = r5
            goto L_0x034f
        L_0x033e:
            r5 = r19
            boolean r8 = r6.containsKey(r5)     // Catch:{ NumberFormatException -> 0x0412 }
            if (r8 == 0) goto L_0x034f
            java.lang.Object r5 = r6.get(r5)     // Catch:{ NumberFormatException -> 0x0412 }
            java.lang.String r5 = r5.toString()     // Catch:{ NumberFormatException -> 0x0412 }
            r1 = r5
        L_0x034f:
            r5 = r20
            boolean r8 = r6.containsKey(r13)     // Catch:{ NumberFormatException -> 0x0412 }
            if (r8 == 0) goto L_0x0361
            java.lang.Object r3 = r6.get(r13)     // Catch:{ NumberFormatException -> 0x0412 }
            java.lang.String r3 = r3.toString()     // Catch:{ NumberFormatException -> 0x0412 }
            r5 = r3
            goto L_0x0370
        L_0x0361:
            boolean r8 = r6.containsKey(r3)     // Catch:{ NumberFormatException -> 0x0412 }
            if (r8 == 0) goto L_0x0370
            java.lang.Object r3 = r6.get(r3)     // Catch:{ NumberFormatException -> 0x0412 }
            java.lang.String r3 = r3.toString()     // Catch:{ NumberFormatException -> 0x0412 }
            r5 = r3
        L_0x0370:
            r3 = r20
            boolean r8 = r6.containsKey(r10)     // Catch:{ NumberFormatException -> 0x0412 }
            if (r8 == 0) goto L_0x0382
            java.lang.Object r8 = r6.get(r10)     // Catch:{ NumberFormatException -> 0x0412 }
            java.lang.String r8 = r8.toString()     // Catch:{ NumberFormatException -> 0x0412 }
            r3 = r8
            goto L_0x0391
        L_0x0382:
            boolean r8 = r6.containsKey(r9)     // Catch:{ NumberFormatException -> 0x0412 }
            if (r8 == 0) goto L_0x0391
            java.lang.Object r8 = r6.get(r9)     // Catch:{ NumberFormatException -> 0x0412 }
            java.lang.String r8 = r8.toString()     // Catch:{ NumberFormatException -> 0x0412 }
            r3 = r8
        L_0x0391:
            r8 = r20
            boolean r9 = r6.containsKey(r7)     // Catch:{ NumberFormatException -> 0x0412 }
            if (r9 == 0) goto L_0x03a3
            java.lang.Object r7 = r6.get(r7)     // Catch:{ NumberFormatException -> 0x0412 }
            java.lang.String r7 = r7.toString()     // Catch:{ NumberFormatException -> 0x0412 }
            r8 = r7
            goto L_0x03b2
        L_0x03a3:
            boolean r7 = r6.containsKey(r11)     // Catch:{ NumberFormatException -> 0x0412 }
            if (r7 == 0) goto L_0x03b2
            java.lang.Object r7 = r6.get(r11)     // Catch:{ NumberFormatException -> 0x0412 }
            java.lang.String r7 = r7.toString()     // Catch:{ NumberFormatException -> 0x0412 }
            r8 = r7
        L_0x03b2:
            byte[] r7 = com.leedarson.serviceimpl.tcp.manager.a.f(r5)     // Catch:{ NumberFormatException -> 0x0412 }
            short r17 = com.leedarson.serviceimpl.tcp.manager.a.b(r7)     // Catch:{ NumberFormatException -> 0x0412 }
            byte[] r7 = com.leedarson.serviceimpl.tcp.manager.a.f(r3)     // Catch:{ NumberFormatException -> 0x0412 }
            short r18 = com.leedarson.serviceimpl.tcp.manager.a.b(r7)     // Catch:{ NumberFormatException -> 0x0412 }
            byte[] r7 = com.leedarson.serviceimpl.tcp.manager.a.f(r8)     // Catch:{ NumberFormatException -> 0x0412 }
            int r19 = com.leedarson.serviceimpl.tcp.manager.a.a(r7)     // Catch:{ NumberFormatException -> 0x0412 }
            r7 = r20
            boolean r9 = r6.containsKey(r15)     // Catch:{ NumberFormatException -> 0x0412 }
            if (r9 == 0) goto L_0x03e0
            java.lang.Object r9 = r6.get(r15)     // Catch:{ NumberFormatException -> 0x0412 }
            java.lang.String r9 = r9.toString()     // Catch:{ NumberFormatException -> 0x0412 }
            r7 = r9
            android.content.Context r9 = r2.b     // Catch:{ NumberFormatException -> 0x0412 }
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefString(r9, r15, r7)     // Catch:{ NumberFormatException -> 0x0412 }
        L_0x03e0:
            r9 = 0
            r10 = r36
            boolean r11 = r6.containsKey(r10)     // Catch:{ NumberFormatException -> 0x0412 }
            if (r11 == 0) goto L_0x03f6
            java.lang.Object r10 = r6.get(r10)     // Catch:{ NumberFormatException -> 0x0412 }
            java.lang.String r10 = r10.toString()     // Catch:{ NumberFormatException -> 0x0412 }
            double r10 = java.lang.Double.parseDouble(r10)     // Catch:{ NumberFormatException -> 0x0412 }
            int r9 = (int) r10     // Catch:{ NumberFormatException -> 0x0412 }
        L_0x03f6:
            com.leedarson.serviceimpl.tcp.manager.INettyManager r12 = com.leedarson.serviceimpl.tcp.manager.INettyManager.h()     // Catch:{ NumberFormatException -> 0x0412 }
            r13 = r7
            r15 = r0
            r16 = r4
            r20 = r35
            r21 = r1
            r22 = r9
            r12.f(r13, r14, r15, r16, r17, r18, r19, r20, r21, r22)     // Catch:{ NumberFormatException -> 0x0412 }
            android.content.Context r10 = r2.b     // Catch:{ NumberFormatException -> 0x0412 }
            com.leedarson.serviceinterface.utils.PlayBackCacheUtils.createDevCacheDir(r10, r7)     // Catch:{ NumberFormatException -> 0x0412 }
            java.util.List<byte[]> r10 = r2.p     // Catch:{ NumberFormatException -> 0x0412 }
            r10.clear()     // Catch:{ NumberFormatException -> 0x0412 }
            goto L_0x0424
        L_0x0412:
            r0 = move-exception
            goto L_0x0419
        L_0x0414:
            r0 = move-exception
            r35 = r4
            r24 = r5
        L_0x0419:
            r0.printStackTrace()
            r9 = r35
            goto L_0x0697
        L_0x0420:
            r35 = r4
            r24 = r5
        L_0x0424:
            r9 = r35
            goto L_0x0697
        L_0x0428:
            r35 = r4
            r24 = r5
            if (r6 == 0) goto L_0x045c
            java.lang.Object r1 = r6.get(r15)     // Catch:{ Exception -> 0x0454 }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x0454 }
            java.lang.Object r0 = r6.get(r0)     // Catch:{ Exception -> 0x0454 }
            java.lang.String r0 = com.leedarson.serviceimpl.tcp.manager.a.e(r0)     // Catch:{ Exception -> 0x0454 }
            com.google.gson.internal.LinkedTreeMap r3 = com.leedarson.serviceimpl.tcp.manager.a.c(r0)     // Catch:{ Exception -> 0x0454 }
            java.lang.String r4 = "seq"
            java.lang.Object r4 = r3.get(r4)     // Catch:{ Exception -> 0x0454 }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x0454 }
            r9 = r35
            r2.setActionTcpSend(r1, r0, r4, r9)     // Catch:{ Exception -> 0x0452 }
            goto L_0x045e
        L_0x0452:
            r0 = move-exception
            goto L_0x0457
        L_0x0454:
            r0 = move-exception
            r9 = r35
        L_0x0457:
            r0.printStackTrace()
            goto L_0x0697
        L_0x045c:
            r9 = r35
        L_0x045e:
            goto L_0x0697
        L_0x0460:
            r9 = r4
            r24 = r5
            if (r6 == 0) goto L_0x051e
            boolean r0 = r6.containsKey(r15)     // Catch:{ Exception -> 0x0518 }
            if (r0 == 0) goto L_0x0473
            java.lang.Object r0 = r6.get(r15)     // Catch:{ Exception -> 0x0518 }
            java.lang.String r20 = r0.toString()     // Catch:{ Exception -> 0x0518 }
        L_0x0473:
            r1 = r20
            java.lang.String r0 = "["
            boolean r0 = r1.contains(r0)     // Catch:{ Exception -> 0x0518 }
            if (r0 == 0) goto L_0x04b4
            java.lang.String r0 = "]"
            boolean r0 = r1.contains(r0)     // Catch:{ Exception -> 0x0518 }
            if (r0 == 0) goto L_0x04b4
            org.json.JSONArray r0 = new org.json.JSONArray     // Catch:{ JSONException -> 0x04af }
            r0.<init>((java.lang.String) r1)     // Catch:{ JSONException -> 0x04af }
            r3 = 0
        L_0x048b:
            int r4 = r0.length()     // Catch:{ JSONException -> 0x04af }
            if (r3 >= r4) goto L_0x04ae
            com.leedarson.serviceimpl.tcp.manager.NettyManager r4 = com.leedarson.serviceimpl.tcp.manager.NettyManager.f()     // Catch:{ JSONException -> 0x04af }
            java.lang.String r5 = r0.getString(r3)     // Catch:{ JSONException -> 0x04af }
            r4.d(r5)     // Catch:{ JSONException -> 0x04af }
            java.util.HashSet<java.lang.String> r4 = r2.a     // Catch:{ JSONException -> 0x04af }
            r4.remove(r1)     // Catch:{ JSONException -> 0x04af }
            com.leedarson.serviceimpl.tcp.beans.TCPChannelMapBean r4 = r2.y(r1)     // Catch:{ JSONException -> 0x04af }
            if (r4 == 0) goto L_0x04ab
            com.leedarson.serviceimpl.tcp.beans.TCPChannelMapBean$TCP_CONNECT_STATUS r5 = com.leedarson.serviceimpl.tcp.beans.TCPChannelMapBean.TCP_CONNECT_STATUS.UNCONNECTED     // Catch:{ JSONException -> 0x04af }
            r4.connectStatus = r5     // Catch:{ JSONException -> 0x04af }
        L_0x04ab:
            int r3 = r3 + 1
            goto L_0x048b
        L_0x04ae:
            goto L_0x04b3
        L_0x04af:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ Exception -> 0x0518 }
        L_0x04b3:
            goto L_0x0503
        L_0x04b4:
            boolean r0 = android.text.TextUtils.isEmpty(r1)     // Catch:{ Exception -> 0x0518 }
            if (r0 != 0) goto L_0x04da
            java.lang.String r0 = "all"
            boolean r0 = r1.equals(r0)     // Catch:{ Exception -> 0x0518 }
            if (r0 == 0) goto L_0x04c3
            goto L_0x04da
        L_0x04c3:
            com.leedarson.serviceimpl.tcp.beans.TCPChannelMapBean r0 = r2.y(r1)     // Catch:{ Exception -> 0x0518 }
            if (r0 == 0) goto L_0x04cd
            com.leedarson.serviceimpl.tcp.beans.TCPChannelMapBean$TCP_CONNECT_STATUS r3 = com.leedarson.serviceimpl.tcp.beans.TCPChannelMapBean.TCP_CONNECT_STATUS.UNCONNECTED     // Catch:{ Exception -> 0x0518 }
            r0.connectStatus = r3     // Catch:{ Exception -> 0x0518 }
        L_0x04cd:
            com.leedarson.serviceimpl.tcp.manager.NettyManager r3 = com.leedarson.serviceimpl.tcp.manager.NettyManager.f()     // Catch:{ Exception -> 0x0518 }
            r3.d(r1)     // Catch:{ Exception -> 0x0518 }
            java.util.HashSet<java.lang.String> r3 = r2.a     // Catch:{ Exception -> 0x0518 }
            r3.remove(r1)     // Catch:{ Exception -> 0x0518 }
            goto L_0x0503
        L_0x04da:
            java.util.HashSet<java.lang.String> r0 = r2.a     // Catch:{ Exception -> 0x0518 }
            java.util.Iterator r0 = r0.iterator()     // Catch:{ Exception -> 0x0518 }
        L_0x04e0:
            boolean r3 = r0.hasNext()     // Catch:{ Exception -> 0x0518 }
            if (r3 == 0) goto L_0x04fe
            java.lang.Object r3 = r0.next()     // Catch:{ Exception -> 0x0518 }
            java.lang.String r3 = (java.lang.String) r3     // Catch:{ Exception -> 0x0518 }
            com.leedarson.serviceimpl.tcp.manager.NettyManager r4 = com.leedarson.serviceimpl.tcp.manager.NettyManager.f()     // Catch:{ Exception -> 0x0518 }
            r4.d(r3)     // Catch:{ Exception -> 0x0518 }
            com.leedarson.serviceimpl.tcp.beans.TCPChannelMapBean r4 = r2.y(r3)     // Catch:{ Exception -> 0x0518 }
            if (r4 == 0) goto L_0x04fd
            com.leedarson.serviceimpl.tcp.beans.TCPChannelMapBean$TCP_CONNECT_STATUS r5 = com.leedarson.serviceimpl.tcp.beans.TCPChannelMapBean.TCP_CONNECT_STATUS.UNCONNECTED     // Catch:{ Exception -> 0x0518 }
            r4.connectStatus = r5     // Catch:{ Exception -> 0x0518 }
        L_0x04fd:
            goto L_0x04e0
        L_0x04fe:
            java.util.HashSet<java.lang.String> r0 = r2.a     // Catch:{ Exception -> 0x0518 }
            r0.clear()     // Catch:{ Exception -> 0x0518 }
        L_0x0503:
            org.greenrobot.eventbus.c r0 = org.greenrobot.eventbus.c.c()     // Catch:{ Exception -> 0x0518 }
            com.leedarson.serviceinterface.event.JsBridgeCallbackEvent r3 = new com.leedarson.serviceinterface.event.JsBridgeCallbackEvent     // Catch:{ Exception -> 0x0518 }
            org.json.JSONObject r4 = com.leedarson.base.utils.p.c()     // Catch:{ Exception -> 0x0518 }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x0518 }
            r3.<init>(r9, r4)     // Catch:{ Exception -> 0x0518 }
            r0.l(r3)     // Catch:{ Exception -> 0x0518 }
            goto L_0x051e
        L_0x0518:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0697
        L_0x051e:
            goto L_0x0697
        L_0x0520:
            r9 = r4
            r24 = r5
            r1 = 1
            r7 = 0
            boolean r0 = r6.containsKey(r15)     // Catch:{ Exception -> 0x0586 }
            if (r0 == 0) goto L_0x0533
            java.lang.Object r0 = r6.get(r15)     // Catch:{ Exception -> 0x0586 }
            java.lang.String r20 = r0.toString()     // Catch:{ Exception -> 0x0586 }
        L_0x0533:
            r0 = r20
            org.json.JSONObject r3 = new org.json.JSONObject     // Catch:{ Exception -> 0x0586 }
            r3.<init>()     // Catch:{ Exception -> 0x0586 }
            java.lang.String r4 = "code"
            r5 = 200(0xc8, float:2.8E-43)
            r3.put((java.lang.String) r4, (int) r5)     // Catch:{ Exception -> 0x0586 }
            org.json.JSONObject r4 = new org.json.JSONObject     // Catch:{ Exception -> 0x0586 }
            r4.<init>()     // Catch:{ Exception -> 0x0586 }
            java.util.HashSet<java.lang.String> r5 = r2.a     // Catch:{ Exception -> 0x0586 }
            boolean r5 = r5.contains(r0)     // Catch:{ Exception -> 0x0586 }
            java.lang.String r8 = "status"
            if (r5 != 0) goto L_0x0555
            r1 = -1
            r4.put((java.lang.String) r8, (int) r1)     // Catch:{ Exception -> 0x0586 }
            goto L_0x056f
        L_0x0555:
            com.leedarson.serviceimpl.tcp.manager.NettyManager r5 = com.leedarson.serviceimpl.tcp.manager.NettyManager.f()     // Catch:{ Exception -> 0x0586 }
            com.leedarson.tcp.d r5 = r5.e(r0)     // Catch:{ Exception -> 0x0586 }
            if (r5 != 0) goto L_0x0564
            r1 = -1
            r4.put((java.lang.String) r8, (int) r1)     // Catch:{ Exception -> 0x0586 }
            goto L_0x056f
        L_0x0564:
            boolean r10 = r5.c()     // Catch:{ Exception -> 0x0586 }
            if (r10 == 0) goto L_0x056b
            goto L_0x056c
        L_0x056b:
            r1 = r7
        L_0x056c:
            r4.put((java.lang.String) r8, (int) r1)     // Catch:{ Exception -> 0x0586 }
        L_0x056f:
            java.lang.String r1 = "data"
            r3.put((java.lang.String) r1, (java.lang.Object) r4)     // Catch:{ Exception -> 0x0586 }
            org.greenrobot.eventbus.c r1 = org.greenrobot.eventbus.c.c()     // Catch:{ Exception -> 0x0586 }
            com.leedarson.serviceinterface.event.JsBridgeCallbackEvent r5 = new com.leedarson.serviceinterface.event.JsBridgeCallbackEvent     // Catch:{ Exception -> 0x0586 }
            java.lang.String r7 = r3.toString()     // Catch:{ Exception -> 0x0586 }
            r5.<init>(r9, r7)     // Catch:{ Exception -> 0x0586 }
            r1.l(r5)     // Catch:{ Exception -> 0x0586 }
            goto L_0x0697
        L_0x0586:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0697
        L_0x058c:
            r10 = r36
            r9 = r4
            r24 = r5
            r1 = r14
            r5 = r19
            if (r6 == 0) goto L_0x0696
            boolean r0 = r6.containsKey(r12)     // Catch:{ Exception -> 0x0691 }
            if (r0 == 0) goto L_0x05a7
            java.lang.Object r0 = r6.get(r12)     // Catch:{ Exception -> 0x0691 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0691 }
            r26 = r0
            goto L_0x05a9
        L_0x05a7:
            r26 = r20
        L_0x05a9:
            r0 = 0
            boolean r3 = r6.containsKey(r8)     // Catch:{ Exception -> 0x0691 }
            if (r3 == 0) goto L_0x05bd
            java.lang.Object r3 = r6.get(r8)     // Catch:{ Exception -> 0x0691 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x0691 }
            double r3 = java.lang.Double.parseDouble(r3)     // Catch:{ Exception -> 0x0691 }
            int r0 = (int) r3     // Catch:{ Exception -> 0x0691 }
        L_0x05bd:
            boolean r3 = r6.containsKey(r15)     // Catch:{ Exception -> 0x0691 }
            if (r3 == 0) goto L_0x05cc
            java.lang.Object r3 = r6.get(r15)     // Catch:{ Exception -> 0x0691 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x0691 }
            goto L_0x05ce
        L_0x05cc:
            r3 = r20
        L_0x05ce:
            r4 = 1
            java.lang.String r7 = "tls"
            boolean r7 = r6.containsKey(r7)     // Catch:{ Exception -> 0x0691 }
            if (r7 == 0) goto L_0x05e7
            java.lang.String r7 = "tls"
            java.lang.Object r7 = r6.get(r7)     // Catch:{ Exception -> 0x0691 }
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x0691 }
            double r7 = java.lang.Double.parseDouble(r7)     // Catch:{ Exception -> 0x0691 }
            int r4 = (int) r7     // Catch:{ Exception -> 0x0691 }
            goto L_0x05fa
        L_0x05e7:
            boolean r7 = r6.containsKey(r10)     // Catch:{ Exception -> 0x0691 }
            if (r7 == 0) goto L_0x05fa
            java.lang.Object r7 = r6.get(r10)     // Catch:{ Exception -> 0x0691 }
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x0691 }
            double r7 = java.lang.Double.parseDouble(r7)     // Catch:{ Exception -> 0x0691 }
            int r4 = (int) r7     // Catch:{ Exception -> 0x0691 }
        L_0x05fa:
            r7 = r20
            boolean r8 = r6.containsKey(r5)     // Catch:{ Exception -> 0x0691 }
            if (r8 == 0) goto L_0x060b
            java.lang.Object r5 = r6.get(r5)     // Catch:{ Exception -> 0x0691 }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x0691 }
            r7 = r5
        L_0x060b:
            r5 = 0
            boolean r8 = r6.containsKey(r1)     // Catch:{ Exception -> 0x0691 }
            if (r8 == 0) goto L_0x061f
            java.lang.Object r1 = r6.get(r1)     // Catch:{ Exception -> 0x0691 }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x0691 }
            double r10 = java.lang.Double.parseDouble(r1)     // Catch:{ Exception -> 0x0691 }
            int r5 = (int) r10     // Catch:{ Exception -> 0x0691 }
        L_0x061f:
            r1 = r20
            java.lang.String r8 = "deviceId"
            boolean r10 = r6.containsKey(r8)     // Catch:{ Exception -> 0x0691 }
            if (r10 == 0) goto L_0x0632
            java.lang.Object r10 = r6.get(r8)     // Catch:{ Exception -> 0x0691 }
            java.lang.String r10 = r10.toString()     // Catch:{ Exception -> 0x0691 }
            r1 = r10
        L_0x0632:
            java.util.concurrent.ConcurrentHashMap<java.lang.String, com.leedarson.serviceimpl.tcp.beans.TCPChannelMapBean> r10 = r2.m     // Catch:{ Exception -> 0x0691 }
            boolean r10 = r10.containsKey(r1)     // Catch:{ Exception -> 0x0691 }
            if (r10 != 0) goto L_0x064e
            com.leedarson.serviceimpl.tcp.beans.TCPChannelMapBean r10 = new com.leedarson.serviceimpl.tcp.beans.TCPChannelMapBean     // Catch:{ Exception -> 0x0691 }
            r10.<init>()     // Catch:{ Exception -> 0x0691 }
            r10.deviceId = r1     // Catch:{ Exception -> 0x0691 }
            r10.sessionId = r3     // Catch:{ Exception -> 0x0691 }
            com.leedarson.serviceimpl.tcp.beans.TCPChannelMapBean$TCP_CONNECT_STATUS r11 = com.leedarson.serviceimpl.tcp.beans.TCPChannelMapBean.TCP_CONNECT_STATUS.UNCONNECTED     // Catch:{ Exception -> 0x0691 }
            r10.connectStatus = r11     // Catch:{ Exception -> 0x0691 }
            java.util.concurrent.ConcurrentHashMap<java.lang.String, com.leedarson.serviceimpl.tcp.beans.TCPChannelMapBean> r11 = r2.m     // Catch:{ Exception -> 0x0691 }
            r11.put(r1, r10)     // Catch:{ Exception -> 0x0691 }
            goto L_0x0658
        L_0x064e:
            java.util.concurrent.ConcurrentHashMap<java.lang.String, com.leedarson.serviceimpl.tcp.beans.TCPChannelMapBean> r10 = r2.m     // Catch:{ Exception -> 0x0691 }
            java.lang.Object r10 = r10.get(r1)     // Catch:{ Exception -> 0x0691 }
            com.leedarson.serviceimpl.tcp.beans.TCPChannelMapBean r10 = (com.leedarson.serviceimpl.tcp.beans.TCPChannelMapBean) r10     // Catch:{ Exception -> 0x0691 }
            r10.sessionId = r3     // Catch:{ Exception -> 0x0691 }
        L_0x0658:
            com.leedarson.serviceimpl.tcp.manager.NettyManager r25 = com.leedarson.serviceimpl.tcp.manager.NettyManager.f()     // Catch:{ Exception -> 0x0691 }
            android.content.Context r10 = r2.b     // Catch:{ Exception -> 0x0691 }
            android.content.Context r29 = r10.getApplicationContext()     // Catch:{ Exception -> 0x0691 }
            r27 = r0
            r28 = r3
            r30 = r4
            r31 = r7
            r32 = r5
            r25.c(r26, r27, r28, r29, r30, r31, r32)     // Catch:{ Exception -> 0x0691 }
            java.util.HashSet<java.lang.String> r10 = r2.a     // Catch:{ Exception -> 0x0691 }
            r10.add(r3)     // Catch:{ Exception -> 0x0691 }
            org.json.JSONObject r10 = new org.json.JSONObject     // Catch:{ Exception -> 0x0691 }
            r10.<init>()     // Catch:{ Exception -> 0x0691 }
            java.lang.String r11 = "code"
            r12 = 200(0xc8, float:2.8E-43)
            r10.put((java.lang.String) r11, (int) r12)     // Catch:{ Exception -> 0x0691 }
            org.greenrobot.eventbus.c r11 = org.greenrobot.eventbus.c.c()     // Catch:{ Exception -> 0x0691 }
            com.leedarson.serviceinterface.event.JsBridgeCallbackEvent r12 = new com.leedarson.serviceinterface.event.JsBridgeCallbackEvent     // Catch:{ Exception -> 0x0691 }
            java.lang.String r13 = r10.toString()     // Catch:{ Exception -> 0x0691 }
            r12.<init>(r9, r13)     // Catch:{ Exception -> 0x0691 }
            r11.l(r12)     // Catch:{ Exception -> 0x0691 }
            goto L_0x0696
        L_0x0691:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0697
        L_0x0696:
        L_0x0697:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.tcp.TcpServiceImpl.handleData(java.lang.String, java.lang.String, java.lang.String):void");
    }

    public class a implements com.leedarson.tcp.b {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;

        a(String str) {
            this.a = str;
        }

        public void onSuccess() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8974, new Class[0], Void.TYPE).isSupported) {
                if (!INettyManager.h().c) {
                    TcpServiceImpl.this.updateTcpChannelConnectStateBySessionId(this.a, 0);
                    org.greenrobot.eventbus.c.c().l(new SocketStatusChangeEvent("", 2, ""));
                }
            }
        }
    }

    public class b implements com.leedarson.tcp.callback.a {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;

        b(String str) {
            this.a = str;
        }

        public void a(Object o, boolean z) {
            if (!PatchProxy.proxy(new Object[]{o, new Byte(z ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 8975, new Class[]{Object.class, Boolean.TYPE}, Void.TYPE).isSupported) {
                try {
                    org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(this.a, new String(((h) o).b(), "UTF-8")));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }

        public void onFailure(int code, String errorStr) {
        }
    }

    public class c implements com.leedarson.tcp.callback.a<String> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;

        c(String str) {
            this.a = str;
        }

        public /* bridge */ /* synthetic */ void a(Object obj, boolean z) {
            if (!PatchProxy.proxy(new Object[]{obj, new Byte(z ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 8978, new Class[]{Object.class, Boolean.TYPE}, Void.TYPE).isSupported) {
                b((String) obj, z);
            }
        }

        public void b(String s, boolean z) {
            if (!PatchProxy.proxy(new Object[]{s, new Byte(z ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 8976, new Class[]{String.class, Boolean.TYPE}, Void.TYPE).isSupported) {
                org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(this.a, s));
            }
        }

        public void onFailure(int code, String errorStr) {
            Object[] objArr = {new Integer(code), errorStr};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8977, new Class[]{Integer.TYPE, String.class}, Void.TYPE).isSupported) {
                org.greenrobot.eventbus.c c = org.greenrobot.eventbus.c.c();
                String str = this.a;
                c.l(new JsBridgeCallbackEvent(str, "{\"ack\":{\"code\":" + code + ",\"desc\":\"" + errorStr + "\"}}"));
            }
        }
    }

    public void setActionTcpSend(String sessionid, String msg, String requestId, String callbackKey) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{sessionid, msg, requestId, callbackKey}, this, changeQuickRedirect, false, 8950, new Class[]{cls, cls, cls, cls}, Void.TYPE).isSupported) {
            NettyManager.f().i(sessionid, msg, requestId, new c(callbackKey));
        }
    }

    public void onMessage(String action, String jsonData) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(new Object[]{action, jsonData}, this, changeQuickRedirect, false, 8951, clsArr, Void.TYPE).isSupported) {
            org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent(Constants.SERVICE_TCP_NEW, action, jsonData));
        }
    }

    public void getRecordStream(String str, long j2, int i2, int i3, int i4, OnGetRecordListener onGetRecordListener) {
        Object[] objArr = {str, new Long(j2), new Integer(i2), new Integer(i3), new Integer(i4), onGetRecordListener};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8952, new Class[]{String.class, Long.TYPE, cls, cls, cls, OnGetRecordListener.class}, Void.TYPE).isSupported) {
            long startTime = j2;
            int speed = i4;
            int type = i2;
            String sessionId = str;
            OnGetRecordListener listener = onGetRecordListener;
            int framenums = i3;
            String devid = SharePreferenceUtils.getPrefString(this.b, "sessionId", "");
            Context context = this.b;
            if (PlayBackCacheUtils.isCacheFileExit(context, devid, startTime + "")) {
                Context context2 = this.b;
                byte[] bytes = PlayBackCacheUtils.readCacheFile(context2, devid, startTime + "");
                a.b g2 = timber.log.a.g("TcpServiceImpl");
                g2.h("ACTION_GETRECORD: exit: startTime=" + startTime + " length=" + bytes.length, new Object[0]);
                List<byte[]> tempsRes = new ArrayList<>();
                tempsRes.add(bytes);
                if (listener != null) {
                    listener.onSuccess(IjkMediaCodecInfo.RANK_SECURE, tempsRes);
                    return;
                }
                return;
            }
            z(sessionId, this.c, this.d, this.e, startTime, type, framenums, speed, listener, true);
        }
    }

    public void disConnect() {
    }

    public void getSeekStream(String str, long startTime, int type, int framenums, int speed, OnGetRecordListener listener) {
        Object[] objArr = {str, new Long(startTime), new Integer(type), new Integer(framenums), new Integer(speed), listener};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        Class[] clsArr = {String.class, Long.TYPE, cls, cls, cls, OnGetRecordListener.class};
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8953, clsArr, Void.TYPE).isSupported) {
            String sessionId = str;
            short s = this.c;
            short s2 = this.d;
            int i2 = this.e;
            z(sessionId, s, s2, i2, startTime, type, framenums, speed, listener, false);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x0196 A[Catch:{ Exception -> 0x019f }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void getSeekStream(java.lang.String r24, long r25, int r27, int r28, int r29, int r30, com.leedarson.serviceinterface.listener.OnGetRecordListener r31) {
        /*
            r23 = this;
            java.lang.String r0 = "_position"
            r1 = 7
            java.lang.Object[] r2 = new java.lang.Object[r1]
            r9 = 0
            r2[r9] = r24
            java.lang.Long r3 = new java.lang.Long
            r10 = r25
            r3.<init>(r10)
            r12 = 1
            r2[r12] = r3
            java.lang.Integer r3 = new java.lang.Integer
            r13 = r27
            r3.<init>(r13)
            r14 = 2
            r2[r14] = r3
            java.lang.Integer r3 = new java.lang.Integer
            r15 = r28
            r3.<init>(r15)
            r4 = 3
            r2[r4] = r3
            java.lang.Integer r3 = new java.lang.Integer
            r8 = r29
            r3.<init>(r8)
            r5 = 4
            r2[r5] = r3
            java.lang.Integer r3 = new java.lang.Integer
            r7 = r30
            r3.<init>(r7)
            r6 = 5
            r2[r6] = r3
            r3 = 6
            r2[r3] = r31
            com.meituan.robust.ChangeQuickRedirect r16 = changeQuickRedirect
            java.lang.Class[] r1 = new java.lang.Class[r1]
            java.lang.Class<java.lang.String> r17 = java.lang.String.class
            r1[r9] = r17
            java.lang.Class r17 = java.lang.Long.TYPE
            r1[r12] = r17
            java.lang.Class r17 = java.lang.Integer.TYPE
            r1[r14] = r17
            r1[r4] = r17
            r1[r5] = r17
            r1[r6] = r17
            java.lang.Class<com.leedarson.serviceinterface.listener.OnGetRecordListener> r4 = com.leedarson.serviceinterface.listener.OnGetRecordListener.class
            r1[r3] = r4
            java.lang.Class r17 = java.lang.Void.TYPE
            r5 = 0
            r6 = 8954(0x22fa, float:1.2547E-41)
            r3 = r23
            r4 = r16
            r7 = r1
            r8 = r17
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r2, r3, r4, r5, r6, r7, r8)
            boolean r1 = r1.isSupported
            if (r1 == 0) goto L_0x006c
            return
        L_0x006c:
            r1 = r23
            r2 = r25
            r18 = r29
            r4 = r27
            r5 = r31
            r11 = r24
            r19 = r30
            r17 = r28
            com.alibaba.android.arouter.launcher.a r6 = com.alibaba.android.arouter.launcher.a.c()
            java.lang.Class<com.leedarson.serviceinterface.DatabaseService> r7 = com.leedarson.serviceinterface.DatabaseService.class
            java.lang.Object r6 = r6.g(r7)
            com.leedarson.serviceinterface.DatabaseService r6 = (com.leedarson.serviceinterface.DatabaseService) r6
            android.content.Context r7 = r1.b
            java.lang.String r8 = r1.j
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            r10.append(r2)
            java.lang.String r13 = ""
            r10.append(r13)
            java.lang.String r10 = r10.toString()
            boolean r7 = com.leedarson.serviceinterface.utils.PlayBackCacheUtils.isCacheFileExit(r7, r8, r10)
            if (r7 == 0) goto L_0x01ab
            if (r6 == 0) goto L_0x01ab
            android.content.Context r7 = r1.b
            java.lang.String r8 = r1.j
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            r10.append(r2)
            r10.append(r13)
            java.lang.String r10 = r10.toString()
            byte[] r7 = com.leedarson.serviceinterface.utils.PlayBackCacheUtils.readCacheFile(r7, r8, r10)
            java.lang.String r8 = "SELECT * FROM t_c_video_time WHERE _startTime=%d AND _playTime>=%d ORDER BY _id limit 0,1"
            java.util.Locale r10 = java.util.Locale.US
            java.lang.Object[] r15 = new java.lang.Object[r14]
            java.lang.Long r16 = java.lang.Long.valueOf(r2)
            r15[r9] = r16
            r24 = r10
            long r9 = (long) r4
            long r9 = r9 + r2
            java.lang.Long r9 = java.lang.Long.valueOf(r9)
            r15[r12] = r9
            r9 = r24
            java.lang.String r10 = java.lang.String.format(r9, r8, r15)
            com.google.gson.JsonArray r10 = r6.rawQuery(r10)
            int r15 = r10.size()
            if (r15 <= 0) goto L_0x01a8
            org.json.JSONObject r15 = new org.json.JSONObject     // Catch:{ Exception -> 0x01a1 }
            r12 = 0
            com.google.gson.JsonElement r21 = r10.get(r12)     // Catch:{ Exception -> 0x01a1 }
            java.lang.String r12 = r21.toString()     // Catch:{ Exception -> 0x01a1 }
            r15.<init>((java.lang.String) r12)     // Catch:{ Exception -> 0x01a1 }
            r12 = r15
            java.lang.String r15 = r12.getString(r0)     // Catch:{ Exception -> 0x01a1 }
            int r15 = java.lang.Integer.parseInt(r15)     // Catch:{ Exception -> 0x01a1 }
            java.lang.String r14 = "_playTime"
            java.lang.String r14 = r12.getString(r14)     // Catch:{ Exception -> 0x01a1 }
            int r14 = r14.length()     // Catch:{ Exception -> 0x01a1 }
            r24 = r10
            r10 = 13
            if (r14 != r10) goto L_0x0163
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x015f }
            r10.<init>()     // Catch:{ Exception -> 0x015f }
            r10.append(r2)     // Catch:{ Exception -> 0x015f }
            r10.append(r13)     // Catch:{ Exception -> 0x015f }
            java.lang.String r10 = r10.toString()     // Catch:{ Exception -> 0x015f }
            int r10 = r10.length()     // Catch:{ Exception -> 0x015f }
            r13 = 10
            if (r10 != r13) goto L_0x0163
            r10 = 2
            java.lang.Object[] r10 = new java.lang.Object[r10]     // Catch:{ Exception -> 0x015f }
            java.lang.Long r13 = java.lang.Long.valueOf(r2)     // Catch:{ Exception -> 0x015f }
            r14 = 0
            r10[r14] = r13     // Catch:{ Exception -> 0x015f }
            long r13 = (long) r4     // Catch:{ Exception -> 0x015f }
            long r13 = r13 + r2
            r21 = 1000(0x3e8, double:4.94E-321)
            long r13 = r13 * r21
            java.lang.Long r13 = java.lang.Long.valueOf(r13)     // Catch:{ Exception -> 0x015f }
            r14 = 1
            r10[r14] = r13     // Catch:{ Exception -> 0x015f }
            java.lang.String r9 = java.lang.String.format(r9, r8, r10)     // Catch:{ Exception -> 0x015f }
            com.google.gson.JsonArray r9 = r6.rawQuery(r9)     // Catch:{ Exception -> 0x015f }
            r10 = r9
            int r9 = r10.size()     // Catch:{ Exception -> 0x019f }
            if (r9 <= 0) goto L_0x0165
            org.json.JSONObject r9 = new org.json.JSONObject     // Catch:{ Exception -> 0x019f }
            r13 = 0
            com.google.gson.JsonElement r14 = r10.get(r13)     // Catch:{ Exception -> 0x019f }
            java.lang.String r13 = r14.toString()     // Catch:{ Exception -> 0x019f }
            r9.<init>((java.lang.String) r13)     // Catch:{ Exception -> 0x019f }
            r12 = r9
            java.lang.String r0 = r12.getString(r0)     // Catch:{ Exception -> 0x019f }
            int r0 = java.lang.Integer.parseInt(r0)     // Catch:{ Exception -> 0x019f }
            r15 = r0
            goto L_0x0165
        L_0x015f:
            r0 = move-exception
            r10 = r24
            goto L_0x01a4
        L_0x0163:
            r10 = r24
        L_0x0165:
            int r0 = r7.length     // Catch:{ Exception -> 0x019f }
            int r0 = r0 - r15
            byte[] r0 = new byte[r0]     // Catch:{ Exception -> 0x019f }
            int r9 = r7.length     // Catch:{ Exception -> 0x019f }
            int r9 = r9 - r15
            r13 = 0
            java.lang.System.arraycopy(r7, r15, r0, r13, r9)     // Catch:{ Exception -> 0x019f }
            java.lang.String r9 = "TcpServiceImpl"
            timber.log.a$b r9 = timber.log.a.g(r9)     // Catch:{ Exception -> 0x019f }
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x019f }
            r13.<init>()     // Catch:{ Exception -> 0x019f }
            java.lang.String r14 = "ACTION_GETRECORD: seek: position="
            r13.append(r14)     // Catch:{ Exception -> 0x019f }
            r13.append(r15)     // Catch:{ Exception -> 0x019f }
            java.lang.String r13 = r13.toString()     // Catch:{ Exception -> 0x019f }
            r14 = 0
            java.lang.Object[] r14 = new java.lang.Object[r14]     // Catch:{ Exception -> 0x019f }
            r9.h(r13, r14)     // Catch:{ Exception -> 0x019f }
            java.util.ArrayList r9 = new java.util.ArrayList     // Catch:{ Exception -> 0x019f }
            r9.<init>()     // Catch:{ Exception -> 0x019f }
            r9.add(r0)     // Catch:{ Exception -> 0x019f }
            if (r5 == 0) goto L_0x019b
            r13 = 300(0x12c, float:4.2E-43)
            r5.onSuccess(r13, r9)     // Catch:{ Exception -> 0x019f }
        L_0x019b:
            r7 = 0
            r0 = 0
            goto L_0x01aa
        L_0x019f:
            r0 = move-exception
            goto L_0x01a4
        L_0x01a1:
            r0 = move-exception
            r24 = r10
        L_0x01a4:
            r0.printStackTrace()
            goto L_0x01aa
        L_0x01a8:
            r24 = r10
        L_0x01aa:
            goto L_0x01bc
        L_0x01ab:
            short r12 = r1.c
            short r13 = r1.d
            int r14 = r1.e
            long r7 = (long) r4
            long r15 = r2 + r7
            r21 = 0
            r10 = r1
            r20 = r5
            r10.z(r11, r12, r13, r14, r15, r17, r18, r19, r20, r21)
        L_0x01bc:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.tcp.TcpServiceImpl.getSeekStream(java.lang.String, long, int, int, int, int, com.leedarson.serviceinterface.listener.OnGetRecordListener):void");
    }

    public void init(Context context) {
        this.b = context;
    }

    public class d implements Callable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ boolean a1;
        final /* synthetic */ short c;
        final /* synthetic */ short d;
        final /* synthetic */ int f;
        final /* synthetic */ String p0;
        final /* synthetic */ OnGetRecordListener p1;
        final /* synthetic */ long q;
        final /* synthetic */ int x;
        final /* synthetic */ int y;
        final /* synthetic */ int z;

        d(short s, short s2, int i, long j, int i2, int i3, int i4, String str, boolean z2, OnGetRecordListener onGetRecordListener) {
            this.c = s;
            this.d = s2;
            this.f = i;
            this.q = j;
            this.x = i2;
            this.y = i3;
            this.z = i4;
            this.p0 = str;
            this.a1 = z2;
            this.p1 = onGetRecordListener;
        }

        public Object call() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8979, new Class[0], Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            String devid = SharePreferenceUtils.getPrefString(TcpServiceImpl.this.b, "sessionId", "");
            int seq = new Random().nextInt();
            g head = new g();
            head.u(256);
            head.r(seq);
            head.k(this.c);
            head.s(this.d);
            head.l(this.f);
            head.t(System.currentTimeMillis());
            head.m(1005);
            head.n(0);
            head.q(4);
            head.p(2);
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("begin", this.q);
                jsonObject.put(IjkMediaMeta.IJKM_KEY_TYPE, this.x);
                jsonObject.put("framenums", this.y);
                jsonObject.put("speed", this.z);
                if (TcpServiceImpl.this.f) {
                    if (this.x == 0) {
                        Context context = TcpServiceImpl.this.b;
                        PlayBackCacheUtils.createDevCacheFile(context, devid, this.q + "");
                        String unused = TcpServiceImpl.this.j = devid;
                        long unused2 = TcpServiceImpl.this.h = this.q;
                        long unused3 = TcpServiceImpl.this.i = 0;
                        long unused4 = TcpServiceImpl.this.g = 0;
                        DatabaseService databaseService = (DatabaseService) com.alibaba.android.arouter.launcher.a.c().g(DatabaseService.class);
                        if (databaseService != null) {
                            databaseService.execSQL(String.format(Locale.US, "DELETE FROM t_c_video_time WHERE _devId='%s' AND _startTime=%d", new Object[]{TcpServiceImpl.this.j, Long.valueOf(this.q)}));
                        }
                    }
                    jsonObject.put("networkqos", 1);
                    jsonObject.put("playId", 1);
                    INettyManager.h().j(this.p0, new h(head, jsonObject.toString().getBytes("UTF-8")), new a(devid));
                } else {
                    INettyManager.h().i(this.p0, new h(head, jsonObject.toString().getBytes("UTF-8")), new b());
                }
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        public class a implements com.leedarson.tcp.callback.a {
            public static ChangeQuickRedirect changeQuickRedirect;
            final /* synthetic */ String a;

            a(String str) {
                this.a = str;
            }

            public void a(Object o, boolean z) {
                if (!PatchProxy.proxy(new Object[]{o, new Byte(z ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 8980, new Class[]{Object.class, Boolean.TYPE}, Void.TYPE).isSupported) {
                    boolean z2 = z;
                    h resq = (h) o;
                    if (resq.a().a() == 273) {
                        if (d.this.a1) {
                            byte[] data = resq.b();
                            byte[] tbs = new byte[8];
                            System.arraycopy(data, 4, tbs, 0, 8);
                            long getTimeSec = com.leedarson.serviceimpl.tcp.manager.c.a(tbs);
                            Context context = TcpServiceImpl.this.b;
                            String str = this.a;
                            StringBuilder sb = new StringBuilder();
                            long getTimeSec2 = getTimeSec;
                            sb.append(d.this.q);
                            sb.append("");
                            PlayBackCacheUtils.writeCacheFile(context, str, sb.toString(), resq.b());
                            if (getTimeSec2 <= 0 || TcpServiceImpl.this.i == getTimeSec2) {
                            } else {
                                long getTimeSec3 = getTimeSec2;
                                long unused = TcpServiceImpl.this.i = getTimeSec3;
                                OnGetRecordListener onGetRecordListener = d.this.p1;
                                if (onGetRecordListener != null) {
                                    onGetRecordListener.onGetCacheTimestamp(getTimeSec3);
                                }
                                a.b g = timber.log.a.g("TcpServiceImpl");
                                g.m(d.this.q + "====time: " + getTimeSec3 + "===" + TcpServiceImpl.this.g + "===" + TcpServiceImpl.this.j, new Object[0]);
                                DatabaseService databaseService = (DatabaseService) com.alibaba.android.arouter.launcher.a.c().g(DatabaseService.class);
                                if (databaseService != null) {
                                    databaseService.execSQL(String.format(Locale.US, "INSERT INTO t_c_video_time (_devId, _startTime, _playTime, _position) VALUES('%s', %d,%d,%d)", new Object[]{TcpServiceImpl.this.j, Long.valueOf(d.this.q), Long.valueOf(getTimeSec3), Long.valueOf(TcpServiceImpl.this.g)}));
                                }
                            }
                            TcpServiceImpl.q(TcpServiceImpl.this, (long) data.length);
                        }
                        if (resq.a().h() == 0) {
                            TcpServiceImpl.this.p.add(resq.b());
                        } else if (resq.a().h() == 1) {
                            TcpServiceImpl.this.p.add(resq.b());
                            List<byte[]> tempsRes = new ArrayList<>();
                            tempsRes.addAll(TcpServiceImpl.this.p);
                            OnGetRecordListener onGetRecordListener2 = d.this.p1;
                            if (onGetRecordListener2 != null) {
                                onGetRecordListener2.onSuccess(resq.a().f(), tempsRes);
                            }
                            TcpServiceImpl.this.p.clear();
                        }
                    } else if (resq.a().a() != 272) {
                    } else {
                        if (resq.a().f() == -15520 || resq.a().f() == -15528) {
                            timber.log.a.g("TcpServiceImpl").h("ACTION_GETRECORD: complete", new Object[0]);
                            d dVar = d.this;
                            if (dVar.a1) {
                                Context context2 = TcpServiceImpl.this.b;
                                String str2 = this.a;
                                PlayBackCacheUtils.writeCacheFileComplete(context2, str2, d.this.q + "");
                            }
                            OnGetRecordListener onGetRecordListener3 = d.this.p1;
                            if (onGetRecordListener3 != null) {
                                onGetRecordListener3.onSuccess(-15528, (List<byte[]>) null);
                            }
                        }
                    }
                }
            }

            public void onFailure(int code, String errorStr) {
                OnGetRecordListener onGetRecordListener;
                Object[] objArr = {new Integer(code), errorStr};
                ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
                if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8981, new Class[]{Integer.TYPE, String.class}, Void.TYPE).isSupported && (onGetRecordListener = d.this.p1) != null) {
                    onGetRecordListener.onFailure(code, errorStr);
                }
            }
        }

        public class b implements com.leedarson.tcp.callback.a {
            public static ChangeQuickRedirect changeQuickRedirect;

            b() {
            }

            public void a(Object o, boolean z) {
                Object[] objArr = {o, new Byte(z ? (byte) 1 : 0)};
                ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
                if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8983, new Class[]{Object.class, Boolean.TYPE}, Void.TYPE).isSupported) {
                    timber.log.a.g("TcpServiceImpl").h("onSuccess sendTCPCommand: ", new Object[0]);
                    h resq = (h) o;
                    List<byte[]> tempsRes = new ArrayList<>();
                    tempsRes.add(resq.b());
                    OnGetRecordListener onGetRecordListener = d.this.p1;
                    if (onGetRecordListener != null) {
                        onGetRecordListener.onSuccess(resq.a().f(), tempsRes);
                    }
                }
            }

            public void onFailure(int code, String errorStr) {
                OnGetRecordListener onGetRecordListener;
                Object[] objArr = {new Integer(code), errorStr};
                ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
                if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8984, new Class[]{Integer.TYPE, String.class}, Void.TYPE).isSupported && (onGetRecordListener = d.this.p1) != null) {
                    onGetRecordListener.onFailure(code, errorStr);
                }
            }
        }
    }

    private void z(String sessionId, short cmd, short s, int cmdParam, long j2, int i2, int framenums, int speed, OnGetRecordListener listener, boolean useCache) {
        Object[] objArr = {sessionId, new Short(cmd), new Short(s), new Integer(cmdParam), new Long(j2), new Integer(i2), new Integer(framenums), new Integer(speed), listener, new Byte(useCache ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Short.TYPE;
        Class cls2 = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8955, new Class[]{String.class, cls, cls, cls2, Long.TYPE, cls2, cls2, cls2, OnGetRecordListener.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            short subCmd = s;
            int type = i2;
            long startTime = j2;
            this.k = this.l.submit(new d(cmd, subCmd, cmdParam, startTime, type, framenums, speed, sessionId, useCache, listener));
        }
    }

    private TCPChannelMapBean y(String _targetSessionId) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{_targetSessionId}, this, changeQuickRedirect, false, 8956, new Class[]{String.class}, TCPChannelMapBean.class);
        if (proxy.isSupported) {
            return (TCPChannelMapBean) proxy.result;
        }
        try {
            for (String key : this.m.keySet()) {
                if (this.m.get(key).sessionId.equals(_targetSessionId)) {
                    return this.m.get(key);
                }
            }
            return null;
        } catch (Exception e2) {
            a("getConnectTableItemBySessionId  --> 查找异常=" + e2.toString());
            return null;
        }
    }

    public void updateTcpChannelConnectStateBySessionId(String sessionId, int statue) {
        if (!PatchProxy.proxy(new Object[]{sessionId, new Integer(statue)}, this, changeQuickRedirect, false, 8957, new Class[]{String.class, Integer.TYPE}, Void.TYPE).isSupported) {
            try {
                TCPChannelMapBean _channel = y(sessionId);
                if (_channel != null) {
                    a("updateTcpChannelConnectStateBySessionId deviceId:  " + _channel.deviceId + " 更新连接状态 status=" + statue);
                    _channel.connectStatus = statue == 1 ? TCPChannelMapBean.TCP_CONNECT_STATUS.CONNECTED : TCPChannelMapBean.TCP_CONNECT_STATUS.UNCONNECTED;
                }
            } catch (Exception e2) {
                a("updateTcpChannelConnectStateBySessionId 更新异常 e=" + e2.toString());
            }
        }
    }

    public void updateTcpChannelLoginStateBySessionId(String sessionId, int statue) {
        if (!PatchProxy.proxy(new Object[]{sessionId, new Integer(statue)}, this, changeQuickRedirect, false, 8958, new Class[]{String.class, Integer.TYPE}, Void.TYPE).isSupported) {
            try {
                TCPChannelMapBean _channel = y(sessionId);
                if (_channel != null) {
                    a("updateTcpChannelLoginStateBySessionId deviceId:  " + _channel.deviceId + " 更新登录状态 status=" + statue);
                    _channel.loginStatus = statue == 1 ? TCPChannelMapBean.TCP_LOGIN_STATUS.LOGIN : TCPChannelMapBean.TCP_LOGIN_STATUS.NOTLOGIN;
                    ConcurrentLinkedDeque<TCPSendTaskBean> taskList = this.n.get(_channel.deviceId);
                    if (taskList != null) {
                        v(taskList, _channel);
                    }
                }
            } catch (Exception e2) {
                a("updateTcpChannelLoginStateBySessionId 更新异常 e=" + e2.toString());
            }
        }
    }

    private void v(ConcurrentLinkedDeque<TCPSendTaskBean> taskList, TCPChannelMapBean channel) {
        if (!PatchProxy.proxy(new Object[]{taskList, channel}, this, changeQuickRedirect, false, 8959, new Class[]{ConcurrentLinkedDeque.class, TCPChannelMapBean.class}, Void.TYPE).isSupported) {
            if (channel == null) {
                try {
                    a("checkStatusAndSendByPriority channel == null");
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            } else {
                if (taskList.size() > 0) {
                    if (channel.isConnectAndLogin()) {
                        TCPSendTaskBean peek = taskList.peek();
                        if (peek != null) {
                            int i2 = peek.priority;
                            if (i2 == 1) {
                                TCPSendTaskBean syncBarrier = new TCPSendTaskBean();
                                syncBarrier.priority = 99;
                                taskList.addFirst(syncBarrier);
                                A(peek);
                                taskList.remove(peek);
                                h("checkAndSendAnswer deviceId: " + channel.deviceId);
                            } else if (i2 != 99) {
                                D(taskList);
                            } else {
                                if (peek.timeOutDeadline < System.currentTimeMillis()) {
                                    a("超时清除同步屏障: 缓存数目：" + taskList.size());
                                    taskList.remove(peek);
                                    D(taskList);
                                }
                                h("同步屏障中: 缓存数目：" + taskList.size());
                            }
                        } else {
                            return;
                        }
                    }
                }
                h("checkAndSendAnswer deviceId: " + channel.deviceId + " connectStatue: " + channel.connectStatus + " loginStatue : " + channel.loginStatus);
            }
        }
    }

    private void D(ConcurrentLinkedDeque<TCPSendTaskBean> taskList) {
        if (!PatchProxy.proxy(new Object[]{taskList}, this, changeQuickRedirect, false, 8960, new Class[]{ConcurrentLinkedDeque.class}, Void.TYPE).isSupported) {
            h("sendOther");
            try {
                TCPSendTaskBean _item = taskList.poll();
                while (_item != null && _item.timeOutDeadline > System.currentTimeMillis()) {
                    A(_item);
                    _item = taskList.poll();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private void E() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8961, new Class[0], Void.TYPE).isSupported) {
            this.o = l.C(10, TimeUnit.SECONDS).X(new a(this));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: B */
    public /* synthetic */ void C(Long l2) {
        if (!PatchProxy.proxy(new Object[]{l2}, this, changeQuickRedirect, false, 8971, new Class[]{Long.class}, Void.TYPE).isSupported) {
            h("定时清除所有设备任务");
            for (String key : this.n.keySet()) {
                ConcurrentLinkedDeque<TCPSendTaskBean> taskList = this.n.get(key);
                if (taskList != null) {
                    x(taskList);
                }
                if (taskList.isEmpty()) {
                    this.n.remove(key);
                }
            }
            if (this.n.isEmpty()) {
                F();
            }
        }
    }

    private void F() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8962, new Class[0], Void.TYPE).isSupported) {
            h("停掉定时器");
            if (this.o != null) {
                this.o.dispose();
                this.o = null;
            }
        }
    }

    private void x(ConcurrentLinkedDeque<TCPSendTaskBean> taskList) {
        if (!PatchProxy.proxy(new Object[]{taskList}, this, changeQuickRedirect, false, 8963, new Class[]{ConcurrentLinkedDeque.class}, Void.TYPE).isSupported) {
            try {
                Iterator<TCPSendTaskBean> iterator = taskList.iterator();
                while (iterator.hasNext()) {
                    TCPSendTaskBean bean = iterator.next();
                    if (bean.timeOutDeadline < System.currentTimeMillis()) {
                        TcpService.INativeTcpSendMsgHandler iNativeTcpSendMsgHandler = bean.callback;
                        if (iNativeTcpSendMsgHandler != null) {
                            iNativeTcpSendMsgHandler.onSendMessageFail("消息超时(TCP)", 4080);
                        }
                        iterator.remove();
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void nativeSendMessageToTarget(String str, String str2, long j2, int i2, TcpService.INativeTcpSendMsgHandler iNativeTcpSendMsgHandler) {
        ConcurrentLinkedDeque<TCPSendTaskBean> taskList;
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{str, str2, new Long(j2), new Integer(i2), iNativeTcpSendMsgHandler}, this, changeQuickRedirect, false, 8964, new Class[]{cls, cls, Long.TYPE, Integer.TYPE, TcpService.INativeTcpSendMsgHandler.class}, Void.TYPE).isSupported) {
            String msg = str2;
            TcpService.INativeTcpSendMsgHandler callback = iNativeTcpSendMsgHandler;
            String deviceId = str;
            long timeOutLimitOfMs = j2;
            int priority = i2;
            int taskId = w();
            TCPChannelMapBean _channel = this.m.get(deviceId);
            ConcurrentLinkedDeque<TCPSendTaskBean> taskList2 = this.n.get(deviceId);
            if (taskList2 == null) {
                ConcurrentLinkedDeque<TCPSendTaskBean> taskList3 = new ConcurrentLinkedDeque<>();
                this.n.put(deviceId, taskList3);
                taskList = taskList3;
            } else {
                taskList = taskList2;
            }
            x(taskList);
            TCPSendTaskBean _task = new TCPSendTaskBean();
            _task.taskId = taskId;
            _task.deviceId = deviceId;
            _task.messageBody = msg;
            _task.callback = callback;
            _task.channel = _channel;
            _task.priority = priority;
            _task.timeOutDeadline = System.currentTimeMillis() + (timeOutLimitOfMs > 0 ? timeOutLimitOfMs : 10000);
            if (priority == 1) {
                taskList.addFirst(_task);
            } else {
                taskList.offer(_task);
            }
            if (this.o == null) {
                synchronized (TcpServiceImpl.class) {
                    if (this.o == null) {
                        h("启动定时器" + Thread.currentThread());
                        E();
                    }
                }
            }
            h("累计缓存: " + taskList.size());
            v(taskList, _channel);
        }
    }

    private void A(TCPSendTaskBean _task) {
        String seq;
        if (!PatchProxy.proxy(new Object[]{_task}, this, changeQuickRedirect, false, 8965, new Class[]{TCPSendTaskBean.class}, Void.TYPE).isSupported) {
            try {
                if (_task.callback == null) {
                    a("消息回调句柄不能为空....(终止执行)");
                    return;
                }
                if (_task.channel == null) {
                    TCPChannelMapBean tCPChannelMapBean = this.m.get(_task.deviceId);
                    _task.channel = tCPChannelMapBean;
                    if (tCPChannelMapBean == null) {
                        a("nativeSendMessageToTarget 未找到对应设备 deviceId=" + _task.deviceId);
                        _task.callback.onSendMessageFail("nativeSendMessageToTarget 未找到对应设备 deviceId=" + _task.deviceId, 4080);
                        return;
                    }
                }
                h("nativeSendMessageToTarget 已找到目标设备");
                JSONObject _msgBody = new JSONObject(_task.messageBody);
                String seq2 = System.currentTimeMillis() + "";
                if (_msgBody.has("seq")) {
                    seq = _msgBody.getString("seq");
                } else {
                    seq = seq2;
                }
                NettyManager.f().h(_task.channel.sessionId, _task.messageBody, seq, _task.timeOutDeadline, new e(_task));
            } catch (Exception e2) {
                e2.printStackTrace();
                a("nativeSendMessageToTarget 发生异常=" + e2.toString());
                _task.callback.onSendMessageFail("nativeSendMessageToTarget 发生异常=" + e2.toString(), 4080);
            }
        }
    }

    public class e implements com.leedarson.tcp.callback.a<String> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ TCPSendTaskBean a;

        e(TCPSendTaskBean tCPSendTaskBean) {
            this.a = tCPSendTaskBean;
        }

        public /* bridge */ /* synthetic */ void a(Object obj, boolean z) {
            if (!PatchProxy.proxy(new Object[]{obj, new Byte(z ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 8989, new Class[]{Object.class, Boolean.TYPE}, Void.TYPE).isSupported) {
                b((String) obj, z);
            }
        }

        public void b(String s, boolean z) {
            TCPSendTaskBean peek;
            if (!PatchProxy.proxy(new Object[]{s, new Byte(z ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 8986, new Class[]{String.class, Boolean.TYPE}, Void.TYPE).isSupported) {
                this.a.callback.onSendMessageSuccess(s, 200);
                TcpServiceImpl tcpServiceImpl = TcpServiceImpl.this;
                TcpServiceImpl.s(tcpServiceImpl, "onSuccess s: " + s);
                ConcurrentLinkedDeque<TCPSendTaskBean> taskList = (ConcurrentLinkedDeque) TcpServiceImpl.this.n.get(this.a.deviceId);
                if (taskList != null) {
                    if (this.a.priority == 1 && (peek = taskList.peek()) != null && peek.priority == 99) {
                        taskList.remove(peek);
                    }
                    TcpServiceImpl.u(TcpServiceImpl.this, taskList);
                }
            }
        }

        public void onFailure(int i, String errorStr) {
            Object[] objArr = {new Integer(i), errorStr};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8987, new Class[]{Integer.TYPE, String.class}, Void.TYPE).isSupported) {
                this.a.callback.onSendMessageFail(errorStr, 4080);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0024, code lost:
        r0 = r9;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onNativeReceiveTcpMessage(java.lang.String r10, java.lang.String r11) {
        /*
            r9 = this;
            java.lang.Class<java.lang.String> r0 = java.lang.String.class
            r1 = 2
            java.lang.Object[] r2 = new java.lang.Object[r1]
            r3 = 0
            r2[r3] = r10
            r4 = 1
            r2[r4] = r11
            com.meituan.robust.ChangeQuickRedirect r5 = changeQuickRedirect
            java.lang.Class[] r7 = new java.lang.Class[r1]
            r7[r3] = r0
            r7[r4] = r0
            java.lang.Class r8 = java.lang.Void.TYPE
            r0 = 0
            r6 = 8966(0x2306, float:1.2564E-41)
            r3 = r9
            r4 = r5
            r5 = r0
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r2, r3, r4, r5, r6, r7, r8)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0024
            return
        L_0x0024:
            r0 = r9
            com.leedarson.serviceimpl.tcp.beans.TCPChannelMapBean r1 = r0.y(r10)
            if (r1 == 0) goto L_0x0065
            java.lang.String r2 = "peerid"
            boolean r2 = r11.contains(r2)     // Catch:{ Exception -> 0x004c }
            if (r2 == 0) goto L_0x004b
            com.alibaba.android.arouter.launcher.a r2 = com.alibaba.android.arouter.launcher.a.c()     // Catch:{ Exception -> 0x004c }
            java.lang.Class<com.leedarson.serviceinterface.IpcService> r3 = com.leedarson.serviceinterface.IpcService.class
            java.lang.Object r2 = r2.g(r3)     // Catch:{ Exception -> 0x004c }
            com.leedarson.serviceinterface.IpcService r2 = (com.leedarson.serviceinterface.IpcService) r2     // Catch:{ Exception -> 0x004c }
            if (r2 == 0) goto L_0x004b
            java.lang.String r3 = r1.deviceId     // Catch:{ Exception -> 0x004c }
            org.json.JSONObject r4 = new org.json.JSONObject     // Catch:{ Exception -> 0x004c }
            r4.<init>((java.lang.String) r11)     // Catch:{ Exception -> 0x004c }
            r2.notifyOfSignalMessageComing(r3, r4)     // Catch:{ Exception -> 0x004c }
        L_0x004b:
            goto L_0x0065
        L_0x004c:
            r2 = move-exception
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "收到Tcp 回送消息--->数据转化异常: exception="
            r3.append(r4)
            java.lang.String r4 = r2.toString()
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            r0.a(r3)
        L_0x0065:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.tcp.TcpServiceImpl.onNativeReceiveTcpMessage(java.lang.String, java.lang.String):void");
    }

    public int w() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8967, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        return Integer.parseInt((System.currentTimeMillis() + "").substring(4));
    }

    public boolean getTcpConnectStatus(String deviceId) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{deviceId}, this, changeQuickRedirect, false, 8968, new Class[]{String.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        TCPChannelMapBean _channel = this.m.get(deviceId);
        if (_channel != null) {
            return _channel.isConnectAndLogin();
        }
        return false;
    }

    private void a(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 8969, new Class[]{String.class}, Void.TYPE).isSupported) {
            a.b g2 = timber.log.a.g("TcpServiceImpl");
            g2.c("TCP==> " + msg, new Object[0]);
        }
    }

    private void h(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 8970, new Class[]{String.class}, Void.TYPE).isSupported) {
            a.b g2 = timber.log.a.g("TcpServiceImpl");
            g2.h("TCP==> " + msg, new Object[0]);
        }
    }
}
