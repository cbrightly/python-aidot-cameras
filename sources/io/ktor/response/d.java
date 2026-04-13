package io.ktor.response;

import io.ktor.application.b;
import io.ktor.util.pipeline.c;
import io.ktor.util.pipeline.g;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

/* compiled from: ApplicationSendPipeline.kt */
public class d extends c<Object, b> {
    @NotNull
    private static final g a1 = new g("ContentEncoding");
    @NotNull
    private static final g a2 = new g("After");
    /* access modifiers changed from: private */
    @NotNull
    public static final g p0 = new g("Render");
    @NotNull
    private static final g p1 = new g("TransferEncoding");
    /* access modifiers changed from: private */
    @NotNull
    public static final g p2 = new g("Engine");
    public static final a p3 = new a((DefaultConstructorMarker) null);
    @NotNull
    private static final g y = new g("Before");
    @NotNull
    private static final g z = new g("Transform");

    public d() {
        super(y, z, p0, a1, p1, a2, p2);
    }

    /* compiled from: ApplicationSendPipeline.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final g b() {
            return d.p0;
        }

        @NotNull
        public final g a() {
            return d.p2;
        }
    }
}
