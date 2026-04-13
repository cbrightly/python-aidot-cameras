package okhttp3.internal.http2;

import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.Nullable;

/* compiled from: ErrorCode.kt */
public enum a {
    NO_ERROR(0),
    PROTOCOL_ERROR(1),
    INTERNAL_ERROR(2),
    FLOW_CONTROL_ERROR(3),
    SETTINGS_TIMEOUT(4),
    STREAM_CLOSED(5),
    FRAME_SIZE_ERROR(6),
    REFUSED_STREAM(7),
    CANCEL(8),
    COMPRESSION_ERROR(9),
    CONNECT_ERROR(10),
    ENHANCE_YOUR_CALM(11),
    INADEQUATE_SECURITY(12),
    HTTP_1_1_REQUIRED(13);
    
    public static final C0464a Companion = null;
    private final int httpCode;

    private a(int httpCode2) {
        this.httpCode = httpCode2;
    }

    public final int getHttpCode() {
        return this.httpCode;
    }

    static {
        Companion = new C0464a((DefaultConstructorMarker) null);
    }

    /* renamed from: okhttp3.internal.http2.a$a  reason: collision with other inner class name */
    /* compiled from: ErrorCode.kt */
    public static final class C0464a {
        private C0464a() {
        }

        public /* synthetic */ C0464a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @Nullable
        public final a a(int code) {
            for (a it : a.values()) {
                if (it.getHttpCode() == code) {
                    return it;
                }
            }
            return null;
        }
    }
}
