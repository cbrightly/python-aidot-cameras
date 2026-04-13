package com.leedarson.serviceimpl.udp;

import android.content.Context;
import android.net.Network;
import com.leedarson.bean.Constants;
import com.leedarson.serviceimpl.udp.manager.b;
import com.leedarson.serviceinterface.UdpService;
import com.leedarson.serviceinterface.event.JsCallH5ByNativeEvent;
import com.leedarson.serviceinterface.event.ScreenStatusReceiveEvent;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import org.greenrobot.eventbus.c;
import org.greenrobot.eventbus.l;

public class UdpServiceImpl implements UdpService {
    public static ChangeQuickRedirect changeQuickRedirect;
    private Context a;
    private boolean b = true;
    io.reactivex.disposables.b c;

    public void handleData(String action, String data) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(new Object[]{action, data}, this, changeQuickRedirect, false, 9096, clsArr, Void.TYPE).isSupported) {
            a(action, data);
        }
    }

    public void setNetWork(Network network) {
        if (!PatchProxy.proxy(new Object[]{network}, this, changeQuickRedirect, false, 9097, new Class[]{Network.class}, Void.TYPE).isSupported) {
            com.leedarson.serviceimpl.udp.manager.b.k().q(network);
        }
    }

    public void removeNetWork() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9098, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.serviceimpl.udp.manager.b.k().m();
        }
    }

    public void init(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 9099, new Class[]{Context.class}, Void.TYPE).isSupported) {
            this.a = context;
            c.c().p(this);
        }
    }

    /*  JADX ERROR: NullPointerException in pass: CodeShrinkVisitor
        java.lang.NullPointerException
        */
    private void a(java.lang.String r14, java.lang.String r15) {
        /*
            r13 = this;
            java.lang.String r0 = "isEncrypt"
            java.lang.String r1 = ""
            java.lang.Class<java.lang.String> r2 = java.lang.String.class
            r3 = 2
            java.lang.Object[] r4 = new java.lang.Object[r3]
            r11 = 0
            r4[r11] = r14
            r12 = 1
            r4[r12] = r15
            com.meituan.robust.ChangeQuickRedirect r6 = changeQuickRedirect
            java.lang.Class[] r9 = new java.lang.Class[r3]
            r9[r11] = r2
            r9[r12] = r2
            java.lang.Class r10 = java.lang.Void.TYPE
            r7 = 0
            r8 = 9100(0x238c, float:1.2752E-41)
            r5 = r13
            com.meituan.robust.PatchProxyResult r2 = com.meituan.robust.PatchProxy.proxy(r4, r5, r6, r7, r8, r9, r10)
            boolean r2 = r2.isSupported
            if (r2 == 0) goto L_0x0026
            return
        L_0x0026:
            r2 = r13
            r4 = -1
            int r5 = r14.hashCode()
            switch(r5) {
                case -1102508601: goto L_0x0043;
                case 3526536: goto L_0x0039;
                case 94756344: goto L_0x0030;
                default: goto L_0x002f;
            }
        L_0x002f:
            goto L_0x004d
        L_0x0030:
            java.lang.String r5 = "close"
            boolean r5 = r14.equals(r5)
            if (r5 == 0) goto L_0x002f
            goto L_0x004e
        L_0x0039:
            java.lang.String r3 = "send"
            boolean r3 = r14.equals(r3)
            if (r3 == 0) goto L_0x002f
            r3 = r11
            goto L_0x004e
        L_0x0043:
            java.lang.String r3 = "listen"
            boolean r3 = r14.equals(r3)
            if (r3 == 0) goto L_0x002f
            r3 = r12
            goto L_0x004e
        L_0x004d:
            r3 = r4
        L_0x004e:
            java.lang.String r4 = "port"
            switch(r3) {
                case 0: goto L_0x007e;
                case 1: goto L_0x005e;
                case 2: goto L_0x0055;
                default: goto L_0x0053;
            }
        L_0x0053:
            goto L_0x0116
        L_0x0055:
            com.leedarson.serviceimpl.udp.manager.b r0 = com.leedarson.serviceimpl.udp.manager.b.k()
            r0.i()
            goto L_0x0116
        L_0x005e:
            com.google.gson.internal.LinkedTreeMap r0 = com.leedarson.serviceimpl.udp.manager.a.a(r15)     // Catch:{ Exception -> 0x0078 }
            r1 = 0
            if (r0 == 0) goto L_0x0076
            java.lang.Object r3 = r0.get(r4)     // Catch:{ Exception -> 0x0078 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x0078 }
            double r3 = java.lang.Double.parseDouble(r3)     // Catch:{ Exception -> 0x0078 }
            int r1 = (int) r3     // Catch:{ Exception -> 0x0078 }
            r3 = 0
            r2.commonBind(r1, r3)     // Catch:{ Exception -> 0x0078 }
        L_0x0076:
            goto L_0x0116
        L_0x0078:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0116
        L_0x007e:
            boolean r3 = r2.b
            if (r3 != 0) goto L_0x0090
            java.lang.String r0 = "UdpServiceImpl"
            timber.log.a$b r0 = timber.log.a.g(r0)
            java.lang.Object[] r1 = new java.lang.Object[r11]
            java.lang.String r3 = "锁屏不发送UDP"
            r0.h(r3, r1)
            return
        L_0x0090:
            com.google.gson.internal.LinkedTreeMap r3 = com.leedarson.serviceimpl.udp.manager.a.a(r15)     // Catch:{ Exception -> 0x0111 }
            r5 = r1
            r6 = 0
            if (r3 == 0) goto L_0x0110
            java.lang.String r7 = "ip"
            java.lang.Object r7 = r3.get(r7)     // Catch:{ Exception -> 0x0111 }
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x0111 }
            r5 = r7
            boolean r7 = r3.containsKey(r4)     // Catch:{ Exception -> 0x0111 }
            if (r7 == 0) goto L_0x00b9
            java.lang.Object r4 = r3.get(r4)     // Catch:{ Exception -> 0x0111 }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x0111 }
            double r7 = java.lang.Double.parseDouble(r4)     // Catch:{ Exception -> 0x0111 }
            int r6 = (int) r7     // Catch:{ Exception -> 0x0111 }
            r10 = r6
            goto L_0x00ba
        L_0x00b9:
            r10 = r6
        L_0x00ba:
            java.lang.String r4 = "message"
            java.lang.Object r4 = r3.get(r4)     // Catch:{ Exception -> 0x0111 }
            java.lang.String r8 = com.leedarson.serviceimpl.udp.manager.a.b(r4)     // Catch:{ Exception -> 0x0111 }
            r1 = 0
            boolean r4 = r3.containsKey(r0)     // Catch:{ Exception -> 0x0111 }
            if (r4 == 0) goto L_0x00dd
            java.lang.Object r0 = r3.get(r0)     // Catch:{ Exception -> 0x0111 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0111 }
            double r6 = java.lang.Double.parseDouble(r0)     // Catch:{ Exception -> 0x0111 }
            int r0 = (int) r6     // Catch:{ Exception -> 0x0111 }
            if (r0 != r12) goto L_0x00dc
            r1 = 1
            goto L_0x00dd
        L_0x00dc:
            r1 = 0
        L_0x00dd:
            com.google.gson.internal.LinkedTreeMap r0 = com.leedarson.serviceimpl.udp.manager.a.a(r8)     // Catch:{ Exception -> 0x0111 }
            java.lang.String r4 = "method"
            java.lang.Object r4 = r0.get(r4)     // Catch:{ Exception -> 0x0111 }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x0111 }
            r11 = r4
            java.lang.String r4 = "devDiscoveryReq"
            boolean r4 = r11.equals(r4)     // Catch:{ Exception -> 0x0111 }
            if (r4 != 0) goto L_0x00fc
            java.lang.String r4 = "discovery_req"
            boolean r4 = r11.equals(r4)     // Catch:{ Exception -> 0x0111 }
            if (r4 == 0) goto L_0x0107
        L_0x00fc:
            boolean r4 = android.text.TextUtils.isEmpty(r5)     // Catch:{ Exception -> 0x0111 }
            if (r4 == 0) goto L_0x0107
            java.lang.String r4 = "255.255.255.255"
            r5 = r4
            r12 = r5
            goto L_0x0108
        L_0x0107:
            r12 = r5
        L_0x0108:
            r9 = 0
            r4 = r2
            r5 = r1
            r6 = r12
            r7 = r10
            r4.sendUdpMessage(r5, r6, r7, r8, r9)     // Catch:{ Exception -> 0x0111 }
        L_0x0110:
            goto L_0x0116
        L_0x0111:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0116:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.udp.UdpServiceImpl.a(java.lang.String, java.lang.String):void");
    }

    public class a implements b.d {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void a(String str, int i, String message) {
            Class<String> cls = String.class;
            Object[] objArr = {str, new Integer(i), message};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9105, new Class[]{cls, Integer.TYPE, cls}, Void.TYPE).isSupported) {
                c.c().l(new JsCallH5ByNativeEvent(Constants.SERVICE_UDP_NEW, "onMessage", message));
            }
        }
    }

    public void sendUdpMessage(boolean isEncrypt, String host, int port, String message, byte[] msgBytes) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{new Byte(isEncrypt ? (byte) 1 : 0), host, new Integer(port), message, msgBytes}, this, changeQuickRedirect, false, 9101, new Class[]{Boolean.TYPE, cls, Integer.TYPE, cls, byte[].class}, Void.TYPE).isSupported) {
            com.leedarson.serviceimpl.udp.manager.b k = com.leedarson.serviceimpl.udp.manager.b.k();
            k.o(isEncrypt, host, port, message, msgBytes, new a());
        }
    }

    public class b implements b.d {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ UdpService.CommonUdpCallback a;

        b(UdpService.CommonUdpCallback commonUdpCallback) {
            this.a = commonUdpCallback;
        }

        public void a(String str, int i, String message) {
            Class<String> cls = String.class;
            Object[] objArr = {str, new Integer(i), message};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9106, new Class[]{cls, Integer.TYPE, cls}, Void.TYPE).isSupported) {
                c.c().l(new JsCallH5ByNativeEvent(Constants.SERVICE_UDP_NEW, "onMessage", message));
                UdpService.CommonUdpCallback commonUdpCallback = this.a;
                if (commonUdpCallback != null) {
                    commonUdpCallback.onBindSuccess("", message);
                }
            }
        }
    }

    public void commonBind(int port, UdpService.CommonUdpCallback commonUdpCallback) {
        Object[] objArr = {new Integer(port), commonUdpCallback};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9102, new Class[]{Integer.TYPE, UdpService.CommonUdpCallback.class}, Void.TYPE).isSupported) {
            com.leedarson.serviceimpl.udp.manager.b.k().h(port, new b(commonUdpCallback));
        }
    }

    @l
    public void onScreenStatusChange(ScreenStatusReceiveEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 9103, new Class[]{ScreenStatusReceiveEvent.class}, Void.TYPE).isSupported) {
            if (event.screenOn) {
                timber.log.a.g("UdpServiceImpl").h("屏幕点亮", new Object[0]);
            } else {
                timber.log.a.g("UdpServiceImpl").h("屏幕熄灭", new Object[0]);
            }
            this.b = event.screenOn;
        }
    }

    public void unInit() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9104, new Class[0], Void.TYPE).isSupported) {
            c.c().r(this);
            io.reactivex.disposables.b bVar = this.c;
            if (bVar != null && !bVar.isDisposed()) {
                this.c.dispose();
            }
        }
    }
}
