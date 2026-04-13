package io.ktor.utils.io.core;

import java.nio.ByteOrder;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: ByteOrderJVM.kt */
public enum l {
    BIG_ENDIAN(r2),
    LITTLE_ENDIAN(r2);
    
    public static final a Companion = null;
    /* access modifiers changed from: private */
    public static final l d = null;
    @NotNull
    private final ByteOrder nioOrder;

    private l(ByteOrder nioOrder2) {
        this.nioOrder = nioOrder2;
    }

    @NotNull
    public final ByteOrder getNioOrder() {
        return this.nioOrder;
    }

    static {
        Companion = new a((DefaultConstructorMarker) null);
        ByteOrder nativeOrder = ByteOrder.nativeOrder();
        k.b(nativeOrder, "java.nio.ByteOrder.nativeOrder()");
        d = m.b(nativeOrder);
    }

    /* compiled from: ByteOrderJVM.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
