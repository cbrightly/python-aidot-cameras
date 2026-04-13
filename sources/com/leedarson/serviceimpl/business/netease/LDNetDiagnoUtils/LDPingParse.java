package com.leedarson.serviceimpl.business.netease.LDNetDiagnoUtils;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LDPingParse {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static String getFormattingStr(String host, String log) {
        Class<String> cls = String.class;
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{host, log}, (Object) null, changeQuickRedirect2, true, 7222, new Class[]{cls, cls}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        StringBuilder logRes = new StringBuilder();
        if (log.contains("timeout")) {
            logRes.append("ping: cannot resolve " + host + ": Timeout");
        } else if (log.contains("unknown")) {
            logRes.append("ping: cannot resolve " + host + ": Unknown host");
        } else {
            makePingResponse(log, logRes);
        }
        return logRes.toString();
    }

    public static void makePingResponse(String log, StringBuilder logRes) {
        Class[] clsArr = {String.class, StringBuilder.class};
        if (!PatchProxy.proxy(new Object[]{log, logRes}, (Object) null, changeQuickRedirect, true, 7223, clsArr, Void.TYPE).isSupported) {
            String hostIp = getIP(log);
            List<String> bytesList = getSumBytes(log);
            List<String> ttlList = getTTL(log);
            List<String> timeList = getTime(log);
            List<String> icmpList = getIcmp_seq(log);
            int len = timeList.size();
            for (int i = 0; i < len - 1; i++) {
                logRes.append(bytesList.get(i) + "bytes from " + hostIp + ": icmp_seq=#" + icmpList.get(i) + " ttl=" + ttlList.get(i) + " time=" + timeList.get(i) + "ms\n");
            }
            logRes.append(bytesList.get(len - 1) + "bytes from " + hostIp + ": icmp_seq=#" + icmpList.get(len - 1) + " ttl=" + ttlList.get(len - 1) + " time=" + timeList.get(len - 1) + "ms");
        }
    }

    private static List<String> getTime(String log) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{log}, (Object) null, changeQuickRedirect, true, 7224, new Class[]{String.class}, List.class);
        if (proxy.isSupported) {
            return (List) proxy.result;
        }
        List<String> timeList = new ArrayList<>();
        Matcher m = Pattern.compile("(?<==)([\\.0-9\\s]+)(?=ms)").matcher(log);
        while (m.find()) {
            timeList.add(m.group().toString().trim());
        }
        return timeList;
    }

    private static List<String> getSumBytes(String log) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{log}, (Object) null, changeQuickRedirect, true, 7225, new Class[]{String.class}, List.class);
        if (proxy.isSupported) {
            return (List) proxy.result;
        }
        List<String> bytesList = new ArrayList<>();
        Matcher m = Pattern.compile("(?<=\\D)([\\s0-9]+)(?=bytes)").matcher(log);
        while (m.find()) {
            String string = m.group().toString().trim();
            if (m.group().toString().trim().matches("\\d+")) {
                bytesList.add(string);
            }
        }
        return bytesList;
    }

    private static List<String> getTTL(String log) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{log}, (Object) null, changeQuickRedirect, true, 7226, new Class[]{String.class}, List.class);
        if (proxy.isSupported) {
            return (List) proxy.result;
        }
        List<String> ttlList = new ArrayList<>();
        Matcher m = Pattern.compile("(?<=ttl=)([0-9]+)(?=\\s)").matcher(log);
        while (m.find()) {
            ttlList.add(m.group().toString().trim());
        }
        return ttlList;
    }

    private static String getIP(String log) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{log}, (Object) null, changeQuickRedirect, true, 7227, new Class[]{String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        String hostIp = null;
        Matcher m = Pattern.compile("(?<=\\()([\\d]+\\.)+[\\d]+(?=\\))").matcher(log);
        while (m.find()) {
            hostIp = m.group().toString().trim();
        }
        return hostIp;
    }

    private static List<String> getIcmp_seq(String log) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{log}, (Object) null, changeQuickRedirect, true, 7228, new Class[]{String.class}, List.class);
        if (proxy.isSupported) {
            return (List) proxy.result;
        }
        List<String> icmpList = new ArrayList<>();
        Matcher m = Pattern.compile("(?<=icmp_seq=)([0-9]+)(?=\\s)").matcher(log);
        while (m.find()) {
            icmpList.add(m.group().toString().trim());
        }
        return icmpList;
    }
}
