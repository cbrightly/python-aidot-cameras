package io.ktor.network.sockets;

import kotlin.jvm.internal.DefaultConstructorMarker;
import org.glassfish.grizzly.http.server.util.MappingData;

/* compiled from: TypeOfService.kt */
public final class u {
    /* access modifiers changed from: private */
    public static final byte a = b((byte) 0);
    private static final byte b = b((byte) 2);
    private static final byte c = b((byte) 4);
    private static final byte d = b((byte) 8);
    private static final byte e = b(MappingData.PATH);
    public static final a f = new a((DefaultConstructorMarker) null);

    public static final boolean c(byte b2, byte b3) {
        return b2 == b3;
    }

    public static byte b(byte value) {
        return value;
    }

    /* compiled from: TypeOfService.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        public final byte a() {
            return u.a;
        }
    }
}
