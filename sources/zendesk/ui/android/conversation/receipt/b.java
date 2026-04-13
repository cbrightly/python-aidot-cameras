package zendesk.ui.android.conversation.receipt;

import kotlin.jvm.functions.l;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: MessageReceiptRendering.kt */
public final class b {
    @NotNull
    private final c a;

    public b(@NotNull a builder) {
        k.e(builder, "builder");
        this.a = builder.b();
    }

    @NotNull
    public final c a() {
        return this.a;
    }

    public b() {
        this(new a());
    }

    @NotNull
    public final a b() {
        return new a(this);
    }

    /* compiled from: MessageReceiptRendering.kt */
    public static final class a {
        @NotNull
        private c a;

        public a() {
            this.a = new c((String) null, (a) null, false, (Integer) null, (Integer) null, 31, (DefaultConstructorMarker) null);
        }

        @NotNull
        public final c b() {
            return this.a;
        }

        public final void c(@NotNull c cVar) {
            k.e(cVar, "<set-?>");
            this.a = cVar;
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        public a(@NotNull b rendering) {
            this();
            k.e(rendering, "rendering");
            this.a = rendering.a();
        }

        @NotNull
        public final a d(@NotNull l<? super c, c> stateUpdate) {
            k.e(stateUpdate, "stateUpdate");
            c(stateUpdate.invoke(b()));
            return this;
        }

        @NotNull
        public final b a() {
            return new b(this);
        }
    }
}
