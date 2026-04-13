package com.leedarson.serviceinterface.prefs.aes;

import android.text.TextUtils;
import android.util.Base64;
import com.meituan.robust.ChangeQuickRedirect;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class LDSAESUtils {
    private static final String AES = "AES";
    private static final String ECB_PKCS7_PADDING = "AES/ECB/PKCS7Padding";
    public static ChangeQuickRedirect changeQuickRedirect;

    public static byte[] get32Key(byte[] seed) {
        byte[] raw = new byte[32];
        for (int i = 0; i < seed.length; i++) {
            raw[i] = seed[i];
        }
        return raw;
    }

    @Deprecated
    public static String encrypt(String key, String cleartext) {
        if (TextUtils.isEmpty(cleartext)) {
            return cleartext;
        }
        try {
            return new String(Base64.encode(encrypt(key, cleartext.getBytes()), 0));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Deprecated
    public static String decrypt(String key, String encrypted) {
        if (TextUtils.isEmpty(encrypted)) {
            return encrypted;
        }
        try {
            return new String(decrypt(key, Base64.decode(encrypted, 0)));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Deprecated
    public static byte[] encrypt(String key, byte[] clear) {
        SecretKeySpec skeySpec = new SecretKeySpec(get32Key(key.getBytes()), AES);
        Cipher cipher = Cipher.getInstance(ECB_PKCS7_PADDING);
        cipher.init(1, skeySpec);
        return cipher.doFinal(clear);
    }

    @Deprecated
    public static byte[] decrypt(String key, byte[] encrypted) {
        SecretKeySpec skeySpec = new SecretKeySpec(get32Key(key.getBytes()), AES);
        Cipher cipher = Cipher.getInstance(ECB_PKCS7_PADDING);
        cipher.init(2, skeySpec);
        return cipher.doFinal(encrypted);
    }
}
