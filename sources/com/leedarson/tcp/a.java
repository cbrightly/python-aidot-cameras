package com.leedarson.tcp;

import com.leedarson.secret.JNIUtil;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/* compiled from: Aes128Util */
public class a {
    private static final char[] a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final String b = JNIUtil.getInstance().getStr12();
    public static ChangeQuickRedirect changeQuickRedirect;

    public static byte[] c(byte[] seed) {
        byte[] raw = new byte[16];
        for (int i = 0; i < 16; i++) {
            raw[i] = seed[i];
        }
        return raw;
    }

    public static byte[] b(String key, byte[] clear) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{key, clear}, (Object) null, changeQuickRedirect, true, 10754, new Class[]{String.class, byte[].class}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        SecretKeySpec skeySpec = new SecretKeySpec(c(key.getBytes()), "AES");
        Cipher cipher = Cipher.getInstance(b);
        cipher.init(1, skeySpec);
        return cipher.doFinal(clear);
    }

    public static byte[] a(String key, byte[] encrypted) {
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{key, encrypted}, (Object) null, changeQuickRedirect2, true, 10755, new Class[]{String.class, byte[].class}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        SecretKeySpec skeySpec = new SecretKeySpec(c(key.getBytes()), "AES");
        Cipher cipher = Cipher.getInstance(b);
        cipher.init(2, skeySpec);
        return cipher.doFinal(encrypted);
    }
}
