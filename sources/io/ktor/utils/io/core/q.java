package io.ktor.utils.io.core;

import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import com.google.firebase.analytics.FirebaseAnalytics;
import io.ktor.utils.io.core.internal.a;
import io.ktor.utils.io.pool.d;
import java.nio.ByteBuffer;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ByteReadPacket.kt */
public final class q extends s implements w {
    /* access modifiers changed from: private */
    @NotNull
    public static final q a1;
    public static final a p1 = new a((DefaultConstructorMarker) null);

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public q(@NotNull io.ktor.utils.io.core.internal.a head, long remaining, @NotNull d<io.ktor.utils.io.core.internal.a> pool) {
        super(head, remaining, pool);
        k.f(head, CacheEntity.HEAD);
        k.f(pool, "pool");
        a1();
    }

    public /* bridge */ /* synthetic */ io.ktor.utils.io.core.internal.a z() {
        return (io.ktor.utils.io.core.internal.a) w1();
    }

    @NotNull
    public final q v1() {
        return new q(j.a(T()), P0(), F0());
    }

    /* access modifiers changed from: protected */
    @Nullable
    public final Void w1() {
        return null;
    }

    /* access modifiers changed from: protected */
    public final int E(@NotNull ByteBuffer destination, int offset, int length) {
        k.f(destination, FirebaseAnalytics.Param.DESTINATION);
        return 0;
    }

    /* access modifiers changed from: protected */
    public final void l() {
    }

    @NotNull
    public String toString() {
        return "ByteReadPacket(" + P0() + " bytes remaining)";
    }

    /* compiled from: ByteReadPacket.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final q a() {
            return q.a1;
        }
    }

    static {
        a.f fVar = io.ktor.utils.io.core.internal.a.z4;
        a1 = new q(fVar.a(), 0, fVar.b());
    }
}
