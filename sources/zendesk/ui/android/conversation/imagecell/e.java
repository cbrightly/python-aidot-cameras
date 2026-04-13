package zendesk.ui.android.conversation.imagecell;

import com.luck.picture.lib.compress.Checker;
import com.yanzhenjie.andserver.util.h;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ImageType.kt */
public enum e {
    JPEG("image/jpeg"),
    JPG(Checker.MIME_TYPE_JPG),
    PNG("image/png"),
    GIF(h.IMAGE_GIF_VALUE),
    WEBP("image/webp"),
    SVG("image/svg+xml");
    
    @NotNull
    public static final a Companion = null;
    @NotNull
    private final String value;

    private e(String value2) {
        this.value = value2;
    }

    @NotNull
    public final String getValue$zendesk_ui_ui_android() {
        return this.value;
    }

    static {
        Companion = new a((DefaultConstructorMarker) null);
    }

    /* compiled from: ImageType.kt */
    public static final class a {
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }

        public final boolean a(@Nullable String value) {
            for (e it : e.values()) {
                if (k.a(it.getValue$zendesk_ui_ui_android(), value)) {
                    return true;
                }
            }
            return false;
        }
    }
}
