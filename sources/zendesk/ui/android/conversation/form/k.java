package zendesk.ui.android.conversation.form;

import kotlin.jvm.functions.l;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

/* compiled from: FieldResponseRendering.kt */
public final class k {
    @NotNull
    private final l a;

    public k(@NotNull a builder) {
        kotlin.jvm.internal.k.e(builder, "builder");
        this.a = builder.b();
    }

    @NotNull
    public final l a() {
        return this.a;
    }

    public k() {
        this(new a());
    }

    @NotNull
    public final a b() {
        return new a(this);
    }

    /* compiled from: FieldResponseRendering.kt */
    public static final class a {
        @NotNull
        private l a;

        public a() {
            this.a = new l((String) null, (String) null, 3, (DefaultConstructorMarker) null);
        }

        @NotNull
        public final l b() {
            return this.a;
        }

        public final void c(@NotNull l lVar) {
            kotlin.jvm.internal.k.e(lVar, "<set-?>");
            this.a = lVar;
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        public a(@NotNull k rendering) {
            this();
            kotlin.jvm.internal.k.e(rendering, "rendering");
            this.a = rendering.a();
        }

        @NotNull
        public final a d(@NotNull l<? super l, l> stateUpdate) {
            kotlin.jvm.internal.k.e(stateUpdate, "stateUpdate");
            c(stateUpdate.invoke(b()));
            return this;
        }

        @NotNull
        public final k a() {
            return new k(this);
        }
    }
}
