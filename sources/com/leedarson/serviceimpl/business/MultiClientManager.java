package com.leedarson.serviceimpl.business;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.HashMap;
import timber.log.a;

public class MultiClientManager {
    public static ChangeQuickRedirect changeQuickRedirect;
    private HashMap<String, BleBusinessClient> map = new HashMap<>();

    public BleBusinessClient getClient(String mac) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{mac}, this, changeQuickRedirect, false, 7050, new Class[]{String.class}, BleBusinessClient.class);
        if (proxy.isSupported) {
            return (BleBusinessClient) proxy.result;
        }
        if (this.map.containsKey(mac)) {
            a.b g = a.g("multiclient");
            g.m("BleBusiness.auto   111 getClient mac=" + mac + "  clientHash=" + this.map.get(mac).toString(), new Object[0]);
            return this.map.get(mac);
        } else if (this.map.containsKey(mac.toUpperCase())) {
            return this.map.get(mac.toUpperCase());
        } else {
            BleBusinessClient client = new BleBusinessClient(mac);
            this.map.put(mac, client);
            a.b g2 = a.g("multiclient");
            g2.m("BleBusiness.auto   2222 getClient mac=" + mac + "  clientHash=" + this.map.get(mac).toString(), new Object[0]);
            return client;
        }
    }

    public void reset(String mac) {
        if (!PatchProxy.proxy(new Object[]{mac}, this, changeQuickRedirect, false, 7051, new Class[]{String.class}, Void.TYPE).isSupported) {
            if (this.map.containsKey(mac)) {
                a.b g = a.g("multiclient");
                g.m("BleBusiness.auto.MultiClientManager   2222 reset mac=" + mac + "  clientHash=" + this.map.get(mac).toString(), new Object[0]);
                this.map.get(mac).reset();
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void disConnectAllDevices() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 7052(0x1b8c, float:9.882E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            java.util.HashMap<java.lang.String, com.leedarson.serviceimpl.business.BleBusinessClient> r1 = r0.map
            if (r1 == 0) goto L_0x003d
            java.util.Set r1 = r1.keySet()
            java.util.Iterator r1 = r1.iterator()
        L_0x0023:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x003d
            java.lang.Object r2 = r1.next()
            java.lang.String r2 = (java.lang.String) r2
            java.util.HashMap<java.lang.String, com.leedarson.serviceimpl.business.BleBusinessClient> r3 = r0.map
            java.lang.Object r3 = r3.get(r2)
            com.leedarson.serviceimpl.business.BleBusinessClient r3 = (com.leedarson.serviceimpl.business.BleBusinessClient) r3
            java.lang.String r4 = ""
            r3.disconnect(r4)
            goto L_0x0023
        L_0x003d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.business.MultiClientManager.disConnectAllDevices():void");
    }
}
