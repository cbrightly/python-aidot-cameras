package io.ktor.utils.io.core;

import java.nio.ByteOrder;

/* compiled from: ByteOrderJVM.kt */
public final class m {
    /* access modifiers changed from: private */
    public static final l b(ByteOrder nioOrder) {
        return nioOrder == ByteOrder.BIG_ENDIAN ? l.BIG_ENDIAN : l.LITTLE_ENDIAN;
    }
}
