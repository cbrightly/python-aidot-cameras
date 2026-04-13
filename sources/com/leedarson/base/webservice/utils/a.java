package com.leedarson.base.webservice.utils;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.regex.Pattern;

/* compiled from: NetUtils */
public class a {
    private static final Pattern a = Pattern.compile("^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$");
    public static ChangeQuickRedirect changeQuickRedirect;

    public static boolean b(String input) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{input}, (Object) null, changeQuickRedirect, true, 986, new Class[]{String.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : a.matcher(input).matches();
    }

    public static InetAddress a() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 987, new Class[0], InetAddress.class);
        if (proxy.isSupported) {
            return (InetAddress) proxy.result;
        }
        Enumeration<NetworkInterface> enumeration = null;
        try {
            enumeration = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        if (enumeration == null) {
            return null;
        }
        while (enumeration.hasMoreElements()) {
            Enumeration<InetAddress> inetAddresses = enumeration.nextElement().getInetAddresses();
            if (inetAddresses != null) {
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    if (!inetAddress.isLoopbackAddress() && b(inetAddress.getHostAddress())) {
                        return inetAddress;
                    }
                }
                continue;
            }
        }
        return null;
    }
}
