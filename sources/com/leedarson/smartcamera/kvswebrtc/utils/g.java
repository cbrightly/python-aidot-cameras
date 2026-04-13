package com.leedarson.smartcamera.kvswebrtc.utils;

import android.text.TextUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import java.util.Map;
import tv.danmaku.ijk.media.player.misc.IjkMediaFormat;

/* compiled from: OfferUtils */
public class g {
    public static ChangeQuickRedirect changeQuickRedirect;

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String b(java.lang.String r11) {
        /*
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r8 = 0
            r1[r8] = r11
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<java.lang.String> r2 = java.lang.String.class
            r6[r8] = r2
            java.lang.Class<java.lang.String> r7 = java.lang.String.class
            r2 = 0
            r4 = 1
            r5 = 10142(0x279e, float:1.4212E-41)
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r2 = r1.isSupported
            if (r2 == 0) goto L_0x0021
            java.lang.Object r11 = r1.result
            java.lang.String r11 = (java.lang.String) r11
            return r11
        L_0x0021:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.util.HashMap r2 = new java.util.HashMap
            r3 = 15
            r4 = 1065353216(0x3f800000, float:1.0)
            r2.<init>(r3, r4)
            r3 = 1
            java.io.InputStreamReader r4 = new java.io.InputStreamReader
            java.io.ByteArrayInputStream r5 = new java.io.ByteArrayInputStream
            java.nio.charset.Charset r6 = java.nio.charset.StandardCharsets.UTF_8
            byte[] r6 = r11.getBytes(r6)
            r5.<init>(r6)
            r4.<init>(r5)
            java.io.BufferedReader r5 = new java.io.BufferedReader
            r5.<init>(r4)
        L_0x0045:
            java.lang.String r6 = r5.readLine()     // Catch:{ IOException -> 0x00d0 }
            if (r6 != 0) goto L_0x0051
            java.lang.String r0 = r1.toString()
            return r0
        L_0x0051:
            java.lang.String r7 = "m="
            boolean r7 = r6.contains(r7)     // Catch:{ IOException -> 0x00d0 }
            java.lang.String r9 = "\r\n"
            java.lang.String r10 = "mediaType"
            if (r7 == 0) goto L_0x0070
            r3 = 0
            r1.append(r6)     // Catch:{ IOException -> 0x00d0 }
            r1.append(r9)     // Catch:{ IOException -> 0x00d0 }
            java.lang.String r7 = " "
            java.lang.String[] r7 = r6.split(r7)     // Catch:{ IOException -> 0x00d0 }
            r7 = r7[r8]     // Catch:{ IOException -> 0x00d0 }
            r2.put(r10, r7)     // Catch:{ IOException -> 0x00d0 }
            goto L_0x0045
        L_0x0070:
            if (r3 == 0) goto L_0x0082
            java.lang.String r7 = "s="
            boolean r7 = r6.contains(r7)     // Catch:{ IOException -> 0x00d0 }
            if (r7 == 0) goto L_0x0082
            r1.append(r6)     // Catch:{ IOException -> 0x00d0 }
            r1.append(r9)     // Catch:{ IOException -> 0x00d0 }
            goto L_0x0045
        L_0x0082:
            java.lang.Object r7 = r2.get(r10)     // Catch:{ IOException -> 0x00d0 }
            java.lang.String r7 = (java.lang.String) r7     // Catch:{ IOException -> 0x00d0 }
            boolean r9 = android.text.TextUtils.isEmpty(r7)     // Catch:{ IOException -> 0x00d0 }
            if (r9 == 0) goto L_0x008f
            goto L_0x0045
        L_0x008f:
            r9 = -1
            int r10 = r7.hashCode()     // Catch:{ IOException -> 0x00d0 }
            switch(r10) {
                case -1077792288: goto L_0x00ac;
                case -206801818: goto L_0x00a2;
                case -187765493: goto L_0x0098;
                default: goto L_0x0097;
            }     // Catch:{ IOException -> 0x00d0 }
        L_0x0097:
            goto L_0x00b5
        L_0x0098:
            java.lang.String r10 = "m=video"
            boolean r10 = r7.equals(r10)     // Catch:{ IOException -> 0x00d0 }
            if (r10 == 0) goto L_0x0097
            r9 = r0
            goto L_0x00b5
        L_0x00a2:
            java.lang.String r10 = "m=audio"
            boolean r10 = r7.equals(r10)     // Catch:{ IOException -> 0x00d0 }
            if (r10 == 0) goto L_0x0097
            r9 = r8
            goto L_0x00b5
        L_0x00ac:
            java.lang.String r10 = "m=application"
            boolean r10 = r7.equals(r10)     // Catch:{ IOException -> 0x00d0 }
            if (r10 == 0) goto L_0x0097
            r9 = 2
        L_0x00b5:
            switch(r9) {
                case 0: goto L_0x00c1;
                case 1: goto L_0x00bd;
                case 2: goto L_0x00b9;
                default: goto L_0x00b8;
            }     // Catch:{ IOException -> 0x00d0 }
        L_0x00b8:
            goto L_0x00c5
        L_0x00b9:
            c(r6, r1)     // Catch:{ IOException -> 0x00d0 }
            goto L_0x00c5
        L_0x00bd:
            h(r6, r1, r2)     // Catch:{ IOException -> 0x00d0 }
            goto L_0x00c5
        L_0x00c1:
            d(r6, r1)     // Catch:{ IOException -> 0x00d0 }
        L_0x00c5:
            f(r6, r1)     // Catch:{ IOException -> 0x00d0 }
            e(r6, r1, r2)     // Catch:{ IOException -> 0x00d0 }
            g(r6, r1, r2)     // Catch:{ IOException -> 0x00d0 }
            goto L_0x0045
        L_0x00d0:
            r0 = move-exception
            r0.printStackTrace()
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.smartcamera.kvswebrtc.utils.g.b(java.lang.String):java.lang.String");
    }

    private static void h(String line, StringBuilder sb, Map<String, String> atrMap) {
        if (!PatchProxy.proxy(new Object[]{line, sb, atrMap}, (Object) null, changeQuickRedirect, true, 10143, new Class[]{String.class, StringBuilder.class, Map.class}, Void.TYPE).isSupported) {
            if (atrMap.get("H264/90000") == null && line.contains("H264/90000")) {
                a(line, sb, atrMap, "H264/90000");
                atrMap.put(IjkMediaFormat.CODEC_NAME_H264, line.split(" ")[0].substring(9));
            } else if (atrMap.get("H265/90000") == null && line.contains("H265/90000")) {
                a(line, sb, atrMap, "H265/90000");
                atrMap.put("h265", line.split(" ")[0].substring(9));
            } else if (line.contains("apt=")) {
                String aptVal = line.split(" ")[1].substring(4);
                String h264Apt = atrMap.get(IjkMediaFormat.CODEC_NAME_H264);
                if (atrMap.get("h264Apt") == null && !TextUtils.isEmpty(h264Apt) && h264Apt.equals(aptVal)) {
                    a(line, sb, atrMap, "h264Apt");
                }
                String h265Apt = atrMap.get("h265");
                if (atrMap.get("h265Apt") == null && !TextUtils.isEmpty(h265Apt) && h265Apt.equals(aptVal)) {
                    a(line, sb, atrMap, "h265Apt");
                }
            } else if (atrMap.get("profile-level") == null && line.contains("fmtp") && line.contains("profile-level-id")) {
                String h264Apt2 = atrMap.get(IjkMediaFormat.CODEC_NAME_H264);
                String aptVal2 = line.split(" ")[0].substring(7);
                if (h264Apt2 != null && h264Apt2.equals(aptVal2)) {
                    a(line, sb, atrMap, "profile-level");
                }
            }
        }
    }

    private static void c(String line, StringBuilder sb) {
        Class[] clsArr = {String.class, StringBuilder.class};
        if (!PatchProxy.proxy(new Object[]{line, sb}, (Object) null, changeQuickRedirect, true, 10144, clsArr, Void.TYPE).isSupported && line.contains("sctp-port")) {
            sb.append(line);
            sb.append("\r\n");
        }
    }

    private static void e(String line, StringBuilder sb, Map<String, String> atrMap) {
        Class[] clsArr = {String.class, StringBuilder.class, Map.class};
        if (!PatchProxy.proxy(new Object[]{line, sb, atrMap}, (Object) null, changeQuickRedirect, true, 10145, clsArr, Void.TYPE).isSupported) {
            if (atrMap.get("ice-ufrag") == null && line.contains("ice-ufrag")) {
                a(line, sb, atrMap, "ice-ufrag");
            } else if (atrMap.get("ice-pwd") == null && line.contains("ice-pwd")) {
                a(line, sb, atrMap, "ice-pwd");
            } else if (atrMap.get("fingerprint") == null && line.contains("fingerprint")) {
                a(line, sb, atrMap, "fingerprint");
            } else if (atrMap.get("setup") == null && line.contains("setup")) {
                a(line, sb, atrMap, "setup");
            } else if (atrMap.get("ice-options") == null && line.contains("ice-options")) {
                a(line, sb, atrMap, "ice-options");
            } else if (line.contains("candidate") && (line.contains(" udp ") || line.contains(" UDP "))) {
                sb.append(line);
                sb.append("\r\n");
            } else if (line.contains("crypto")) {
                a(line, sb, atrMap, "crypto");
            }
        }
    }

    private static void g(String line, StringBuilder sb, Map<String, String> atrMap) {
        Class[] clsArr = {String.class, StringBuilder.class, Map.class};
        if (!PatchProxy.proxy(new Object[]{line, sb, atrMap}, (Object) null, changeQuickRedirect, true, 10146, clsArr, Void.TYPE).isSupported) {
            if (atrMap.get("http://www.ietf.org/id/draft-holmer-rmcat-transport-wide-cc-extensions-01") == null && line.contains("http://www.ietf.org/id/draft-holmer-rmcat-transport-wide-cc-extensions-01")) {
                a(line, sb, atrMap, "http://www.ietf.org/id/draft-holmer-rmcat-transport-wide-cc-extensions-01");
            } else if (atrMap.get("urn:ietf:params:rtp-hdrext:sdes:rtp-stream-id") == null && line.contains("urn:ietf:params:rtp-hdrext:sdes:rtp-stream-id")) {
                a(line, sb, atrMap, "urn:ietf:params:rtp-hdrext:sdes:rtp-stream-id");
            }
        }
    }

    private static void d(String line, StringBuilder sb) {
        Class[] clsArr = {String.class, StringBuilder.class};
        if (!PatchProxy.proxy(new Object[]{line, sb}, (Object) null, changeQuickRedirect, true, 10147, clsArr, Void.TYPE).isSupported) {
            if (line.contains("opus") || line.contains("PCMU") || line.contains("PCMA") || line.contains("AAC")) {
                sb.append(line);
                sb.append("\r\n");
            }
        }
    }

    private static void f(String line, StringBuilder sb) {
        Class[] clsArr = {String.class, StringBuilder.class};
        if (!PatchProxy.proxy(new Object[]{line, sb}, (Object) null, changeQuickRedirect, true, 10148, clsArr, Void.TYPE).isSupported) {
            if (line.contains("a=ssrc") && line.contains("cname")) {
                sb.append(line);
                sb.append("\r\n");
            }
            if (line.contains("sendrecv") || line.contains("recvonly") || line.contains("sendonly")) {
                sb.append(line);
                sb.append("\r\n");
            }
        }
    }

    private static void a(String line, StringBuilder sb, Map<String, String> atrMap, String type) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{line, sb, atrMap, type}, (Object) null, changeQuickRedirect, true, 10149, new Class[]{cls, StringBuilder.class, Map.class, cls}, Void.TYPE).isSupported) {
            sb.append(line);
            sb.append("\r\n");
            atrMap.put(type, "1");
        }
    }
}
