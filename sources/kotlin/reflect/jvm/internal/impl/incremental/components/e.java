package kotlin.reflect.jvm.internal.impl.incremental.components;

import java.io.Serializable;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: LookupLocation.kt */
public final class e implements Serializable {
    public static final a Companion = new a((DefaultConstructorMarker) null);
    /* access modifiers changed from: private */
    @NotNull
    public static final e c = new e(-1, -1);
    private final int column;
    private final int line;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof e)) {
            return false;
        }
        e eVar = (e) obj;
        return this.line == eVar.line && this.column == eVar.column;
    }

    public int hashCode() {
        return (this.line * 31) + this.column;
    }

    @NotNull
    public String toString() {
        return "Position(line=" + this.line + ", column=" + this.column + ")";
    }

    /* compiled from: LookupLocation.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final e a() {
            return e.c;
        }
    }

    public e(int line2, int column2) {
        this.line = line2;
        this.column = column2;
    }
}
