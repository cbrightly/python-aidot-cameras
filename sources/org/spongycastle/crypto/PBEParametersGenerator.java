package org.spongycastle.crypto;

import org.spongycastle.util.Strings;

public abstract class PBEParametersGenerator {
    protected byte[] a;
    protected byte[] b;
    protected int c;

    public abstract CipherParameters d(int i);

    public abstract CipherParameters e(int i);

    public abstract CipherParameters f(int i, int i2);

    protected PBEParametersGenerator() {
    }

    public void g(byte[] password, byte[] salt, int iterationCount) {
        this.a = password;
        this.b = salt;
        this.c = iterationCount;
    }

    public static byte[] b(char[] password) {
        if (password == null) {
            return new byte[0];
        }
        byte[] bytes = new byte[password.length];
        for (int i = 0; i != bytes.length; i++) {
            bytes[i] = (byte) password[i];
        }
        return bytes;
    }

    public static byte[] c(char[] password) {
        if (password != null) {
            return Strings.k(password);
        }
        return new byte[0];
    }

    public static byte[] a(char[] password) {
        if (password == null || password.length <= 0) {
            return new byte[0];
        }
        byte[] bytes = new byte[((password.length + 1) * 2)];
        for (int i = 0; i != password.length; i++) {
            bytes[i * 2] = (byte) (password[i] >>> 8);
            bytes[(i * 2) + 1] = (byte) password[i];
        }
        return bytes;
    }
}
