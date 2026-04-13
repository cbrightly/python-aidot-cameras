package org.spongycastle.jcajce;

import org.spongycastle.crypto.CharToByteConverter;
import org.spongycastle.util.Arrays;

public class PBKDF2Key implements PBKDFKey {
    private final CharToByteConverter converter;
    private final char[] password;

    public PBKDF2Key(char[] password2, CharToByteConverter converter2) {
        this.password = Arrays.j(password2);
        this.converter = converter2;
    }

    public char[] getPassword() {
        return this.password;
    }

    public String getAlgorithm() {
        return "PBKDF2";
    }

    public String getFormat() {
        return this.converter.getType();
    }

    public byte[] getEncoded() {
        return this.converter.convert(this.password);
    }
}
