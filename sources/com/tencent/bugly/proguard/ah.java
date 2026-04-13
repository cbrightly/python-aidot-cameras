package com.tencent.bugly.proguard;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* compiled from: BUGLY */
public final class ah implements aj {
    private String a = null;

    public final byte[] a(byte[] bArr) {
        if (this.a == null || bArr == null) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b : bArr) {
            stringBuffer.append(b + " ");
        }
        SecretKeySpec secretKeySpec = new SecretKeySpec(this.a.getBytes(), "AES");
        Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
        instance.init(2, secretKeySpec, new IvParameterSpec(this.a.getBytes()));
        byte[] doFinal = instance.doFinal(bArr);
        StringBuffer stringBuffer2 = new StringBuffer();
        for (byte b2 : doFinal) {
            stringBuffer2.append(b2 + " ");
        }
        return doFinal;
    }

    public final byte[] b(byte[] bArr) {
        if (this.a == null || bArr == null) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b : bArr) {
            stringBuffer.append(b + " ");
        }
        SecretKeySpec secretKeySpec = new SecretKeySpec(this.a.getBytes(), "AES");
        Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
        instance.init(1, secretKeySpec, new IvParameterSpec(this.a.getBytes()));
        byte[] doFinal = instance.doFinal(bArr);
        StringBuffer stringBuffer2 = new StringBuffer();
        for (byte b2 : doFinal) {
            stringBuffer2.append(b2 + " ");
        }
        return doFinal;
    }

    public final void a(String str) {
        if (str != null) {
            for (int length = str.length(); length < 16; length++) {
                str = str + "0";
            }
            this.a = str.substring(0, 16);
        }
    }
}
