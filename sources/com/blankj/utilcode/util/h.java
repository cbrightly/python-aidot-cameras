package com.blankj.utilcode.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* compiled from: EncryptUtils */
public final class h {
    static byte[] a(byte[] data, String algorithm) {
        if (data == null || data.length <= 0) {
            return null;
        }
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(data);
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
