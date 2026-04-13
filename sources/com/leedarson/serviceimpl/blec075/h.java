package com.leedarson.serviceimpl.blec075;

import android.text.TextUtils;
import androidx.core.view.ViewCompat;
import com.leedarson.base.utils.b;
import com.leedarson.secret.JNIUtil;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.glassfish.grizzly.http.server.util.MappingData;

/* compiled from: Utils */
public class h {
    private static final char[] a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final String b = JNIUtil.getInstance().getStr12();
    public static ChangeQuickRedirect changeQuickRedirect;

    public static String b(byte[] bytes) {
        int a2;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{bytes}, (Object) null, changeQuickRedirect, true, 6450, new Class[]{byte[].class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (bytes == null) {
            return "";
        }
        char[] buf = new char[(bytes.length * 2)];
        int index = 0;
        for (byte b2 : bytes) {
            if (b2 < 0) {
                a2 = b2 + 256;
            } else {
                a2 = b2;
            }
            int index2 = index + 1;
            char[] cArr = a;
            buf[index] = cArr[a2 / 16];
            index = index2 + 1;
            buf[index2] = cArr[a2 % 16];
        }
        return new String(buf);
    }

    public static byte[] n(String str) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{str}, (Object) null, changeQuickRedirect, true, 6451, new Class[]{String.class}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        if (str == null || str.trim().equals("")) {
            return new byte[0];
        }
        byte[] bytes = new byte[(str.length() / 2)];
        for (int i = 0; i < str.length() / 2; i++) {
            bytes[i] = (byte) Integer.parseInt(str.substring(i * 2, (i * 2) + 2), 16);
        }
        return bytes;
    }

    public static byte[] h(byte[] seed) {
        byte[] raw = new byte[16];
        for (int i = 0; i < seed.length; i++) {
            raw[i] = seed[i];
        }
        return raw;
    }

    public static byte[] i(byte[] seed) {
        byte[] raw = new byte[32];
        for (int i = 0; i < seed.length; i++) {
            raw[i] = seed[i];
        }
        return raw;
    }

    @Deprecated
    public static byte[] d(String key, byte[] encrypted) {
        byte[] raw = h(key.getBytes());
        byte[] ss = new byte[(encrypted.length % 16 != 0 ? ((encrypted.length / 16) + 1) * 16 : encrypted.length)];
        for (int i = 0; i < encrypted.length; i++) {
            ss[i] = encrypted[i];
        }
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance(b);
        cipher.init(2, skeySpec);
        return cipher.doFinal(ss);
    }

    @Deprecated
    public static byte[] g(String key, byte[] encrypted) {
        SecretKeySpec skeySpec = new SecretKeySpec(i(key.getBytes()), "AES");
        Cipher cipher = Cipher.getInstance(b);
        cipher.init(1, skeySpec);
        return cipher.doFinal(encrypted);
    }

    @Deprecated
    public static byte[] e(String key, byte[] encrypted) {
        byte[] raw = i(key.getBytes());
        byte[] ss = new byte[(encrypted.length % 16 != 0 ? ((encrypted.length / 16) + 1) * 16 : encrypted.length)];
        for (int i = 0; i < encrypted.length; i++) {
            ss[i] = encrypted[i];
        }
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance(b);
        cipher.init(2, skeySpec);
        return cipher.doFinal(ss);
    }

    @Deprecated
    public static byte[] f(byte[] ivBytes, b.a aesType, String key, byte[] encrypted) {
        byte[] raw;
        byte[] ivOffset = ivBytes;
        if (ivOffset == null) {
            ivOffset = new byte[16];
        }
        IvParameterSpec ivSpec = new IvParameterSpec(ivOffset);
        if (aesType == b.a.AES256) {
            raw = i(key.getBytes());
        } else {
            raw = h(key.getBytes());
        }
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
        cipher.init(2, skeySpec, ivSpec);
        return cipher.doFinal(encrypted);
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=byte, code=int, for r3v1, types: [byte] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.util.Map<java.lang.Byte, com.leedarson.serviceimpl.blec075.h.a> k(byte[] r11) {
        /*
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r8 = 0
            r1[r8] = r11
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<byte[]> r2 = byte[].class
            r6[r8] = r2
            java.lang.Class<java.util.Map> r7 = java.util.Map.class
            r2 = 0
            r4 = 1
            r5 = 6453(0x1935, float:9.043E-42)
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r2 = r1.isSupported
            if (r2 == 0) goto L_0x0021
            java.lang.Object r11 = r1.result
            java.util.Map r11 = (java.util.Map) r11
            return r11
        L_0x0021:
            java.util.concurrent.ConcurrentHashMap r1 = new java.util.concurrent.ConcurrentHashMap
            r1.<init>()
            r2 = 0
            byte r3 = r11[r2]
        L_0x0029:
            if (r3 <= 0) goto L_0x005c
            int r4 = r3 + 1
            byte[] r4 = new byte[r4]     // Catch:{ Exception -> 0x0057 }
            int r5 = r4.length     // Catch:{ Exception -> 0x0057 }
            java.lang.System.arraycopy(r11, r2, r4, r8, r5)     // Catch:{ Exception -> 0x0057 }
            com.leedarson.serviceimpl.blec075.h$a r5 = new com.leedarson.serviceimpl.blec075.h$a     // Catch:{ Exception -> 0x0057 }
            byte r6 = r4[r8]     // Catch:{ Exception -> 0x0057 }
            byte r7 = r4[r0]     // Catch:{ Exception -> 0x0057 }
            r9 = 2
            int r10 = r4.length     // Catch:{ Exception -> 0x0057 }
            byte[] r9 = java.util.Arrays.copyOfRange(r4, r9, r10)     // Catch:{ Exception -> 0x0057 }
            r5.<init>(r6, r7, r9)     // Catch:{ Exception -> 0x0057 }
            byte r6 = r5.b     // Catch:{ Exception -> 0x0057 }
            java.lang.Byte r6 = java.lang.Byte.valueOf(r6)     // Catch:{ Exception -> 0x0057 }
            r1.put(r6, r5)     // Catch:{ Exception -> 0x0057 }
            int r6 = r2 + r3
            int r2 = r6 + 1
            int r6 = r11.length     // Catch:{ Exception -> 0x0057 }
            if (r2 < r6) goto L_0x0053
            goto L_0x005c
        L_0x0053:
            byte r6 = r11[r2]     // Catch:{ Exception -> 0x0057 }
            r3 = r6
            goto L_0x0029
        L_0x0057:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x005d
        L_0x005c:
        L_0x005d:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.blec075.h.k(byte[]):java.util.Map");
    }

    /* compiled from: Utils */
    public static class a {
        public static ChangeQuickRedirect changeQuickRedirect;
        public byte a;
        public byte b;
        public byte[] c;

        public a(byte dataUnitSize, byte dataUnitType, byte[] adData) {
            this.a = dataUnitSize;
            this.b = dataUnitType;
            this.c = adData;
        }

        public String toString() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6456, new Class[0], String.class);
            if (proxy.isSupported) {
                return (String) proxy.result;
            }
            return "{" + "\"dataUnitSize\":" + h.b(new byte[]{this.a}) + ",\"dataUnitType\":" + h.b(new byte[]{this.b}) + ",\"adData\":" + h.b(this.c) + '}';
        }
    }

    public static String l(String mac) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{mac}, (Object) null, changeQuickRedirect, true, 6454, new Class[]{String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (TextUtils.isEmpty(mac) || (mac.length() != 12 && mac.length() != 17)) {
            return "";
        }
        String temp = mac.replace(":", "").toLowerCase();
        StringBuilder sb = new StringBuilder();
        for (int i = (temp.length() / 2) - 1; i >= 0; i--) {
            sb.append(temp.substring(i * 2, (i * 2) + 2));
        }
        return sb.toString();
    }

    public static String m(String uuid) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{uuid}, (Object) null, changeQuickRedirect, true, 6455, new Class[]{String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (TextUtils.isEmpty(uuid)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = (uuid.length() / 2) - 1; i >= 0; i--) {
            sb.append(uuid.substring(i * 2, (i * 2) + 2));
        }
        return sb.toString();
    }

    public static byte[] c(byte[] a2) {
        byte[] b2 = new byte[a2.length];
        for (int i = 0; i < b2.length; i++) {
            b2[i] = a2[(b2.length - i) - 1];
        }
        return b2;
    }

    public static byte[] j(long i, int bytesLen) {
        byte[] bytes = new byte[bytesLen];
        if (bytesLen == 2) {
            bytes[0] = (byte) ((int) ((i >> 8) & 255));
            bytes[1] = (byte) ((int) (i & 255));
        } else if (bytesLen == 4) {
            bytes[0] = (byte) ((int) ((i >> 24) & 255));
            bytes[1] = (byte) ((int) ((i >> 16) & 255));
            bytes[2] = (byte) ((int) ((i >> 8) & 255));
            bytes[3] = (byte) ((int) (i & 255));
        }
        return bytes;
    }

    public static long a(byte[] bytes, int bytesLen) {
        if (bytesLen == 2) {
            return (long) ((65280 & (bytes[0] << 8)) | (bytes[1] & 255));
        }
        if (bytesLen != 4) {
            return 0;
        }
        return (long) (((bytes[0] << 24) & ViewCompat.MEASURED_STATE_MASK) | ((bytes[1] << MappingData.PATH) & 16711680) | (65280 & (bytes[2] << 8)) | (bytes[3] & 255));
    }
}
