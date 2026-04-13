package io.ktor.server.engine;

import androidx.core.app.NotificationCompat;
import io.ktor.application.b;
import io.ktor.response.d;
import io.ktor.util.pipeline.c;
import io.ktor.util.pipeline.g;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: EnginePipeline.kt */
public final class s extends c<x, b> {
    public static final a p0 = new a((DefaultConstructorMarker) null);
    /* access modifiers changed from: private */
    @NotNull
    public static final g y = new g("before");
    /* access modifiers changed from: private */
    @NotNull
    public static final g z = new g(NotificationCompat.CATEGORY_CALL);
    @NotNull
    private final io.ktor.request.b a1 = new io.ktor.request.b();
    @NotNull
    private final d p1 = new d();

    public s() {
        super(y, z);
    }

    @NotNull
    public final io.ktor.request.b A() {
        return this.a1;
    }

    @NotNull
    public final d B() {
        return this.p1;
    }

    /* compiled from: EnginePipeline.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final g a() {
            return s.y;
        }

        @NotNull
        public final g b() {
            return s.z;
        }
    }
}
