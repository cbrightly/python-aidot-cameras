package io.ktor.http.content;

import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import io.ktor.http.o;
import io.ktor.http.v;
import io.ktor.utils.io.i;
import io.ktor.utils.io.l;
import kotlin.coroutines.g;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.x;
import kotlinx.coroutines.z1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: OutgoingContent.kt */
public abstract class j {
    private io.ktor.util.b a;

    private j() {
    }

    public /* synthetic */ j(DefaultConstructorMarker $constructor_marker) {
        this();
    }

    @Nullable
    public io.ktor.http.c b() {
        return null;
    }

    @Nullable
    public Long a() {
        return null;
    }

    @Nullable
    public v e() {
        return null;
    }

    @NotNull
    public o c() {
        return o.a.a();
    }

    @Nullable
    public <T> T d(@NotNull io.ktor.util.a<T> key) {
        k.f(key, CacheEntity.KEY);
        io.ktor.util.b bVar = this.a;
        if (bVar != null) {
            return bVar.e(key);
        }
        return null;
    }

    public <T> void f(@NotNull io.ktor.util.a<T> key, @Nullable T value) {
        k.f(key, CacheEntity.KEY);
        if (value != null || this.a != null) {
            if (value == null) {
                io.ktor.util.b bVar = this.a;
                if (bVar != null) {
                    bVar.d(key);
                    return;
                }
                return;
            }
            io.ktor.util.b it = this.a;
            if (it == null) {
                it = io.ktor.util.d.b(false, 1, (Object) null);
            }
            this.a = it;
            it.b(key, value);
        }
    }

    /* compiled from: OutgoingContent.kt */
    public static abstract class b extends j {
        public b() {
            super((DefaultConstructorMarker) null);
        }
    }

    /* compiled from: OutgoingContent.kt */
    public static abstract class d extends j {
        @NotNull
        public abstract i g();

        public d() {
            super((DefaultConstructorMarker) null);
        }
    }

    /* compiled from: OutgoingContent.kt */
    public static abstract class e extends j {
        @Nullable
        public abstract Object g(@NotNull l lVar, @NotNull kotlin.coroutines.d<? super x> dVar);

        public e() {
            super((DefaultConstructorMarker) null);
        }
    }

    /* compiled from: OutgoingContent.kt */
    public static abstract class a extends j {
        @NotNull
        public abstract byte[] g();

        public a() {
            super((DefaultConstructorMarker) null);
        }
    }

    /* compiled from: OutgoingContent.kt */
    public static abstract class c extends j {
        @Nullable
        public abstract Object g(@NotNull i iVar, @NotNull l lVar, @NotNull g gVar, @NotNull g gVar2, @NotNull kotlin.coroutines.d<? super z1> dVar);

        public c() {
            super((DefaultConstructorMarker) null);
        }

        @Nullable
        public final v e() {
            return v.c0.Q();
        }
    }
}
