package zendesk.ui.android.conversation.form;

import java.util.List;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: FormResponseRendering.kt */
public final class q {
    @NotNull
    private final r a;

    public q(@NotNull a builder) {
        k.e(builder, "builder");
        this.a = builder.b();
    }

    @NotNull
    public final r a() {
        return this.a;
    }

    public q() {
        this(new a());
    }

    /* compiled from: FormResponseRendering.kt */
    public static final class a {
        @NotNull
        private r a = new r((List) null, 1, (DefaultConstructorMarker) null);

        @NotNull
        public final r b() {
            return this.a;
        }

        public final void c(@NotNull r rVar) {
            k.e(rVar, "<set-?>");
            this.a = rVar;
        }

        @NotNull
        public final a d(@NotNull l<? super r, r> stateUpdate) {
            k.e(stateUpdate, "stateUpdate");
            c(stateUpdate.invoke(b()));
            return this;
        }

        @NotNull
        public final q a() {
            return new q(this);
        }
    }
}
