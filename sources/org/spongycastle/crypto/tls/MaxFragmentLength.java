package org.spongycastle.crypto.tls;

public class MaxFragmentLength {
    public static boolean a(short maxFragmentLength) {
        return maxFragmentLength >= 1 && maxFragmentLength <= 4;
    }
}
