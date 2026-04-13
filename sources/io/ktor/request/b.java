package io.ktor.request;

import io.ktor.util.pipeline.c;
import io.ktor.util.pipeline.g;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

/* compiled from: ApplicationReceiveFunctions.kt */
public class b extends c<c, io.ktor.application.b> {
    public static final a a1 = new a((DefaultConstructorMarker) null);
    @NotNull
    private static final g p0 = new g("After");
    @NotNull
    private static final g y = new g("Before");
    /* access modifiers changed from: private */
    @NotNull
    public static final g z = new g("Transform");

    public b() {
        super(y, z, p0);
    }

    /* compiled from: ApplicationReceiveFunctions.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final g a() {
            return b.z;
        }
    }
}
