package com.leedarson.base.utils;

import com.leedarson.secret.JNIUtil;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* compiled from: Aes128Util */
public class b {
    private static final char[] a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final String b = JNIUtil.getInstance().getStr12();
    public static ChangeQuickRedirect changeQuickRedirect;

    /* compiled from: Aes128Util */
    public enum a {
        AES128,
        AES256;
        
        public static ChangeQuickRedirect changeQuickRedirect;
    }

    public static byte[] h(byte[] seed) {
        byte[] raw = new byte[16];
        for (int i = 0; i < 16; i++) {
            if (i < seed.length) {
                raw[i] = seed[i];
            }
        }
        return raw;
    }

    public static byte[] i(byte[] seed) {
        byte[] raw = new byte[32];
        for (int i = 0; i < 32; i++) {
            if (i < seed.length) {
                raw[i] = seed[i];
            }
        }
        return raw;
    }

    @Deprecated
    public static byte[] d(String key, byte[] clear) {
        SecretKeySpec skeySpec = new SecretKeySpec(h(key.getBytes()), "AES");
        Cipher cipher = Cipher.getInstance(b);
        cipher.init(1, skeySpec);
        return cipher.doFinal(clear);
    }

    @Deprecated
    public static byte[] a(String key, byte[] encrypted) {
        SecretKeySpec skeySpec = new SecretKeySpec(h(key.getBytes()), "AES");
        Cipher cipher = Cipher.getInstance(b);
        cipher.init(2, skeySpec);
        return cipher.doFinal(encrypted);
    }

    public static byte[] g(byte[] ivBytes, a aesType, String key, byte[] clear) {
        byte[] raw;
        Class<byte[]> cls = byte[].class;
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{ivBytes, aesType, key, clear}, (Object) null, changeQuickRedirect2, true, 421, new Class[]{cls, a.class, String.class, cls}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        byte[] ivOffset = ivBytes;
        if (ivOffset == null) {
            ivOffset = new byte[16];
        }
        IvParameterSpec ivSpec = new IvParameterSpec(ivOffset);
        if (aesType == a.AES256) {
            raw = i(key.getBytes());
        } else {
            raw = h(key.getBytes());
        }
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
        cipher.init(1, skeySpec, ivSpec);
        return cipher.doFinal(clear);
    }

    public static byte[] e(byte[] ivBytes, a aesType, String key, byte[] clear) {
        byte[] raw;
        Class<byte[]> cls = byte[].class;
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{ivBytes, aesType, key, clear}, (Object) null, changeQuickRedirect2, true, 422, new Class[]{cls, a.class, String.class, cls}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        byte[] ivOffset = ivBytes;
        if (ivOffset == null) {
            ivOffset = new byte[16];
        }
        IvParameterSpec ivSpec = new IvParameterSpec(ivOffset);
        if (aesType == a.AES256) {
            raw = i(key.getBytes());
        } else {
            raw = h(key.getBytes());
        }
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        cipher.init(1, skeySpec, ivSpec);
        return cipher.doFinal(clear);
    }

    public static byte[] f(a aesType, String key, byte[] clear) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{aesType, key, clear}, (Object) null, changeQuickRedirect, true, 423, new Class[]{a.class, String.class, byte[].class}, byte[].class);
        return proxy.isSupported ? (byte[]) proxy.result : g((byte[]) null, aesType, key, clear);
    }

    public static byte[] c(byte[] ivBytes, a aesType, String key, byte[] encrypted) {
        byte[] raw;
        Class<byte[]> cls = byte[].class;
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{ivBytes, aesType, key, encrypted}, (Object) null, changeQuickRedirect2, true, 424, new Class[]{cls, a.class, String.class, cls}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        byte[] ivOffset = ivBytes;
        if (ivOffset == null) {
            ivOffset = new byte[16];
        }
        IvParameterSpec ivSpec = new IvParameterSpec(ivOffset);
        if (aesType == a.AES256) {
            raw = i(key.getBytes());
        } else {
            raw = h(key.getBytes());
        }
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
        cipher.init(2, skeySpec, ivSpec);
        return cipher.doFinal(encrypted);
    }

    public static byte[] b(a aesType, String key, byte[] encrypted) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{aesType, key, encrypted}, (Object) null, changeQuickRedirect, true, 425, new Class[]{a.class, String.class, byte[].class}, byte[].class);
        return proxy.isSupported ? (byte[]) proxy.result : c((byte[]) null, aesType, key, encrypted);
    }
}
