package okhttp3.internal.ws;

import io.netty.util.internal.StringUtil;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.text.v;
import kotlin.text.w;
import kotlin.text.x;
import okhttp3.internal.b;
import okhttp3.u;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: WebSocketExtensions.kt */
public final class e {
    public static final a a = new a((DefaultConstructorMarker) null);
    public final boolean b;
    @Nullable
    public final Integer c;
    public final boolean d;
    @Nullable
    public final Integer e;
    public final boolean f;
    public final boolean g;

    public e() {
        this(false, (Integer) null, false, (Integer) null, false, false, 63, (DefaultConstructorMarker) null);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof e)) {
            return false;
        }
        e eVar = (e) obj;
        return this.b == eVar.b && k.a(this.c, eVar.c) && this.d == eVar.d && k.a(this.e, eVar.e) && this.f == eVar.f && this.g == eVar.g;
    }

    public int hashCode() {
        boolean z = this.b;
        boolean z2 = true;
        if (z) {
            z = true;
        }
        int i = (z ? 1 : 0) * true;
        Integer num = this.c;
        int i2 = 0;
        int hashCode = (i + (num != null ? num.hashCode() : 0)) * 31;
        boolean z3 = this.d;
        if (z3) {
            z3 = true;
        }
        int i3 = (hashCode + (z3 ? 1 : 0)) * 31;
        Integer num2 = this.e;
        if (num2 != null) {
            i2 = num2.hashCode();
        }
        int i4 = (i3 + i2) * 31;
        boolean z4 = this.f;
        if (z4) {
            z4 = true;
        }
        int i5 = (i4 + (z4 ? 1 : 0)) * 31;
        boolean z5 = this.g;
        if (!z5) {
            z2 = z5;
        }
        return i5 + (z2 ? 1 : 0);
    }

    @NotNull
    public String toString() {
        return "WebSocketExtensions(perMessageDeflate=" + this.b + ", clientMaxWindowBits=" + this.c + ", clientNoContextTakeover=" + this.d + ", serverMaxWindowBits=" + this.e + ", serverNoContextTakeover=" + this.f + ", unknownValues=" + this.g + ")";
    }

    public e(boolean perMessageDeflate, @Nullable Integer clientMaxWindowBits, boolean clientNoContextTakeover, @Nullable Integer serverMaxWindowBits, boolean serverNoContextTakeover, boolean unknownValues) {
        this.b = perMessageDeflate;
        this.c = clientMaxWindowBits;
        this.d = clientNoContextTakeover;
        this.e = serverMaxWindowBits;
        this.f = serverNoContextTakeover;
        this.g = unknownValues;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ e(boolean r6, java.lang.Integer r7, boolean r8, java.lang.Integer r9, boolean r10, boolean r11, int r12, kotlin.jvm.internal.DefaultConstructorMarker r13) {
        /*
            r5 = this;
            r13 = r12 & 1
            r0 = 0
            if (r13 == 0) goto L_0x0007
            r13 = r0
            goto L_0x0008
        L_0x0007:
            r13 = r6
        L_0x0008:
            r6 = r12 & 2
            r1 = 0
            if (r6 == 0) goto L_0x000f
            r2 = r1
            goto L_0x0010
        L_0x000f:
            r2 = r7
        L_0x0010:
            r6 = r12 & 4
            if (r6 == 0) goto L_0x0016
            r3 = r0
            goto L_0x0017
        L_0x0016:
            r3 = r8
        L_0x0017:
            r6 = r12 & 8
            if (r6 == 0) goto L_0x001c
            goto L_0x001d
        L_0x001c:
            r1 = r9
        L_0x001d:
            r6 = r12 & 16
            if (r6 == 0) goto L_0x0023
            r4 = r0
            goto L_0x0024
        L_0x0023:
            r4 = r10
        L_0x0024:
            r6 = r12 & 32
            if (r6 == 0) goto L_0x002a
            r12 = r0
            goto L_0x002b
        L_0x002a:
            r12 = r11
        L_0x002b:
            r6 = r5
            r7 = r13
            r8 = r2
            r9 = r3
            r10 = r1
            r11 = r4
            r6.<init>(r7, r8, r9, r10, r11, r12)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.ws.e.<init>(boolean, java.lang.Integer, boolean, java.lang.Integer, boolean, boolean, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    public final boolean a(boolean clientOriginated) {
        if (clientOriginated) {
            return this.d;
        }
        return this.f;
    }

    /* compiled from: WebSocketExtensions.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final e a(@NotNull u responseHeaders) {
            boolean z;
            String extensionToken;
            boolean compressionEnabled;
            String value;
            boolean unexpectedValues;
            boolean unexpectedValues2;
            u uVar = responseHeaders;
            k.f(uVar, "responseHeaders");
            boolean compressionEnabled2 = false;
            Integer clientMaxWindowBits = null;
            boolean clientNoContextTakeover = false;
            Integer serverMaxWindowBits = null;
            boolean serverNoContextTakeover = false;
            boolean unexpectedValues3 = false;
            int size = responseHeaders.size();
            int i = 0;
            while (i < size) {
                boolean unexpectedValues4 = true;
                if (w.y(uVar.b(i), "Sec-WebSocket-Extensions", true)) {
                    String header = uVar.h(i);
                    int pos = 0;
                    while (pos < header.length()) {
                        int extensionEnd = b.o(header, StringUtil.COMMA, pos, 0, 4, (Object) null);
                        int extensionTokenEnd = b.m(header, ';', pos, extensionEnd);
                        String extensionToken2 = b.V(header, pos, extensionTokenEnd);
                        pos = extensionTokenEnd + 1;
                        if (w.y(extensionToken2, "permessage-deflate", unexpectedValues4)) {
                            if (compressionEnabled2) {
                                unexpectedValues3 = true;
                            }
                            compressionEnabled2 = true;
                            while (pos < extensionEnd) {
                                int parameterEnd = b.m(header, ';', pos, extensionEnd);
                                int equals = b.m(header, '=', pos, parameterEnd);
                                String name = b.V(header, pos, equals);
                                if (equals < parameterEnd) {
                                    compressionEnabled = compressionEnabled2;
                                    extensionToken = extensionToken2;
                                    value = x.y0(b.V(header, equals + 1, parameterEnd), "\"");
                                } else {
                                    compressionEnabled = compressionEnabled2;
                                    extensionToken = extensionToken2;
                                    value = null;
                                }
                                pos = parameterEnd + 1;
                                boolean unexpectedValues5 = unexpectedValues3;
                                if (w.y(name, "client_max_window_bits", true)) {
                                    boolean unexpectedValues6 = clientMaxWindowBits != null ? true : unexpectedValues5;
                                    Integer clientMaxWindowBits2 = value != null ? v.o(value) : null;
                                    if (clientMaxWindowBits2 == null) {
                                        unexpectedValues = true;
                                        clientMaxWindowBits = clientMaxWindowBits2;
                                        unexpectedValues2 = true;
                                    } else {
                                        clientMaxWindowBits = clientMaxWindowBits2;
                                        unexpectedValues2 = unexpectedValues6;
                                        unexpectedValues = true;
                                    }
                                } else if (w.y(name, "client_no_context_takeover", true)) {
                                    boolean unexpectedValues7 = clientNoContextTakeover ? true : unexpectedValues5;
                                    if (value != null) {
                                        unexpectedValues7 = true;
                                    }
                                    clientNoContextTakeover = true;
                                    unexpectedValues2 = unexpectedValues7;
                                    unexpectedValues = true;
                                } else if (w.y(name, "server_max_window_bits", true)) {
                                    boolean unexpectedValues8 = serverMaxWindowBits != null ? true : unexpectedValues5;
                                    Integer serverMaxWindowBits2 = value != null ? v.o(value) : null;
                                    if (serverMaxWindowBits2 == null) {
                                        unexpectedValues = true;
                                        serverMaxWindowBits = serverMaxWindowBits2;
                                        unexpectedValues2 = true;
                                    } else {
                                        serverMaxWindowBits = serverMaxWindowBits2;
                                        unexpectedValues2 = unexpectedValues8;
                                        unexpectedValues = true;
                                    }
                                } else {
                                    unexpectedValues = true;
                                    if (w.y(name, "server_no_context_takeover", true)) {
                                        unexpectedValues2 = serverNoContextTakeover ? true : unexpectedValues5;
                                        if (value != null) {
                                            unexpectedValues2 = true;
                                        }
                                        serverNoContextTakeover = true;
                                    } else {
                                        unexpectedValues2 = true;
                                    }
                                }
                                u uVar2 = responseHeaders;
                                unexpectedValues4 = unexpectedValues;
                                compressionEnabled2 = compressionEnabled;
                                unexpectedValues3 = unexpectedValues2;
                                extensionToken2 = extensionToken;
                            }
                            String str = extensionToken2;
                            z = unexpectedValues4;
                            unexpectedValues3 = unexpectedValues3;
                        } else {
                            z = unexpectedValues4;
                            unexpectedValues3 = true;
                        }
                        unexpectedValues4 = z;
                        u uVar3 = responseHeaders;
                    }
                }
                i++;
                uVar = responseHeaders;
            }
            return new e(compressionEnabled2, clientMaxWindowBits, clientNoContextTakeover, serverMaxWindowBits, serverNoContextTakeover, unexpectedValues3);
        }
    }
}
