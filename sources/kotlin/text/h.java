package kotlin.text;

import java.util.List;
import kotlin.jvm.internal.k;
import kotlin.ranges.i;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: MatchResult.kt */
public interface h {
    @NotNull
    b a();

    @NotNull
    List<String> b();

    @NotNull
    i c();

    @NotNull
    g d();

    @Nullable
    h next();

    /* compiled from: MatchResult.kt */
    public static final class a {
        @NotNull
        public static b a(@NotNull h $this) {
            return new b($this);
        }
    }

    /* compiled from: MatchResult.kt */
    public static final class b {
        @NotNull
        private final h a;

        public b(@NotNull h match) {
            k.e(match, "match");
            this.a = match;
        }

        @NotNull
        public final h a() {
            return this.a;
        }
    }
}
