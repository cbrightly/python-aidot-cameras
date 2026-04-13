package zendesk.ui.android.conversation.imagecell;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: ImageCellDirection.kt */
public enum a {
    INBOUND_SINGLE,
    INBOUND_TOP,
    INBOUND_MIDDLE,
    INBOUND_BOTTOM,
    OUTBOUND_SINGLE,
    OUTBOUND_TOP,
    OUTBOUND_MIDDLE,
    OUTBOUND_BOTTOM;
    
    @NotNull
    public static final C0568a Companion = null;

    static {
        Companion = new C0568a((DefaultConstructorMarker) null);
    }

    /* renamed from: zendesk.ui.android.conversation.imagecell.a$a  reason: collision with other inner class name */
    /* compiled from: ImageCellDirection.kt */
    public static final class C0568a {
        public /* synthetic */ C0568a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private C0568a() {
        }

        public final boolean a(@NotNull a $this$isInbound) {
            k.e($this$isInbound, "<this>");
            return $this$isInbound == a.INBOUND_SINGLE || $this$isInbound == a.INBOUND_TOP || $this$isInbound == a.INBOUND_MIDDLE || $this$isInbound == a.INBOUND_BOTTOM;
        }
    }
}
