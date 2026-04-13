package com.telink.crypto;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.util.a;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public abstract class AES {
    public static boolean a = true;
    public static ChangeQuickRedirect changeQuickRedirect;

    private static native byte[] decryptCmd(byte[] bArr, byte[] bArr2, byte[] bArr3);

    private static native byte[] encryptCmd(byte[] bArr, byte[] bArr2, byte[] bArr3);

    static {
        System.loadLibrary("TelinkCrypto");
    }

    public static byte[] b(byte[] key, byte[] content) {
        Class<byte[]> cls = byte[].class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{key, content}, (Object) null, changeQuickRedirect, true, 13857, new Class[]{cls, cls}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        if (!a) {
            return content;
        }
        byte[] key2 = a.d(key);
        byte[] content2 = a.d(content);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key2, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
        cipher.init(1, secretKeySpec);
        return cipher.doFinal(content2);
    }

    public static byte[] c(byte[] key, byte[] nonce, byte[] plaintext) {
        Class<byte[]> cls = byte[].class;
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{key, nonce, plaintext}, (Object) null, changeQuickRedirect2, true, 13859, new Class[]{cls, cls, cls}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        if (!a) {
            return plaintext;
        }
        return encryptCmd(plaintext, nonce, key);
    }

    public static byte[] a(byte[] key, byte[] nonce, byte[] plaintext) {
        Class<byte[]> cls = byte[].class;
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{key, nonce, plaintext}, (Object) null, changeQuickRedirect2, true, 13860, new Class[]{cls, cls, cls}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        if (!a) {
            return plaintext;
        }
        return decryptCmd(plaintext, nonce, key);
    }
}
