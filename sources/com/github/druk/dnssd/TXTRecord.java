package com.github.druk.dnssd;

import java.io.UnsupportedEncodingException;

public class TXTRecord {
    protected static final byte kAttrSep = 61;
    protected byte[] fBytes;

    public TXTRecord() {
        this.fBytes = new byte[0];
    }

    public TXTRecord(byte[] initBytes) {
        this.fBytes = (byte[]) initBytes.clone();
    }

    public void set(String key, String value) {
        set(key, value != null ? value.getBytes() : null);
    }

    public void set(String key, byte[] value) {
        int valLen = value != null ? value.length : 0;
        try {
            byte[] keyBytes = key.getBytes("US-ASCII");
            int i = 0;
            while (i < keyBytes.length) {
                if (keyBytes[i] != 61) {
                    i++;
                } else {
                    throw new IllegalArgumentException();
                }
            }
            if (keyBytes.length + valLen < 255) {
                int prevLoc = remove(key);
                if (prevLoc == -1) {
                    prevLoc = size();
                }
                insert(keyBytes, value, prevLoc);
                return;
            }
            throw new ArrayIndexOutOfBoundsException();
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException();
        }
    }

    /* access modifiers changed from: protected */
    public void insert(byte[] keyBytes, byte[] value, int index) {
        byte[] oldBytes = this.fBytes;
        int valLen = value != null ? value.length : 0;
        int insertion = 0;
        for (int i = 0; i < index; i++) {
            byte[] bArr = this.fBytes;
            if (insertion >= bArr.length) {
                break;
            }
            insertion += (bArr[insertion] + 1) & 255;
        }
        int avLen = keyBytes.length + valLen + (value != null ? 1 : 0);
        int newLen = oldBytes.length + avLen + 1;
        byte[] bArr2 = new byte[newLen];
        this.fBytes = bArr2;
        System.arraycopy(oldBytes, 0, bArr2, 0, insertion);
        int secondHalfLen = oldBytes.length - insertion;
        System.arraycopy(oldBytes, insertion, this.fBytes, newLen - secondHalfLen, secondHalfLen);
        byte[] bArr3 = this.fBytes;
        bArr3[insertion] = (byte) avLen;
        System.arraycopy(keyBytes, 0, bArr3, insertion + 1, keyBytes.length);
        if (value != null) {
            byte[] bArr4 = this.fBytes;
            bArr4[insertion + 1 + keyBytes.length] = kAttrSep;
            System.arraycopy(value, 0, bArr4, keyBytes.length + insertion + 2, valLen);
        }
    }

    public int remove(String key) {
        int avStart = 0;
        int i = 0;
        while (true) {
            byte[] bArr = this.fBytes;
            if (avStart >= bArr.length) {
                return -1;
            }
            byte avLen = bArr[avStart];
            if (key.length() > avLen || !((key.length() == avLen || this.fBytes[key.length() + avStart + 1] == 61) && key.compareToIgnoreCase(new String(this.fBytes, avStart + 1, key.length())) == 0)) {
                avStart += (avLen + 1) & 255;
                i++;
            } else {
                byte[] oldBytes = this.fBytes;
                byte[] bArr2 = new byte[((oldBytes.length - avLen) - 1)];
                this.fBytes = bArr2;
                System.arraycopy(oldBytes, 0, bArr2, 0, avStart);
                System.arraycopy(oldBytes, avStart + avLen + 1, this.fBytes, avStart, ((oldBytes.length - avStart) - avLen) - 1);
                return i;
            }
        }
    }

    public int size() {
        int i = 0;
        int avStart = 0;
        while (true) {
            byte[] bArr = this.fBytes;
            if (avStart >= bArr.length) {
                return i;
            }
            avStart += (bArr[avStart] + 1) & 255;
            i++;
        }
    }

    public boolean contains(String key) {
        int i = 0;
        while (true) {
            String key2 = getKey(i);
            String s = key2;
            if (key2 == null) {
                return false;
            }
            if (key.compareToIgnoreCase(s) == 0) {
                return true;
            }
            i++;
        }
    }

    public String getKey(int index) {
        int avStart = 0;
        for (int i = 0; i < index; i++) {
            byte[] bArr = this.fBytes;
            if (avStart >= bArr.length) {
                break;
            }
            avStart += bArr[avStart] + 1;
        }
        byte[] bArr2 = this.fBytes;
        if (avStart >= bArr2.length) {
            return null;
        }
        byte avLen = bArr2[avStart];
        int aLen = 0;
        while (aLen < avLen && this.fBytes[avStart + aLen + 1] != 61) {
            aLen++;
        }
        return new String(this.fBytes, avStart + 1, aLen);
    }

    public byte[] getValue(int index) {
        int avStart = 0;
        for (int i = 0; i < index; i++) {
            byte[] bArr = this.fBytes;
            if (avStart >= bArr.length) {
                break;
            }
            avStart += bArr[avStart] + 1;
        }
        byte[] bArr2 = this.fBytes;
        if (avStart >= bArr2.length) {
            return null;
        }
        byte avLen = bArr2[avStart];
        for (int aLen = 0; aLen < avLen; aLen++) {
            byte[] bArr3 = this.fBytes;
            if (bArr3[avStart + aLen + 1] == 61) {
                byte[] value = new byte[((avLen - aLen) - 1)];
                System.arraycopy(bArr3, avStart + aLen + 2, value, 0, (avLen - aLen) - 1);
                return value;
            }
        }
        return null;
    }

    public String getValueAsString(int index) {
        byte[] value = getValue(index);
        if (value != null) {
            return new String(value);
        }
        return null;
    }

    public byte[] getValue(String forKey) {
        int i = 0;
        while (true) {
            String key = getKey(i);
            String s = key;
            if (key == null) {
                return null;
            }
            if (forKey.compareToIgnoreCase(s) == 0) {
                return getValue(i);
            }
            i++;
        }
    }

    public String getValueAsString(String forKey) {
        byte[] val = getValue(forKey);
        if (val != null) {
            return new String(val);
        }
        return null;
    }

    public byte[] getRawBytes() {
        return (byte[]) this.fBytes.clone();
    }

    public String toString() {
        String av;
        String result = null;
        int i = 0;
        while (true) {
            String key = getKey(i);
            String a = key;
            if (key == null) {
                break;
            }
            String av2 = String.valueOf(i) + "={" + a;
            String val = getValueAsString(i);
            if (val != null) {
                av = av2 + "=" + val + "}";
            } else {
                av = av2 + "}";
            }
            if (result == null) {
                result = av;
            } else {
                result = result + ", " + av;
            }
            i++;
        }
        return result != null ? result : "";
    }
}
