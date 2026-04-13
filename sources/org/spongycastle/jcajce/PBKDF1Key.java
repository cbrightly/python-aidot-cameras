package org.spongycastle.jcajce;

import org.spongycastle.crypto.CharToByteConverter;

public class PBKDF1Key implements PBKDFKey {
    private final CharToByteConverter converter;
    private final char[] password;

    public PBKDF1Key(char[] password2, CharToByteConverter converter2) {
        char[] cArr = new char[password2.length];
        this.password = cArr;
        this.converter = converter2;
        System.arraycopy(password2, 0, cArr, 0, password2.length);
    }

    public char[] getPassword() {
        return this.password;
    }

    public String getAlgorithm() {
        return "PBKDF1";
    }

    public String getFormat() {
        return this.converter.getType();
    }

    public byte[] getEncoded() {
        return this.converter.convert(this.password);
    }
}
