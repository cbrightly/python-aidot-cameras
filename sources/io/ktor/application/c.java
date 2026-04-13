package io.ktor.application;

import io.ktor.request.b;
import io.ktor.response.d;
import io.ktor.util.pipeline.g;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: ApplicationCallPipeline.kt */
public class c extends io.ktor.util.pipeline.c<x, b> {
    /* access modifiers changed from: private */
    @NotNull
    public static final g a1 = new g("Call");
    public static final a a2 = new a((DefaultConstructorMarker) null);
    /* access modifiers changed from: private */
    @NotNull
    public static final g p0 = new g("Features");
    @NotNull
    private static final g p1 = new g("Fallback");
    @NotNull
    private static final g y = new g("Setup");
    /* access modifiers changed from: private */
    @NotNull
    public static final g z = new g("Monitoring");
    @NotNull
    private final b p2 = new b();
    @NotNull
    private final d p3 = new d();

    public c() {
        super(y, z, p0, a1, p1);
    }

    @NotNull
    public final b B() {
        return this.p2;
    }

    @NotNull
    public final d C() {
        return this.p3;
    }

    /* compiled from: ApplicationCallPipeline.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final g c() {
            return c.z;
        }

        @NotNull
        public final g b() {
            return c.p0;
        }

        @NotNull
        public final g a() {
            return c.a1;
        }
    }
}
