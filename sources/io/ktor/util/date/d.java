package io.ktor.util.date;

import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

/* compiled from: Date.kt */
public enum d {
    JANUARY("Jan"),
    FEBRUARY("Feb"),
    MARCH("Mar"),
    APRIL("Apr"),
    MAY("May"),
    JUNE("Jun"),
    JULY("Jul"),
    AUGUST("Aug"),
    SEPTEMBER("Sep"),
    OCTOBER("Oct"),
    NOVEMBER("Nov"),
    DECEMBER("Dec");
    
    public static final a Companion = null;
    @NotNull
    private final String value;

    private d(String value2) {
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
        public final d a(int ordinal) {
            return d.values()[ordinal];
        }
    }
}
