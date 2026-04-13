package org.spongycastle.crypto.util;

import org.spongycastle.util.Strings;

public final class DERMacData {

    public static final class Builder {
    }

    public enum Type {
        UNILATERALU("KC_1_U"),
        UNILATERALV("KC_1_V"),
        BILATERALU("KC_2_U"),
        BILATERALV("KC_2_V");
        
        private final String enc;

        private Type(String enc2) {
            this.enc = enc2;
        }

        public byte[] getHeader() {
            return Strings.f(this.enc);
        }
    }

    /* renamed from: org.spongycastle.crypto.util.DERMacData$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[Type.values().length];
            a = iArr;
            try {
                iArr[Type.UNILATERALU.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[Type.BILATERALU.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[Type.UNILATERALV.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                a[Type.BILATERALV.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }
}
