package com.leedarson.smartcamera.kvswebrtc.utils;

import com.didichuxing.doraemonkit.okgo.cookie.SerializableCookie;
import com.leedarson.base.webservice.utils.a;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import timber.log.a;

/* compiled from: CandidateUtil */
public class b {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static boolean b(String ip) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{ip}, (Object) null, changeQuickRedirect, true, 10102, new Class[]{String.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (!a.b(ip)) {
            return false;
        }
        String[] ipStringArray = ip.split("\\.");
        int[] ipIntArray = {Integer.parseInt(ipStringArray[0]), Integer.parseInt(ipStringArray[1])};
        return ipIntArray[0] == 10 || (ipIntArray[0] == 172 && ipIntArray[1] >= 16 && ipIntArray[1] <= 31) || (ipIntArray[0] == 192 && ipIntArray[1] == 168);
    }

    public static String a(String localCandidate, String remoteCandidate) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{localCandidate, remoteCandidate}, (Object) null, changeQuickRedirect, true, 10103, new Class[]{cls, cls}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        String[] localFields = localCandidate.split(" ");
        String[] remoteFields = remoteCandidate.split(" ");
        if (localFields.length < 8 || remoteFields.length < 8) {
            return "unknown";
        }
        String localAddress = localFields[4];
        String remoteAddress = remoteFields[4];
        String lType = localFields[7];
        String rType = remoteFields[7];
        a.b g = timber.log.a.g("getIceType");
        g.a("lType:" + lType + " rType:" + rType + " localAddress:" + localAddress + " remoteAddress:" + remoteAddress, new Object[0]);
        if (lType.equals("relay") || rType.equals("relay")) {
            return "Relay";
        }
        if (!lType.equals(SerializableCookie.HOST) || !rType.equals(SerializableCookie.HOST)) {
            if (lType.equals("srflx") || rType.equals("srflx") || !b(localAddress) || !b(remoteAddress)) {
                return "P2P";
            }
            return "Lan";
        } else if (!b(localAddress) || !b(remoteAddress)) {
            return "P2P";
        } else {
            return "Lan";
        }
    }
}
