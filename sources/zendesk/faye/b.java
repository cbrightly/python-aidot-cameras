package zendesk.faye;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: BayeuxOptionalFields.kt */
public final class b {
    @NotNull
    public static final C0517b a = new C0517b((DefaultConstructorMarker) null);
    @Nullable
    private final String b;
    @Nullable
    private final String c;

    public /* synthetic */ b(String str, String str2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2);
    }

    private b(String ext, String id) {
        this.b = ext;
        this.c = id;
    }

    @Nullable
    public final String a() {
        return this.b;
    }

    @Nullable
    public final String b() {
        return this.c;
    }

    /* compiled from: BayeuxOptionalFields.kt */
    public static final class a {
        @Nullable
        private String a;
        @Nullable
        private String b;

        @NotNull
        public final a b(@NotNull String ext) {
            k.e(ext, "ext");
            this.a = ext;
            return this;
        }

        @NotNull
        public final b a() {
            return new b(this.a, this.b, (DefaultConstructorMarker) null);
        }
    }

    /* renamed from: zendesk.faye.b$b  reason: collision with other inner class name */
    /* compiled from: BayeuxOptionalFields.kt */
    public static final class C0517b {
        public /* synthetic */ C0517b(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private C0517b() {
        }

        @NotNull
        public final a a() {
            return new a();
        }
    }
}
