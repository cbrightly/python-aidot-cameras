package kotlin.reflect.jvm.internal.impl.utils;

import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

/* compiled from: Jsr305State.kt */
public enum h {
    IGNORE("ignore"),
    WARN("warn"),
    STRICT("strict");
    
    public static final a Companion = null;
    @NotNull
    private final String description;

    private h(String description2) {
        this.description = description2;
    }

    @NotNull
    public final String getDescription() {
        return this.description;
    }

    static {
        Companion = new a((DefaultConstructorMarker) null);
    }

    /* compiled from: Jsr305State.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    public final boolean isWarning() {
        return this == WARN;
    }

    public final boolean isIgnore() {
        return this == IGNORE;
    }
}
