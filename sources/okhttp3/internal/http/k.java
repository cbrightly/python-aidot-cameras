package okhttp3.internal.http;

import java.net.ProtocolException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.w;
import okhttp3.a0;
import org.glassfish.grizzly.http.server.Constants;
import org.jetbrains.annotations.NotNull;

/* compiled from: StatusLine.kt */
public final class k {
    public static final a a = new a((DefaultConstructorMarker) null);
    @NotNull
    public final a0 b;
    public final int c;
    @NotNull
    public final String d;

    public k(@NotNull a0 protocol, int code, @NotNull String message) {
        kotlin.jvm.internal.k.f(protocol, "protocol");
        kotlin.jvm.internal.k.f(message, "message");
        this.b = protocol;
        this.c = code;
        this.d = message;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder();
        StringBuilder $this$buildString = sb;
        if (this.b == a0.HTTP_1_0) {
            $this$buildString.append(Constants.HTTP_10);
        } else {
            $this$buildString.append(Constants.HTTP_11);
        }
        $this$buildString.append(' ');
        $this$buildString.append(this.c);
        $this$buildString.append(' ');
        $this$buildString.append(this.d);
        String sb2 = sb.toString();
        kotlin.jvm.internal.k.b(sb2, "StringBuilder().apply(builderAction).toString()");
        return sb2;
    }

    /* compiled from: StatusLine.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final k a(@NotNull String statusLine) {
            a0 protocol;
            int codeStart;
            a0 a0Var;
            kotlin.jvm.internal.k.f(statusLine, "statusLine");
            if (w.N(statusLine, "HTTP/1.", false, 2, (Object) null)) {
                if (statusLine.length() < 9 || statusLine.charAt(8) != ' ') {
                    throw new ProtocolException("Unexpected status line: " + statusLine);
                }
                int httpMinorVersion = statusLine.charAt(7) - '0';
                codeStart = 9;
                if (httpMinorVersion == 0) {
                    a0Var = a0.HTTP_1_0;
                } else if (httpMinorVersion == 1) {
                    a0Var = a0.HTTP_1_1;
                } else {
                    throw new ProtocolException("Unexpected status line: " + statusLine);
                }
                protocol = a0Var;
            } else if (w.N(statusLine, "ICY ", false, 2, (Object) null)) {
                protocol = a0.HTTP_1_0;
                codeStart = 4;
            } else {
                throw new ProtocolException("Unexpected status line: " + statusLine);
            }
            if (statusLine.length() >= codeStart + 3) {
                try {
                    String substring = statusLine.substring(codeStart, codeStart + 3);
                    kotlin.jvm.internal.k.b(substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                    int code = Integer.parseInt(substring);
                    String message = "";
                    if (statusLine.length() > codeStart + 3) {
                        if (statusLine.charAt(codeStart + 3) == ' ') {
                            String substring2 = statusLine.substring(codeStart + 4);
                            kotlin.jvm.internal.k.b(substring2, "(this as java.lang.String).substring(startIndex)");
                            message = substring2;
                        } else {
                            throw new ProtocolException("Unexpected status line: " + statusLine);
                        }
                    }
                    return new k(protocol, code, message);
                } catch (NumberFormatException e) {
                    throw new ProtocolException("Unexpected status line: " + statusLine);
                }
            } else {
                throw new ProtocolException("Unexpected status line: " + statusLine);
            }
        }
    }
}
