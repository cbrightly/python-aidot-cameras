package kotlin.reflect.jvm.internal.impl.descriptors;

import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

/* compiled from: Modality.kt */
public enum w {
    FINAL,
    SEALED,
    OPEN,
    ABSTRACT;
    
    public static final a Companion = null;

    static {
        Companion = new a((DefaultConstructorMarker) null);
    }

    /* compiled from: Modality.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final w a(boolean z, boolean open) {
            if (z) {
                return w.ABSTRACT;
            }
            if (open) {
                return w.OPEN;
            }
            return w.FINAL;
        }
    }
}
