package zendesk.ui.android.conversation.imagerviewer;

import android.net.Uri;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: ImageViewerRendering.kt */
public final class a {
    @NotNull
    private final kotlin.jvm.functions.a<x> a;
    @NotNull
    private final b b;

    public a(@NotNull C0571a builder) {
        k.e(builder, "builder");
        this.a = builder.b();
        this.b = builder.c();
    }

    @NotNull
    public final kotlin.jvm.functions.a<x> a() {
        return this.a;
    }

    @NotNull
    public final b b() {
        return this.b;
    }

    public a() {
        this(new C0571a());
    }

    @NotNull
    public final C0571a c() {
        return new C0571a(this);
    }

    /* renamed from: zendesk.ui.android.conversation.imagerviewer.a$a  reason: collision with other inner class name */
    /* compiled from: ImageViewerRendering.kt */
    public static final class C0571a {
        @NotNull
        private kotlin.jvm.functions.a<x> a;
        @NotNull
        private b b;

        /* renamed from: zendesk.ui.android.conversation.imagerviewer.a$a$a  reason: collision with other inner class name */
        /* compiled from: ImageViewerRendering.kt */
        public static final class C0572a extends l implements kotlin.jvm.functions.a<x> {
            public static final C0572a INSTANCE = new C0572a();

            C0572a() {
                super(0);
            }

            public final void invoke() {
            }
        }

        public C0571a() {
            this.a = C0572a.INSTANCE;
            this.b = new b((String) null, (String) null, (String) null, (Uri) null, (Integer) null, (Integer) null, 63, (DefaultConstructorMarker) null);
        }

        @NotNull
        public final kotlin.jvm.functions.a<x> b() {
            return this.a;
        }

        public final void e(@NotNull kotlin.jvm.functions.a<x> aVar) {
            k.e(aVar, "<set-?>");
            this.a = aVar;
        }

        @NotNull
        public final b c() {
            return this.b;
        }

        public final void f(@NotNull b bVar) {
            k.e(bVar, "<set-?>");
            this.b = bVar;
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        public C0571a(@NotNull a rendering) {
            this();
            k.e(rendering, "rendering");
            this.a = rendering.a();
            this.b = rendering.b();
        }

        @NotNull
        public final C0571a d(@NotNull kotlin.jvm.functions.a<x> onBackButtonClicked) {
            k.e(onBackButtonClicked, "onBackButtonClicked");
            e(onBackButtonClicked);
            return this;
        }

        @NotNull
        public final C0571a g(@NotNull kotlin.jvm.functions.l<? super b, b> stateUpdate) {
            k.e(stateUpdate, "stateUpdate");
            f(stateUpdate.invoke(c()));
            return this;
        }

        @NotNull
        public final a a() {
            return new a(this);
        }
    }
}
