package zendesk.conversationkit.android.model;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.luck.picture.lib.config.PictureMimeType;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Message.kt */
public enum v {
    UNSUPPORTED("unsupported"),
    TEXT("text"),
    FILE_UPLOAD("file_upload"),
    FILE("file"),
    IMAGE(PictureMimeType.MIME_TYPE_PREFIX_IMAGE),
    CAROUSEL("carousel"),
    LIST("list"),
    LOCATION(FirebaseAnalytics.Param.LOCATION),
    FORM("form"),
    FORM_RESPONSE("formResponse");
    
    @NotNull
    public static final a Companion = null;
    @NotNull
    private final String value;

    private v(String value2) {
        this.value = value2;
    }

    @NotNull
    public final String getValue$zendesk_conversationkit_conversationkit_android() {
        return this.value;
    }

    static {
        Companion = new a((DefaultConstructorMarker) null);
    }

    /* compiled from: Message.kt */
    public static final class a {
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }

        @NotNull
        public final v a(@NotNull String value) {
            v it;
            k.e(value, "value");
            v[] values = v.values();
            int length = values.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    it = null;
                    break;
                }
                it = values[i];
                if (k.a(it.getValue$zendesk_conversationkit_conversationkit_android(), value)) {
                    break;
                }
                i++;
            }
            return it == null ? v.UNSUPPORTED : it;
        }
    }
}
