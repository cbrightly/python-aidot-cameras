package jakarta.websocket;

import androidx.core.view.PointerIconCompat;
import java.io.UnsupportedEncodingException;

public class CloseReason {
    private final CloseCode a;
    private final String b;

    public interface CloseCode {
        int getCode();
    }

    public CloseReason(CloseCode closeCode, String reasonPhrase) {
        if (closeCode != null) {
            if (reasonPhrase != null) {
                try {
                    if (reasonPhrase.getBytes("UTF-8").length > 123) {
                        throw new IllegalArgumentException("Reason Phrase cannot exceed 123 UTF-8 encoded bytes: " + reasonPhrase);
                    }
                } catch (UnsupportedEncodingException uee) {
                    throw new IllegalStateException(uee);
                }
            }
            this.a = closeCode;
            this.b = "".equals(reasonPhrase) ? null : reasonPhrase;
            return;
        }
        throw new IllegalArgumentException("closeCode cannot be null");
    }

    public CloseCode a() {
        return this.a;
    }

    public String b() {
        String str = this.b;
        return str == null ? "" : str;
    }

    public String toString() {
        if (this.b == null) {
            return "CloseReason[" + this.a.getCode() + "]";
        }
        return "CloseReason[" + this.a.getCode() + "," + this.b + "]";
    }

    public enum a implements CloseCode {
        NORMAL_CLOSURE(1000),
        GOING_AWAY(1001),
        PROTOCOL_ERROR(1002),
        CANNOT_ACCEPT(1003),
        RESERVED(1004),
        NO_STATUS_CODE(1005),
        CLOSED_ABNORMALLY(1006),
        NOT_CONSISTENT(1007),
        VIOLATED_POLICY(PointerIconCompat.TYPE_TEXT),
        TOO_BIG(1009),
        NO_EXTENSION(1010),
        UNEXPECTED_CONDITION(1011),
        SERVICE_RESTART(PointerIconCompat.TYPE_NO_DROP),
        TRY_AGAIN_LATER(PointerIconCompat.TYPE_ALL_SCROLL),
        TLS_HANDSHAKE_FAILURE(PointerIconCompat.TYPE_VERTICAL_DOUBLE_ARROW);
        
        private int code;

        public static CloseCode getCloseCode(int code2) {
            if (code2 < 1000 || code2 > 4999) {
                throw new IllegalArgumentException("Invalid code: " + code2);
            }
            switch (code2) {
                case 1000:
                    return NORMAL_CLOSURE;
                case 1001:
                    return GOING_AWAY;
                case 1002:
                    return PROTOCOL_ERROR;
                case 1003:
                    return CANNOT_ACCEPT;
                case 1004:
                    return RESERVED;
                case 1005:
                    return NO_STATUS_CODE;
                case 1006:
                    return CLOSED_ABNORMALLY;
                case 1007:
                    return NOT_CONSISTENT;
                case PointerIconCompat.TYPE_TEXT:
                    return VIOLATED_POLICY;
                case 1009:
                    return TOO_BIG;
                case 1010:
                    return NO_EXTENSION;
                case 1011:
                    return UNEXPECTED_CONDITION;
                case PointerIconCompat.TYPE_NO_DROP:
                    return SERVICE_RESTART;
                case PointerIconCompat.TYPE_ALL_SCROLL:
                    return TRY_AGAIN_LATER;
                case PointerIconCompat.TYPE_VERTICAL_DOUBLE_ARROW:
                    return TLS_HANDSHAKE_FAILURE;
                default:
                    return new C0314a(code2);
            }
        }

        /* renamed from: jakarta.websocket.CloseReason$a$a  reason: collision with other inner class name */
        public static final class C0314a implements CloseCode {
            final /* synthetic */ int c;

            C0314a(int i) {
                this.c = i;
            }

            public int getCode() {
                return this.c;
            }
        }

        private a(int code2) {
            this.code = code2;
        }

        public int getCode() {
            return this.code;
        }
    }
}
