package io.netty.channel.socket.nio;

import io.netty.channel.socket.InternetProtocolFamily;
import java.net.ProtocolFamily;
import java.net.StandardProtocolFamily;

public final class ProtocolFamilyConverter {
    private ProtocolFamilyConverter() {
    }

    /* renamed from: io.netty.channel.socket.nio.ProtocolFamilyConverter$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$io$netty$channel$socket$InternetProtocolFamily;

        static {
            int[] iArr = new int[InternetProtocolFamily.values().length];
            $SwitchMap$io$netty$channel$socket$InternetProtocolFamily = iArr;
            try {
                iArr[InternetProtocolFamily.IPv4.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$io$netty$channel$socket$InternetProtocolFamily[InternetProtocolFamily.IPv6.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    public static ProtocolFamily convert(InternetProtocolFamily family) {
        switch (AnonymousClass1.$SwitchMap$io$netty$channel$socket$InternetProtocolFamily[family.ordinal()]) {
            case 1:
                return StandardProtocolFamily.INET;
            case 2:
                return StandardProtocolFamily.INET6;
            default:
                throw new IllegalArgumentException();
        }
    }
}
