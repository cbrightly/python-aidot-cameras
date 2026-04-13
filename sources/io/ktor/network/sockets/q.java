package io.ktor.network.sockets;

import java.util.HashMap;
import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: SocketOptions.kt */
public abstract class q {
    public static final b a = new b((DefaultConstructorMarker) null);
    private byte b;
    private boolean c;
    private boolean d;
    @NotNull
    private final Map<Object, Object> e;

    private q(Map<Object, Object> customOptions) {
        this.e = customOptions;
        this.b = u.f.a();
    }

    public /* synthetic */ q(Map customOptions, DefaultConstructorMarker $constructor_marker) {
        this(customOptions);
    }

    /* access modifiers changed from: protected */
    public void b(@NotNull q from) {
        k.f(from, "from");
        this.b = from.b;
        this.c = from.c;
        this.d = from.d;
    }

    @NotNull
    public final d f() {
        d dVar = new d(new HashMap(this.e));
        d dVar2 = dVar;
        b(this);
        return dVar;
    }

    @NotNull
    public final a a() {
        a $this$apply = new a(new HashMap(this.e));
        $this$apply.b(this);
        return $this$apply;
    }

    /* compiled from: SocketOptions.kt */
    public static final class c extends q {
        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public c(@NotNull Map<Object, Object> customOptions) {
            super(customOptions, (DefaultConstructorMarker) null);
            k.f(customOptions, "customOptions");
        }
    }

    public final byte e() {
        return this.b;
    }

    public final boolean c() {
        return this.c;
    }

    public final boolean d() {
        return this.d;
    }

    /* compiled from: SocketOptions.kt */
    public static final class a extends q {
        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public a(@NotNull Map<Object, Object> customOptions) {
            super(customOptions, (DefaultConstructorMarker) null);
            k.f(customOptions, "customOptions");
        }
    }

    /* compiled from: SocketOptions.kt */
    public static class d extends q {
        private int f = -1;
        private int g = -1;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public d(@NotNull Map<Object, Object> customOptions) {
            super(customOptions, (DefaultConstructorMarker) null);
            k.f(customOptions, "customOptions");
        }

        public final int h() {
            return this.f;
        }

        public final int g() {
            return this.g;
        }

        /* access modifiers changed from: protected */
        public void b(@NotNull q from) {
            k.f(from, "from");
            q.super.b(from);
            if (from instanceof d) {
                this.f = ((d) from).f;
                this.g = ((d) from).g;
            }
        }
    }

    /* compiled from: SocketOptions.kt */
    public static final class e extends d {
        private boolean h;
        private int i;
        @Nullable
        private Boolean j;
        private long k;

        public final boolean k() {
            return this.h;
        }

        public final int j() {
            return this.i;
        }

        @Nullable
        public final Boolean i() {
            return this.j;
        }

        public final long l() {
            return this.k;
        }

        /* access modifiers changed from: protected */
        public void b(@NotNull q from) {
            k.f(from, "from");
            super.b(from);
            if (from instanceof e) {
                this.h = ((e) from).h;
                this.i = ((e) from).i;
                this.j = ((e) from).j;
            }
        }
    }

    /* compiled from: SocketOptions.kt */
    public static final class b {
        private b() {
        }

        public /* synthetic */ b(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final q a() {
            return new c(new HashMap());
        }
    }
}
