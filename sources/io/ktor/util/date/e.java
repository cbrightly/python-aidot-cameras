package io.ktor.util.date;

import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

/* compiled from: Date.kt */
public enum e {
    MONDAY("Mon"),
    TUESDAY("Tue"),
    WEDNESDAY("Wed"),
    THURSDAY("Thu"),
    FRIDAY("Fri"),
    SATURDAY("Sat"),
    SUNDAY("Sun");
    
    public static final a Companion = null;
    @NotNull
    private final String value;

    private e(String value2) {
        this.value = value2;
    }

    @NotNull
    public final String getValue() {
        return this.value;
    }

    static {
        Companion = new a((DefaultConstructorMarker) null);
    }

    /* compiled from: Date.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final e a(int ordinal) {
            return e.values()[ordinal];
        }
    }
}
